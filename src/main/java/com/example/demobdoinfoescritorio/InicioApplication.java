package com.example.demobdoinfoescritorio;

import com.example.demobdoinfoescritorio.db.UsuariosDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioApplication extends Application {
    private TableView<UsuariosDB> usuariosTable;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("inicio-view.fxml"));
        Scene scene = new Scene(root, 620, 540);

        stage.setTitle("Inicio");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
