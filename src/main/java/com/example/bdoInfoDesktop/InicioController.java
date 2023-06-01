package com.example.bdoInfoDesktop;

import com.example.bdoInfoDesktop.db.UsuariosDB;
import com.example.bdoInfoDesktop.db.ZonasDB;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class InicioController {
    static String conexionURL = "mongodb+srv://Otelox:I3LvJTkOkZsqDm4j@cluster0.shwupsp.mongodb.net/?retryWrites=true&w=majority";
    @FXML
    private StackPane inicioPane;
    @FXML
    private StackPane usuariosPane;
    @FXML
    private Pane configuracionPane;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private TableView<UsuariosDB> usuariosTable;
    @FXML
    private TableColumn<UsuariosDB, String> usuarioColumna;
    @FXML
    private TableColumn<UsuariosDB, String> contraseñaColumna;
    @FXML
    private TableColumn<UsuariosDB, String> emailColumna;
    @FXML
    private TableColumn<UsuariosDB, Integer> maestriaCarneColumna;
    @FXML
    private TableColumn<UsuariosDB, Integer> maestriaHierbasColumna;
    @FXML
    private TableColumn<UsuariosDB, Integer> maestriaSangreColumna;
    @FXML
    private TableColumn<UsuariosDB, Integer> maestriaTalaColumna;
    @FXML
    private TableView<ZonasDB> zonasTable;
    @FXML
    private TableColumn<ZonasDB, String> nombreZonaColumna;
    @FXML
    private TableColumn<ZonasDB, String> tipoZonaColumna;
    @FXML
    private TableColumn<ZonasDB, Integer> item1Columna;
    @FXML
    private TableColumn<ZonasDB, Integer> item2Columna;
    @FXML
    private TableColumn<ZonasDB, Integer> item3Columna;
    @FXML
    private TableColumn<ZonasDB, Integer> item4Columna;
    @FXML
    private TableColumn<ZonasDB, Integer> item5Columna;

    private void initialize() {

    }

    @FXML
    public void mostrarInicio() {
        inicioPane.setVisible(true);
        usuariosPane.setVisible(false);
        configuracionPane.setVisible(false);
        zonasTable.setVisible(false);
        progressBar.setVisible(true);

        Task<List<ZonasDB>> obtenerZonasTask = new Task<List<ZonasDB>>() {
            @Override
            protected List<ZonasDB> call() throws Exception {
                List<ZonasDB> listaZonas = obtenerZonasDB();
                int totalZonas = listaZonas.size();
                int progreso = 0;

                for (ZonasDB zona : listaZonas) {
                    progreso++;
                    double progresoPorcentaje = (double) progreso / totalZonas;
                    updateProgress(progresoPorcentaje, 1.0);
                }

                return listaZonas;
            }
        };

        obtenerZonasTask.setOnSucceeded(event -> {
            List<ZonasDB> listaZonas = obtenerZonasTask.getValue();
            ObservableList<ZonasDB> zonasObservableList = FXCollections.observableArrayList(listaZonas);
            nombreZonaColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tipoZonaColumna.setCellValueFactory(new PropertyValueFactory<>("tipoZona"));
            item1Columna.setCellValueFactory(new PropertyValueFactory<>("item1"));
            item2Columna.setCellValueFactory(new PropertyValueFactory<>("item2"));
            item3Columna.setCellValueFactory(new PropertyValueFactory<>("item3"));
            item4Columna.setCellValueFactory(new PropertyValueFactory<>("item4"));
            item5Columna.setCellValueFactory(new PropertyValueFactory<>("item5"));
            zonasTable.setItems(zonasObservableList);
            progressBar.setVisible(false);
            zonasTable.setVisible(true);
        });
        //Vinculo la barra de progreso al task
        progressBar.progressProperty().bind(obtenerZonasTask.progressProperty());

        // Ejecuto el task en otro hilo.
        Thread obtenerZonasThread = new Thread(obtenerZonasTask);
        obtenerZonasThread.start();
    }

    @FXML
    public void mostrarUsuarios() {
        inicioPane.setVisible(false);
        usuariosPane.setVisible(true);
        configuracionPane.setVisible(false);
        usuariosTable.setVisible(false);
        progressBar.setVisible(true);

        Task<List<UsuariosDB>> obtenerUsuariosTask = new Task<List<UsuariosDB>>() {
            @Override
            protected List<UsuariosDB> call() throws Exception {
                List<UsuariosDB> listaUsuarios = obtenerUsuariosDB();
                int totalUsuarios = listaUsuarios.size();
                int progreso = 0;

                for (UsuariosDB usuario : listaUsuarios) {
                    progreso++;
                    double progresoPorcentaje = (double) progreso / totalUsuarios;
                    updateProgress(progresoPorcentaje, 1.0);
                }

                return listaUsuarios;
            }
        };

        obtenerUsuariosTask.setOnSucceeded(event -> {
            List<UsuariosDB> listaUsuarios = obtenerUsuariosTask.getValue();
            ObservableList<UsuariosDB> usuariosObservableList = FXCollections.observableArrayList(listaUsuarios);
            usuarioColumna.setCellValueFactory(new PropertyValueFactory<>("usuario"));
            contraseñaColumna.setCellValueFactory(new PropertyValueFactory<>("contraseña"));
            emailColumna.setCellValueFactory(new PropertyValueFactory<>("email"));
            maestriaCarneColumna.setCellValueFactory(new PropertyValueFactory<>("maestriaCarne"));
            maestriaHierbasColumna.setCellValueFactory(new PropertyValueFactory<>("maestriaHierbas"));
            maestriaSangreColumna.setCellValueFactory(new PropertyValueFactory<>("maestriaSangre"));
            maestriaTalaColumna.setCellValueFactory(new PropertyValueFactory<>("maestriaTala"));
            usuariosTable.setItems(usuariosObservableList);
            progressBar.setVisible(false);
            usuariosTable.setVisible(true);
        });
        //Vinculo la barra de progreso al task
        progressBar.progressProperty().bind(obtenerUsuariosTask.progressProperty());

        // Ejecuto el task en otro hilo.
        Thread obtenerUsuariosThread = new Thread(obtenerUsuariosTask);
        obtenerUsuariosThread.start();
    }


    @FXML
    public void mostrarConfiguracion() {
        inicioPane.setVisible(false);
        usuariosPane.setVisible(false);
        configuracionPane.setVisible(true);
    }

    public static List<UsuariosDB> obtenerUsuariosDB() {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(conexionURL))
                .serverApi(serverApi)
                .build();
        List<UsuariosDB> usuariosList = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                MongoDatabase database = mongoClient.getDatabase("bdoHelp");
                MongoCollection<Document> collection = database.getCollection("Usuarios");

                for (Document document : collection.find()) {
                    UsuariosDB usuario = new UsuariosDB();
                    usuario.set_id(document.getObjectId("_id"));
                    usuario.setUsuario(document.getString("usuario"));
                    usuario.setContraseña(document.getString("contraseña"));
                    usuario.setEmail(document.getString("email"));
                    usuario.setMaestriaCarne(document.getInteger("maestriaCarne"));
                    usuario.setMaestriaHierbas(document.getInteger("maestriaHierbas"));
                    usuario.setMaestriaSangre(document.getInteger("maestriaSangre"));
                    usuario.setMaestriaTala(document.getInteger("maestriaTala"));
                    usuariosList.add(usuario);
                }
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
        return usuariosList;
    }
    public static List<ZonasDB> obtenerZonasDB() {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(conexionURL))
                .serverApi(serverApi)
                .build();
        List<ZonasDB> zonasList = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                MongoDatabase database = mongoClient.getDatabase("bdoHelp");
                MongoCollection<Document> collection = database.getCollection("Zonas");

                for (Document document : collection.find()) {
                    ZonasDB zona = new ZonasDB();
                    zona.set_id(document.getObjectId("_id"));
                    zona.setNombre(document.getString("nombre"));
                    zona.setTipoZona(document.getString("tipoZona"));
                    zona.setItem1(document.getInteger("item1"));
                    zona.setItem2(document.getInteger("item2"));
                    zona.setItem3(document.getInteger("item3"));
                    zona.setItem4(document.getInteger("item4"));
                    zona.setItem5(document.getInteger("item5"));
                    zonasList.add(zona);
                }
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
        return zonasList;
    }
}
