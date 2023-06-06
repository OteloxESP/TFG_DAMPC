package com.example.tfg_dampc;

import com.example.tfg_dampc.db.UsuariosDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioApplication extends Application {
    private TableView<UsuariosDB> usuariosTable;
    private InicioController inicioController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("inicio-view.fxml"));
        Parent root = loader.load();
        inicioController = loader.getController();
        Scene scene = new Scene(root, 820, 640);
        stage.setTitle("Inicio");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        inicioController.mostrarInicio();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
