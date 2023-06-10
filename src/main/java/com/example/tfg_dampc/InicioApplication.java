package com.example.tfg_dampc;

import com.example.tfg_dampc.db.UsuariosDB;
import com.example.tfg_dampc.db.ZonasDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioApplication extends Application {
    private TableView<UsuariosDB> usuariosTable;
    private InicioController inicioController;
    private Button inicioButton;
    private Button usuariosButton;
    private Button salirButton;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("inicio-view.fxml"));
        Parent root = loader.load();
        inicioController = loader.getController();

        // Obtener los 3 botones y añadir un icono a estos
        inicioButton = inicioController.inicioButton;
        ImageView imageViewHome = new ImageView(new Image(getClass().getResourceAsStream("home.png")));
        imageViewHome.setFitHeight(20);
        imageViewHome.setFitWidth(20);
        inicioButton.setGraphic(imageViewHome);
        usuariosButton = inicioController.usuariosButton;
        ImageView imageViewUser = new ImageView(new Image(getClass().getResourceAsStream("user.png")));
        imageViewUser.setFitHeight(20);
        imageViewUser.setFitWidth(20);
        usuariosButton.setGraphic(imageViewUser);
        salirButton = inicioController.salirButton;
        ImageView imageViewSalir = new ImageView(new Image(getClass().getResourceAsStream("salir.png")));
        imageViewSalir.setFitHeight(20);
        imageViewSalir.setFitWidth(20);
        salirButton.setGraphic(imageViewSalir);
        Scene scene = new Scene(root, 820, 640);
        stage.setTitle("Inicio");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("logo.png")));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        inicioController.mostrarInicio();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
