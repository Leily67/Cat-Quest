module com.catquest.catquest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires json.simple;
    requires javafx.media;

    opens com.catquest to javafx.fxml;
    exports com.catquest;
    opens com.catquest.screens to javafx.fxml;
    exports com.catquest.screens;
    opens com.catquest.controllers to javafx.fxml;
    exports com.catquest.controllers;
}