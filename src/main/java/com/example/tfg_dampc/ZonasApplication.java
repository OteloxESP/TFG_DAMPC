package com.example.tfg_dampc;

import com.example.tfg_dampc.db.ZonasDB;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ZonasApplication extends Application {
    public ZonasController zonasController;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("zonas-view.fxml"));
        Parent root = loader.load();
        zonasController = loader.getController();
        Scene scene = new Scene(root, 360, 330);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("logo.png")));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
