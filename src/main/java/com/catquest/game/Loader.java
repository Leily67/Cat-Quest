package com.catquest.game;

import com.catquest.Main;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.SVGPath;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Loader {

    public static Stage stage;

    private static void initialize() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(Main.stage);

        // Create a VBox layout
        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #241044; -fx-padding: 5px; -fx-spacing: 20px;");
        layout.setPrefSize(500, 200);
        layout.setAlignment(Pos.CENTER);

        // Label for the title
        Label label = new Label("Please wait while we load your game...");
        label.setStyle("-fx-font-family: \"Sofadi One\"; -fx-font-size: 23px; -fx-text-fill: #ffffff;");
        label.setAlignment(Pos.CENTER);
        layout.getChildren().add(label);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.initStyle(javafx.stage.StageStyle.UNDECORATED);
        Loader.stage = stage;
    }

    public static void show() {
        Loader.initialize();
        stage.show();
    }

    public static void hide() {
        stage.close();
    }
}
