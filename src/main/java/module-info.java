module com.example.demobdoinfoescritorio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires jbcrypt;

    opens com.example.bdoInfoDesktop to javafx.fxml;
    opens com.example.bdoInfoDesktop.db to javafx.base;
    exports com.example.bdoInfoDesktop;
}