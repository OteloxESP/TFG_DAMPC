package com.example.bdoInfoDesktop;

import com.example.bdoInfoDesktop.db.UsuariosDB;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.control.TableView;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class InicioController {
    @FXML
    private void initialize() {
        // Inicializar controlador
    }
    static String conexionURL = "mongodb+srv://Otelox:I3LvJTkOkZsqDm4j@cluster0.shwupsp.mongodb.net/?retryWrites=true&w=majority";
    @FXML
    private Pane inicioPane;

    @FXML
    private Pane usuariosPane;

    @FXML
    private Pane configuracionPane;

    @FXML
    private TableView<UsuariosDB> usuariosTable;
    @FXML
    private void mostrarInicio() {
        inicioPane.setVisible(true);
        usuariosPane.setVisible(false);
        configuracionPane.setVisible(false);
    }

    @FXML
    private void mostrarUsuarios() {
        inicioPane.setVisible(false);
        usuariosPane.setVisible(true);
        configuracionPane.setVisible(false);
        List<UsuariosDB> listaUsuarios = obtenerUsuariosDB();

    }

    @FXML
    private void mostrarConfiguracion() {
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
                    usuario.setContraseña(document.getString("contaseña"));
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

}
