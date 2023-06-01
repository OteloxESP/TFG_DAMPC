package com.example.demobdoinfoescritorio;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class LoginController {
    String conexionURL = "mongodb+srv://Otelox:I3LvJTkOkZsqDm4j@cluster0.shwupsp.mongodb.net/?retryWrites=true&w=majority";
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
        // Inicializar controlador
    }

    @FXML
    private void loginButtonClicked() {
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
                String usuario = usernameField.getText();
                String contraseña = passwordField.getText();

                // Buscar el usuario y contraseña en la colección
                Document query = new Document("usuario", usuario);
                Document user = collection.find(query).first();

                if (user != null) {
                    String contraseñaDB = user.getString("contraseña");

                    if (BCrypt.checkpw(contraseña, contraseñaDB)) {
                        System.out.println("Inicio de sesión exitoso");
                        try {
                            Stage stage = new Stage();
                            InicioApplication inicio = new InicioApplication();
                            inicio.start(stage);
                            loginButton.getScene().getWindow().hide();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        System.out.println("Credenciales incorrectas 2");
                    }
                } else {
                    System.out.println("Credenciales incorrectas 1");
                }


            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }
}
