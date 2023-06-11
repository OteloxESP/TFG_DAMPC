package com.example.tfg_dampc;

import com.example.tfg_dampc.db.UsuariosDB;
import com.example.tfg_dampc.db.ZonasDB;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class InicioController {
    static String conexionURL = "mongodb+srv://Otelox:I3LvJTkOkZsqDm4j@cluster0.shwupsp.mongodb.net/?retryWrites=true&w=majority";
    @FXML
    private StackPane inicioPane;
    @FXML
    private StackPane usuariosPane;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private TableView<UsuariosDB> usuariosTable;
    @FXML
    private TableColumn<UsuariosDB, String> usuarioColumna;
    @FXML
    private TableColumn<UsuariosDB, String> contrasenaColumna;
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
    private TableColumn<UsuariosDB, Integer> administradorColumna;
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
    Button inicioButton;
    @FXML
    Button usuariosButton;
    @FXML
    Button salirButton;
    @FXML
    Button anadirZonaButton;
    @FXML
    Button editarZonaButton;
    @FXML
    Button borrarZonaButton;
    @FXML
    Button anadirUsuarioButton;
    @FXML
    Button editarUsuarioButton;
    @FXML
    Button borrarUsuarioButton;
    @FXML
    ZonasController zonasC;
    @FXML
    UsuariosController usuariosC;

    static MongoClientSettings settings;
    static MongoDatabase database;

    public void initialize() {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(conexionURL))
                .serverApi(serverApi)
                .build();
        try{
            MongoClient mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase("bdoHelp");

        }catch (MongoException e){
            mostrarErrorAlerta("Error de servidor. Vuelve a intentarlo más tarde");
        }


        // Asignar un color a los botones
        anadirZonaButton.setBackground(new Background(new BackgroundFill(Color.LIMEGREEN, new CornerRadii(3), null)));
        editarZonaButton.setBackground(new Background(new BackgroundFill(Color.AQUA, new CornerRadii(3), null)));
        borrarZonaButton.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(3), null)));

        anadirUsuarioButton.setBackground(new Background(new BackgroundFill(Color.LIMEGREEN, new CornerRadii(3), null)));
        editarUsuarioButton.setBackground(new Background(new BackgroundFill(Color.AQUA, new CornerRadii(3), null)));
        borrarUsuarioButton.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(3), null)));

        editarZonaButton.setDisable(true);
        borrarZonaButton.setDisable(true);
        editarUsuarioButton.setDisable(true);
        borrarUsuarioButton.setDisable(true);

    }

    @FXML
    public void mostrarInicio() {
        editarZonaButton.setDisable(true);
        borrarZonaButton.setDisable(true);
        inicioPane.setVisible(true);
        usuariosPane.setVisible(false);
        mostrarZonas();
    }

    @FXML
    public void anadirZona() throws IOException {
        ZonasApplication zonas = new ZonasApplication();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Añadir zona");
        zonas.start(stage);
        zonasC = zonas.zonasController;
        zonasC.aceptarButton.setText("Añadir");
        zonasC.aceptarButton.setOnAction(actionEvent -> {
            if (zonasC.comprobarCamposVacios()) {
                mostrarErrorAlerta("Uno o más campos estan vacíos");

            } else if (zonasC.comprobarCamposNumericos()) {
                mostrarErrorAlerta("Los campos de los items tienen que ser numericos");

            } else {
                if (zonasC.anadirZonaDB(settings)) { // Si devuelve true es que se añadió correctamente
                    zonasTable.getItems().clear(); // Borra la tabla
                    mostrarZonas(); // Volver a generar la tabla
                    editarZonaButton.setDisable(true);
                    borrarZonaButton.setDisable(true);
                    stage.close();
                } else {
                    mostrarErrorAlerta("Algo salió mal, no se han guardado los cambios");
                }
            }
        });
    }

    @FXML
    public void editarZona() throws IOException {
        ZonasDB zonaSeleccionada = zonasTable.getSelectionModel().getSelectedItem();
        if (zonaSeleccionada != null) {
            ZonasApplication zonas = new ZonasApplication();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editar zona");
            zonas.start(stage);
            zonasC = zonas.zonasController;
            zonasC.rellenarZonaDatos(zonaSeleccionada);
            zonasC.aceptarButton.setText("Editar");
            zonasC.aceptarButton.setOnAction(actionEvent -> {
                if (zonasC.comprobarCamposVacios()) {
                    mostrarErrorAlerta("Uno o más campos estan vacíos");

                } else if (zonasC.comprobarCamposNumericos()) {
                    mostrarErrorAlerta("Los campos de los items tienen que ser numericos");

                } else {
                    if (zonasC.editarZonaDB(settings, zonaSeleccionada)) { // Si devuelve true es que se editó en la DB
                        zonasTable.getItems().clear(); // Borra la tabla
                        mostrarZonas(); // Volver a generar la tabla
                        editarZonaButton.setDisable(true);
                        borrarZonaButton.setDisable(true);
                        stage.close();
                    }  else {
                        mostrarErrorAlerta("Algo salió mal, es posible de que no se hayan guardado los cambios");
                    }
                }
            });

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText(null);
            alert.setContentText("Seleccione antes una zona para editarla");
            alert.show();
        }
    }

    @FXML
    private void borrarZona() {
        ZonasDB zonaSeleccionada = zonasTable.getSelectionModel().getSelectedItem();
        if (zonaSeleccionada != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Borrar zona");
            alert.setHeaderText(null);
            alert.setContentText("¿Estás seguro de que deseas borrar la zona seleccionada?");

            // Obtener la respuesta del usuario
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

            if (result == ButtonType.OK) {
                if (eliminarZonaDB(zonaSeleccionada)) { // Si devuelve true es que se borró de la DB
                    zonasTable.getItems().remove(zonaSeleccionada); // Borra la fila de la tabla
                    editarZonaButton.setDisable(true);
                    borrarZonaButton.setDisable(true);
                }
            }
        }
    }

    @FXML
    public void anadirUsuario() throws IOException {
        UsuariosApplication usuarios = new UsuariosApplication();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Añadir usuario");
        usuarios.start(stage);
        usuariosC = usuarios.usuariosController;
        usuariosC.aceptarButton.setText("Añadir");
        usuariosC.aceptarButton.setOnAction(actionEvent -> {
            if (usuariosC.comprobarCamposVacios()) {
                mostrarErrorAlerta("Uno o más campos estan vacíos");

            } else if (usuariosC.comprobarCamposNumericos()) {
                mostrarErrorAlerta("Los campos de los items tienen que ser numericos");

            }else if(usuariosC.existeUsuario(null)){
                mostrarErrorAlerta("El usuario ya existe");
            }else if (usuariosC.existeCorreo(null)) {
                mostrarErrorAlerta("El correo ya existe");
            }else if (usuariosC.nivelMaestriaSuperior()){
                mostrarErrorAlerta("El nivel de maestría no puede ser superior a 2000");
            }else if (!usuariosC.correoValido()){
                mostrarErrorAlerta("Introduce un correo válido ej: hola@gm.co, hola@gmail.es");

            } else {
                if (usuariosC.anadirUsuarioDB()) { // Si devuelve true es que se añadió correctamente
                    usuariosTable.getItems().clear(); // Borra la tabla
                    mostrarUsuarios(); // Volver a generar la tabla
                    editarUsuarioButton.setDisable(true);
                    borrarUsuarioButton.setDisable(true);
                    stage.close();
                } else {
                    mostrarErrorAlerta("Algo salió mal, no se han guardado los cambios");
                }
            }
        });
    }
    @FXML
    public void editarUsuario() throws IOException {
        UsuariosDB usuarioSeleccionado = usuariosTable.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado != null) {
            UsuariosApplication usuarios = new UsuariosApplication();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editar usuario");
            usuarios.start(stage);
            usuariosC = usuarios.usuariosController;
            usuariosC.rellenarUsuariosDatos(usuarioSeleccionado);
            usuariosC.aceptarButton.setText("Editar");
            usuariosC.aceptarButton.setOnAction(actionEvent -> {
                if (usuariosC.comprobarCamposVacios()) {
                    mostrarErrorAlerta("Uno o más campos estan vacíos");

                } else if (usuariosC.comprobarCamposNumericos()) {
                    mostrarErrorAlerta("Los campos de los items tienen que ser numericos");

                }else if(usuariosC.existeUsuario(usuarioSeleccionado)){
                    mostrarErrorAlerta("El usuario ya existe");

                }else if (usuariosC.existeCorreo(usuarioSeleccionado)){
                    mostrarErrorAlerta("El correo ya existe");

                }else if (usuariosC.nivelMaestriaSuperior()){
                    mostrarErrorAlerta("El nivel de maestría no puede ser superior a 2000");

                }else if (!usuariosC.correoValido()){
                    mostrarErrorAlerta("Introduce un correo válido");

                } else {
                    if (usuariosC.editarUsuariosDB(usuarioSeleccionado)) { // Si devuelve true es que se editó en la DB
                        usuariosTable.getItems().clear(); // Borra la tabla
                        mostrarUsuarios(); // Volver a generar la tabla
                        editarUsuarioButton.setDisable(true);
                        borrarUsuarioButton.setDisable(true);
                        stage.close();
                    } else {
                        mostrarErrorAlerta("Algo salió mal, es posible de que no se hayan guardado los cambios");
                    }
                }
            });

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText(null);
            alert.setContentText("Seleccione antes un usuario para editarlo");
            alert.show();
        }
    }

    @FXML
    private void borrarUsuario() {
        UsuariosDB usuarioSeleccionado = usuariosTable.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Borrar zona");
            alert.setHeaderText(null);
            alert.setContentText("¿Estás seguro de que deseas borrar al usuario seleccionado?");

            // Obtener la respuesta del usuario
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

            if (result == ButtonType.OK) {
                if (eliminarUsuarioDB(usuarioSeleccionado)) { // Si devuelve true es que se borró de la DB
                    usuariosTable.getItems().remove(usuarioSeleccionado); // Borra la fila de la tabla
                    editarUsuarioButton.setDisable(true);
                    borrarUsuarioButton.setDisable(true);
                }
            }
        }
    }

    @FXML
    public void mostrarZonas() {
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
            contrasenaColumna.setCellValueFactory(new PropertyValueFactory<>("contrasena"));
            emailColumna.setCellValueFactory(new PropertyValueFactory<>("email"));
            maestriaCarneColumna.setCellValueFactory(new PropertyValueFactory<>("maestriaCarne"));
            maestriaHierbasColumna.setCellValueFactory(new PropertyValueFactory<>("maestriaHierbas"));
            maestriaSangreColumna.setCellValueFactory(new PropertyValueFactory<>("maestriaSangre"));
            maestriaTalaColumna.setCellValueFactory(new PropertyValueFactory<>("maestriaTala"));
            administradorColumna.setCellValueFactory(new PropertyValueFactory<>("administrador"));
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
    public void cerrarSesion() {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.initModality(Modality.APPLICATION_MODAL);
        confirmDialog.initOwner(salirButton.getScene().getWindow());
        confirmDialog.setTitle("Confirmar cierre de sesión");
        confirmDialog.setHeaderText("¿Estás seguro de que quieres cerrar sesión?");

        confirmDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Crear y hacer visiblela ventana de login
                    Stage stage = new Stage();
                    LoginApplication login = new LoginApplication();
                    login.start(stage);
                    //Cerrar la actual
                    Stage ventanaActual = (Stage) salirButton.getScene().getWindow();
                    ventanaActual.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    mostrarErrorAlerta("Algo ha salido mal. Vuelve a intentarlo más tarde");
                }
            } else {
                confirmDialog.close(); // Se cierra el cuadro de diálogo
            }
        });

    }

    public static List<UsuariosDB> obtenerUsuariosDB() {
        List<UsuariosDB> usuariosList = new ArrayList<>();

        try {
            MongoCollection<Document> collection = database.getCollection("Usuarios");

            for (Document document : collection.find()) {
                UsuariosDB usuario = new UsuariosDB();
                usuario.set_id(document.getObjectId("_id"));
                usuario.setUsuario(document.getString("usuario"));
                usuario.setContrasena(document.getString("contraseña"));
                usuario.setEmail(document.getString("email"));
                usuario.setMaestriaCarne(document.getInteger("maestriaCarne"));
                usuario.setMaestriaHierbas(document.getInteger("maestriaHierbas"));
                usuario.setMaestriaSangre(document.getInteger("maestriaSangre"));
                usuario.setMaestriaTala(document.getInteger("maestriaTala"));
                usuario.setAdministrador(document.getInteger("administrador"));
                usuariosList.add(usuario); // Añadir usuario a la lista
            }
        } catch (MongoException e) {
            e.printStackTrace();
            mostrarErrorAlerta("Error de servidor. Vuelve a intentarlo más tarde");

        } catch (Exception e) {
            e.printStackTrace();
            mostrarErrorAlerta("Error de servidor. Vuelve a intentarlo más tarde");
        }

        return usuariosList;
    }

    public static List<ZonasDB> obtenerZonasDB() {
        List<ZonasDB> zonasList = new ArrayList<>();
        try {
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
                zonasList.add(zona); // Añadir zona a la lista
            }
        } catch (MongoException e) {
            e.printStackTrace();
            mostrarErrorAlerta("Error de servidor. Vuelve a intentarlo más tarde");

        } catch (Exception e) {
            e.printStackTrace();
            mostrarErrorAlerta("Error de servidor. Vuelve a intentarlo más tarde");
        }

        return zonasList;
    }

    public static Boolean eliminarZonaDB(ZonasDB zona) {
        Boolean v = false;
        try {
            MongoCollection<Document> collection = database.getCollection("Zonas");
            Bson filter = Filters.eq("_id", zona.get_id());
            collection.deleteOne(filter); // Eliminar doc de la DB
            v = true;

        } catch (MongoException e) {
            e.printStackTrace();
            mostrarErrorAlerta("Error de servidor. Vuelve a intentarlo más tarde");

        } catch (Exception e) {
            e.printStackTrace();
            mostrarErrorAlerta("Error de servidor. Vuelve a intentarlo más tarde"); //Mostrar alerta al usuario
        }
        return v;
    }

    public static Boolean eliminarUsuarioDB(UsuariosDB usuario) {
        Boolean v = false;
        try {
            MongoCollection<Document> collection = database.getCollection("Usuarios");
            Bson filter = Filters.eq("_id", usuario.get_id());
            collection.deleteOne(filter); // Eliminar doc de la DB
            v = true;

        } catch (MongoException e) {
            e.printStackTrace();
            mostrarErrorAlerta("Error de servidor. Vuelve a intentarlo más tarde");

        } catch (Exception e) {
            e.printStackTrace();
            mostrarErrorAlerta("Error de servidor. Vuelve a intentarlo más tarde"); //Mostrar alerta al usuario
        }
        return v;
    }

    public void activarEditarBorrarZona() {
        ZonasDB zonaSeleccionada = zonasTable.getSelectionModel().getSelectedItem();
        if (zonaSeleccionada != null) {
            // Si hay una fila seleccionada habilita los botones
            editarZonaButton.setDisable(false);
            borrarZonaButton.setDisable(false);

            // Crear el menú contextual y las opciones de menú
            ContextMenu contextMenu = new ContextMenu();
            MenuItem editarMenuItem = new MenuItem("Editar");
            MenuItem borrarMenuItem = new MenuItem("Borrar");

            editarMenuItem.setOnAction(event -> {
                try {
                    editarZona();
                } catch (IOException e) {
                    e.printStackTrace();
                    mostrarErrorAlerta("Algo salió mal, no se han guardado los cambios");
                }
            });

            borrarMenuItem.setOnAction(event -> {
                borrarZona();
            });

            // Agregar las opciones al menú
            contextMenu.getItems().addAll(editarMenuItem, borrarMenuItem);

            zonasTable.setContextMenu(contextMenu); // Asignar el menú a la tabla
        } else {
            // Si no hay fila seleccionada, deshabilita los botones y elimina el menú contextual
            editarZonaButton.setDisable(true);
            borrarZonaButton.setDisable(true);
            zonasTable.setContextMenu(null);
        }
    }


    public void activarEditarBorrarUsuario() {
        UsuariosDB usuarioSeleccionado = usuariosTable.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado != null) {
            // Si hay una fila seleccionada, habilita los botones
            editarUsuarioButton.setDisable(false);
            borrarUsuarioButton.setDisable(false);

            // Crear el menú contextual y las opciones de menú
            ContextMenu contextMenu = new ContextMenu();
            MenuItem editarMenuItem = new MenuItem("Editar");
            MenuItem borrarMenuItem = new MenuItem("Borrar");

            editarMenuItem.setOnAction(event -> {
                try {
                    editarUsuario();
                } catch (IOException e) {
                    e.printStackTrace();
                    mostrarErrorAlerta("Algo salió mal, no se han guardado los cambios");
                }
            });

            borrarMenuItem.setOnAction(event -> {
                borrarUsuario();
            });

            // Agregar las opciones al menú contextual
            contextMenu.getItems().addAll(editarMenuItem, borrarMenuItem);

            // Asignar el menú contextual a la tabla
            usuariosTable.setContextMenu(contextMenu);
        } else {
            // Si no hay fila seleccionada, deshabilita los botones y elimina el menú contextual
            editarUsuarioButton.setDisable(true);
            borrarUsuarioButton.setDisable(true);
            usuariosTable.setContextMenu(null);
        }
    }


    public static void mostrarErrorAlerta(String titulo) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(titulo);
        alert.show();
    }
}
