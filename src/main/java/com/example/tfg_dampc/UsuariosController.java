package com.example.tfg_dampc;

import com.example.tfg_dampc.db.UsuariosDB;
import com.example.tfg_dampc.db.ZonasDB;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import net.synedra.validatorfx.Check;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.mindrot.jbcrypt.BCrypt;

public class UsuariosController {
    public Button aceptarButton;
    public Button cancelarButton;
    @FXML
    private Node rootNode;
    @FXML
    public TextField usuarioTextField;
    @FXML
    public TextField correoTextField;
    @FXML
    public TextField contraseñaTextField;
    @FXML
    public TextField talaTextField;
    @FXML
    public TextField carneTextField;
    @FXML
    public TextField sangreTextField;
    @FXML
    public TextField hierbasTextField;
    @FXML
    public CheckBox administradorCheck;

    public void initialize() {
        // Aplicar tooltips
        usuarioTextField.setTooltip(new Tooltip("Introduce el usuario"));
        /*item1TextField.setTooltip(new Tooltip("Multiplicador por cada 50 de maestría (ej: (160/50=3)*multiplicador"));
        item2TextField.setTooltip(new Tooltip("Multiplicador por cada 50 de maestría (ej: (160/50=3)*multiplicador"));
        item3TextField.setTooltip(new Tooltip("Multiplicador por cada 50 de maestría (ej: (160/50=3)*multiplicador"));
        item4TextField.setTooltip(new Tooltip("Multiplicador por cada 50 de maestría (ej: (160/50=3)*multiplicador"));*/
    }

    public boolean añadirUsuarioDB(MongoClientSettings settings){
        boolean v = false;

        try {
            MongoClient mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("bdoHelp");
            MongoCollection<Document> collection = database.getCollection("Usuarios");

            String contrasenaEncriptada = BCrypt.hashpw(contraseñaTextField.getText(), BCrypt.gensalt());
            int admin = 0;
            if (administradorCheck.isSelected()){
                admin = 1;
            }
            // Crear un documento con los datos de la zona
            Document usuarioDocument = new Document()
                    .append("usuario", usuarioTextField.getText())
                    .append("email", correoTextField.getText())
                    .append("contraseña", contrasenaEncriptada)
                    .append("maestriaCarne", Integer.parseInt(carneTextField.getText()))
                    .append("maestriaHierbas", Integer.parseInt(hierbasTextField.getText()))
                    .append("maestriaSangre", Integer.parseInt(sangreTextField.getText()))
                    .append("maestriaTala", Integer.parseInt(talaTextField.getText()))
                    .append("administrador", admin);

            collection.insertOne(usuarioDocument); // Insertar el documento en la colección
            v = true;

        } catch (MongoException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        return v;
    }

    public boolean editarUsuariosDB(MongoClientSettings settings, UsuariosDB usuario) {
        boolean v = false;

        try {
            MongoClient mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("bdoHelp");
            MongoCollection<Document> collection = database.getCollection("Usuarios");
            Bson filter = Filters.eq("_id", usuario.get_id()); // Crear un Bson filter para identificar el documento a actualizar
            int admin = 0;
            if (administradorCheck.isSelected()){
                admin = 1;
            }
            // Crear un documento con los campos a actualizar
            Bson cambios = new Document()
                    .append("usuario", usuarioTextField.getText())
                    .append("email", correoTextField.getText())
                    .append("maestriaCarne", Integer.parseInt(carneTextField.getText()))
                    .append("maestriaHierbas", Integer.parseInt(hierbasTextField.getText()))
                    .append("maestriaSangre", Integer.parseInt(sangreTextField.getText()))
                    .append("maestriaTala", Integer.parseInt(talaTextField.getText()))
                    .append("administrador", admin);
            Bson update = new Document("$set", cambios);

            UpdateResult result = collection.updateOne(filter, update); // Realizar la update en la db.
            v = true;

        } catch (MongoException e) {
            e.printStackTrace();
            v = false;

        } catch (Exception e){
            e.printStackTrace();
            v = false;
        }

        return v;
    }

    public void cerrarZonasVentana(){
        Stage ventana = (Stage) rootNode.getScene().getWindow();
        ventana.close();
    }

    public void rellenarUsuariosDatos(UsuariosDB usuarioSeleccionado) {
        // Obtener y rellenar los campos con los datos de la zona
        usuarioTextField.setText(usuarioSeleccionado.getUsuario());
        correoTextField.setText(usuarioSeleccionado.getEmail());
        contraseñaTextField.setText(usuarioSeleccionado.getContraseña());
        contraseñaTextField.setEditable(false);
        if (usuarioSeleccionado.getAdministrador()==1){
            administradorCheck.setSelected(true);
        }else{
            administradorCheck.setSelected(false);
        }

        carneTextField.setText(String.valueOf(usuarioSeleccionado.getMaestriaCarne()));
        hierbasTextField.setText(String.valueOf(usuarioSeleccionado.getMaestriaHierbas()));
        sangreTextField.setText(String.valueOf(usuarioSeleccionado.getMaestriaSangre()));
        talaTextField.setText(String.valueOf(usuarioSeleccionado.getMaestriaTala()));
    }

    public boolean comprobarCamposVacios() {
        Boolean v = false;
        // Comprobar si hay algún campo vacío y si es asi señalarlo en rojo
        if (usuarioTextField.getText().isEmpty()){
            v = true;
            usuarioTextField.setStyle("-fx-border-color: red;");
        }else{
            usuarioTextField.setStyle(null);
        }
        if (correoTextField.getText().isEmpty()){
            v = true;
            correoTextField.setStyle("-fx-border-color: red;");
        }else{
            correoTextField.setStyle(null);
        }
        if (contraseñaTextField.getText().isEmpty()){
            v = true;
            contraseñaTextField.setStyle("-fx-border-color: red;");
        }else{
            contraseñaTextField.setStyle(null);
        }
        if (carneTextField.getText().isEmpty()){
            v = true;
            carneTextField.setStyle("-fx-border-color: red;");
        }else{
            carneTextField.setStyle(null);
        }
        if (hierbasTextField.getText().isEmpty()){
            v = true;
            hierbasTextField.setStyle("-fx-border-color: red;");
        }else{
            hierbasTextField.setStyle(null);
        }
        if (sangreTextField.getText().isEmpty()){
            v = true;
            sangreTextField.setStyle("-fx-border-color: red;");
        }else{
            sangreTextField.setStyle(null);
        }
        if (talaTextField.getText().isEmpty()){
            v = true;
            talaTextField.setStyle("-fx-border-color: red;");
        }else{
            talaTextField.setStyle(null);
        }

        return v;
    }

    public boolean comprobarCamposNumericos(){
        Boolean v = false;

        // Comprobar si los campos numéricos tienen otra cosa que no sean dígitos
        if (!carneTextField.getText().matches("\\d*")){
            v = true;
            carneTextField.setStyle("-fx-border-color: red;");
        }else{
            carneTextField.setStyle(null);
        }
        if (!hierbasTextField.getText().matches("\\d*")){
            v = true;
            hierbasTextField.setStyle("-fx-border-color: red;");
        }else{
            hierbasTextField.setStyle(null);
        }
        if (!sangreTextField.getText().matches("\\d*")){
            v = true;
            sangreTextField.setStyle("-fx-border-color: red;");
        }else{
            sangreTextField.setStyle(null);
        }
        if (!talaTextField.getText().matches("\\d*")){
            v = true;
            talaTextField.setStyle("-fx-border-color: red;");
        }else{
            talaTextField.setStyle(null);
        }

        return v;
    }

    @FXML
    private void teclaPulsada(KeyEvent event){
        // Si la tecla es el Enter
        if (event.getCode() == KeyCode.ENTER) {
            aceptarButton.fire();
        }
    }
}
