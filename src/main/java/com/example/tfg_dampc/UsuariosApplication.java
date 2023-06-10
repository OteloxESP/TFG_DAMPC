package com.example.tfg_dampc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class UsuariosApplication extends Application {
    public UsuariosController usuariosController;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("usuarios-view.fxml"));
        Parent root = loader.load();
        usuariosController = loader.getController();
        Scene scene = new Scene(root, 360, 330);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("logo.png")));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
