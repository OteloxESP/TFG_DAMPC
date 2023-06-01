package com.example.demobdoinfoescritorio;

import com.example.demobdoinfoescritorio.db.UsuariosDB;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.List;


public class InicioController {
    @FXML
    private void initialize() {
        // Inicializar controlador
    }
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

    private UsuariosDB obtenerUsuariosDB(){
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(conexionURL))
                .serverApi(serverApi)
                .build();
        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                MongoDatabase database = mongoClient.getDatabase("bdoHelp");
                MongoCollection<Document> collection = database.getCollection("Usuarios");
                collection.find().iterator();



            } catch (MongoException e) {
                e.printStackTrace();
            }
        }

        return UsuariosDB;
    }
}
