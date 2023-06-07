package com.example.tfg_dampc;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.bson.Document;
import org.controlsfx.dialog.ProgressDialog;
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
    private Label errorLabel;

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

        // Crear un cliente y conectarlo al servidor.
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                MongoDatabase database = mongoClient.getDatabase("bdoHelp"); // Obtener base de datos
                MongoCollection<Document> collection = database.getCollection("Usuarios"); // Obtener la colección de usuarios

                // Obtener los valores de los campos
                String usuario = usernameField.getText();
                String contrasena = passwordField.getText();

                // Comprobar si los campos están vacíos
                if (usuario.isEmpty() || contrasena.isEmpty()) {
                    mostrarErrorLabel("Ingrese el usuario y la contraseña"); // Mostrar label de aviso al usuario

                } else {
                    ocultarErrorLabel(); //Ocultar label al usuario y quitar estilos a los campos

                    // Buscar el usuario y contrasena en la colección
                    Document query = new Document("usuario", usuario);
                    Document user = collection.find(query).first();

                    // Comprobar si el usuario coincide
                    if (user != null) {
                        String contrasenaDB = user.getString("contraseña");

                        // Comprobar si la contrasena coincide
                        if (BCrypt.checkpw(contrasena, contrasenaDB)) {
                            System.out.println("Inicio de sesión exitoso");
                            try {
                                // Crear la nueva ventana y ocultar la actual
                                Stage stage = new Stage();
                                InicioApplication inicio = new InicioApplication();
                                inicio.start(stage);
                                loginButton.getScene().getWindow().hide();

                            } catch (IOException e) {
                                e.printStackTrace();
                                ocultarErrorLabel(); //Ocultar label al usuario y quitar estilos a los campos
                                mostrarErrorAlerta("Error de servidor"); //Mostrar alerta al usuario
                            }

                        } else {
                            System.out.println("Credenciales incorrectas 2");
                            mostrarErrorLabel("No existe un usuario con ese usuario y/o contraseña"); // Mostrar label de aviso al usuario
                        }
                    } else {
                        System.out.println("Credenciales incorrectas 1");
                        mostrarErrorLabel("No existe un usuario con ese usuario y/o contraseña"); // Mostrar label de aviso al usuario
                    }
                }

            } catch (MongoException e) {
                e.printStackTrace();
                ocultarErrorLabel(); //Ocultar label al usuario y quitar estilos a los campos
                mostrarErrorAlerta("Error de servidor"); //Mostrar alerta al usuario
            }
        }
    }

    public void mostrarErrorLabel(String titulo){
        errorLabel.setText(titulo);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);

        // Añadir los bordes rojos a los campos
        usernameField.setStyle("-fx-border-color: red;");
        passwordField.setStyle("-fx-border-color: red;");
    }

    public void ocultarErrorLabel(){
        // Restaurar estilo de los campos de texto
        usernameField.setStyle("");
        passwordField.setStyle("");

        // Ocultar el errorLabel
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }

    public void mostrarErrorAlerta(String titulo){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de servidor");
        alert.setHeaderText(null);
        alert.setContentText(titulo);
        alert.show();
    }
}
