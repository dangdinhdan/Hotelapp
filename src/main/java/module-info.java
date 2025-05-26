module com.example.hotelloginapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires java.desktop;
    opens com.example.hotelloginapp.models to javafx.base;
    opens com.example.hotelloginapp.controller to javafx.fxml;
    opens com.example.hotelloginapp to javafx.graphics, javafx.fxml;
//    exports com.example.hotellogin;
    exports com.example.hotelloginapp;
    exports com.example.hotelloginapp.controller;
}
