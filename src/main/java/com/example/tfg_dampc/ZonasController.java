package com.example.tfg_dampc;

import com.example.tfg_dampc.db.ZonasDB;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.conversions.Bson;

public class ZonasController {
    public Button aceptarButton;
    public Button cancelarButton;
    @FXML
    private Node rootNode;
    @FXML
    public TextField nombreZonaTextField;
    @FXML
    public ChoiceBox tipoZonaChoiceBox;
    @FXML
    public TextField item1TextField;
    @FXML
    public TextField item2TextField;
    @FXML
    public TextField item3TextField;
    @FXML
    public TextField item4TextField;
    static MongoClientSettings settings;
    static MongoDatabase database;
    static String conexionURL = "mongodb+srv://Otelox:I3LvJTkOkZsqDm4j@cluster0.shwupsp.mongodb.net/?retryWrites=true&w=majority";
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
            e.printStackTrace();
        }

        tipoZonaChoiceBox.getItems().addAll("Tala","Hierbas","Sangre","Carne"); // Añadir las opciones al selector

        // Aplicar tooltips
        nombreZonaTextField.setTooltip(new Tooltip("Introduce el nombre de la zona"));
        item1TextField.setTooltip(new Tooltip("Multiplicador por cada 50 de maestría (ej: (160/50=3)*multiplicador"));
        item2TextField.setTooltip(new Tooltip("Multiplicador por cada 50 de maestría (ej: (160/50=3)*multiplicador"));
        item3TextField.setTooltip(new Tooltip("Multiplicador por cada 50 de maestría (ej: (160/50=3)*multiplicador"));
        item4TextField.setTooltip(new Tooltip("Multiplicador por cada 50 de maestría (ej: (160/50=3)*multiplicador"));
    }

    public boolean anadirZonaDB(MongoClientSettings settings){
        boolean v = false;

        try {
            MongoCollection<Document> collection = database.getCollection("Zonas");

            // Crear un documento con los datos de la zona
            Document zonaDocument = new Document()
                    .append("nombre", nombreZonaTextField.getText())
                    .append("tipoZona", tipoZonaChoiceBox.getValue())
                    .append("item1", Integer.parseInt(item1TextField.getText()))
                    .append("item2", Integer.parseInt(item2TextField.getText()))
                    .append("item3", Integer.parseInt(item3TextField.getText()))
                    .append("item4", Integer.parseInt(item4TextField.getText()));

            collection.insertOne(zonaDocument); // Insertar el documento en la colección
            v = true;

        } catch (MongoException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        return v;
    }

    public boolean editarZonaDB(MongoClientSettings settings, ZonasDB zona) {
        Boolean v = false;

        try {
            MongoCollection<Document> collection = database.getCollection("Zonas");
            Bson filter = Filters.eq("_id", zona.get_id()); // Crear un Bson filter para identificar el documento a actualizar

            // Crear un documento con los campos a actualizar
            Bson cambios = new Document()
                    .append("nombre", nombreZonaTextField.getText())
                    .append("tipoZona", tipoZonaChoiceBox.getValue())
                    .append("item1", Integer.parseInt(item1TextField.getText()))
                    .append("item2", Integer.parseInt(item2TextField.getText()))
                    .append("item3", Integer.parseInt(item3TextField.getText()))
                    .append("item4", Integer.parseInt(item4TextField.getText()));
            Bson update = new Document("$set", cambios);

            collection.updateOne(filter, update); // Realizar la update en la db.
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

    public void rellenarZonaDatos(ZonasDB zonaSeleccionada) {
        // Obtener y rellenar los campos con los datos de la zona
        nombreZonaTextField.setText(zonaSeleccionada.getNombre());
        tipoZonaChoiceBox.setValue(zonaSeleccionada.getTipoZona());
        item1TextField.setText(String.valueOf(zonaSeleccionada.getItem1()));
        item2TextField.setText(String.valueOf(zonaSeleccionada.getItem2()));
        item3TextField.setText(String.valueOf(zonaSeleccionada.getItem3()));
        item4TextField.setText(String.valueOf(zonaSeleccionada.getItem4()));
    }

    public boolean comprobarCamposVacios() {
        Boolean v = false;
        // Comprobar si hay algún campo vacío y si es asi señalarlo en rojo
        if (nombreZonaTextField.getText().isEmpty()){
            v = true;
            nombreZonaTextField.setStyle("-fx-border-color: red;");
        }else{
            nombreZonaTextField.setStyle(null);
        }
        if (tipoZonaChoiceBox.getValue() == null){
            v = true;
            tipoZonaChoiceBox.setStyle("-fx-border-color: red;");
        }else{
            tipoZonaChoiceBox.setStyle(null);
        }
        if (item1TextField.getText().isEmpty()){
            v = true;
            item1TextField.setStyle("-fx-border-color: red;");
        }else{
            item1TextField.setStyle(null);
        }
        if (item2TextField.getText().isEmpty()){
            v = true;
            item2TextField.setStyle("-fx-border-color: red;");
        }else{
            item2TextField.setStyle(null);
        }
        if (item3TextField.getText().isEmpty()){
            v = true;
            item3TextField.setStyle("-fx-border-color: red;");
        }else{
            item3TextField.setStyle(null);
        }
        if (item4TextField.getText().isEmpty()){
            v = true;
            item4TextField.setStyle("-fx-border-color: red;");
        }else{
            item4TextField.setStyle(null);
        }

        return v;
    }

    public boolean comprobarCamposNumericos(){
        Boolean v = false;

        // Comprobar si los campos numéricos tienen otra cosa que no sean dígitos
        if (!item1TextField.getText().matches("\\d*")){
            v = true;
            item1TextField.setStyle("-fx-border-color: red;");
        }else{
            item1TextField.setStyle(null);
        }
        if (!item2TextField.getText().matches("\\d*")){
            v = true;
            item2TextField.setStyle("-fx-border-color: red;");
        }else{
            item2TextField.setStyle(null);
        }
        if (!item3TextField.getText().matches("\\d*")){
            v = true;
            item3TextField.setStyle("-fx-border-color: red;");
        }else{
            item3TextField.setStyle(null);
        }
        if (!item4TextField.getText().matches("\\d*")){
            v = true;
            item4TextField.setStyle("-fx-border-color: red;");
        }else{
            item4TextField.setStyle(null);
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
