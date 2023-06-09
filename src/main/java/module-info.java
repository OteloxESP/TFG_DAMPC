module com.example.tfg_dampc {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires jbcrypt;

    opens com.example.tfg_dampc to javafx.fxml;
    opens com.example.tfg_dampc.db to javafx.base;
    exports com.example.tfg_dampc;
}