package com.catquest.controllers;

import com.catquest.Main;
import com.catquest.helpers.Audio;
import com.catquest.helpers.Controls;
import com.catquest.screens.SettingsScreen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControlsController implements Initializable {

    @FXML
    private VBox controls;

    /**
     * Generates a row for the controls screen
     *
     * @param action Corresponds to the action that the key is bound to
     * @param binding Corresponds to the key that the action is bound to
     * @return A HBox that contains the action, a spacer, and the binding
     */
    private HBox generateItem(String action, String binding) {
        HBox row = new HBox();
        row.getStyleClass().add("item");
        Label actionLabel = new Label(action);
        actionLabel.getStyleClass().add("label-text");
        Label bindingLabel = new Label(binding);
        bindingLabel.getStyleClass().add("item-key");
        Region region = new Region();
        HBox.setHgrow(region, javafx.scene.layout.Priority.ALWAYS);
        row.getChildren().addAll(actionLabel, region, bindingLabel);
        row.setOnMouseClicked(event -> handleRowClick(action, bindingLabel));
        row.setAlignment(Pos.CENTER);
        return row;
    }

    /**
     * Generates all rows for the controls screen using the Bindings map
     */
    private void generateRows() {
        controls.getChildren().clear();
        for (String key : Controls.bindings.keySet()) {
            controls.getChildren().add(generateItem(key, Controls.bindings.get(key)));
        }
    }

    /**
     * Handles the click event for a row and opens a new window to bind a key to an action
     *
     * @param action The action that the key is bound to
     * @param bindingLabel The label that displays the key that the action is bound to
     */
    private void handleRowClick(String action, Label bindingLabel) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(Main.stage);

        // Create a VBox layout
        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #241044; -fx-padding: 20px; -fx-spacing: 20px;");
        layout.setPrefSize(500, 200);
        layout.setAlignment(Pos.CENTER);

        // Close button
        SVGPath svg = new SVGPath();
        svg.setContent("M1 1L19 19M1 19L19 1");
        svg.setStroke(Paint.valueOf("#ffffff"));
        svg.setStrokeWidth(4);
        svg.setStrokeLineCap(javafx.scene.shape.StrokeLineCap.ROUND);
        svg.setStrokeLineJoin(javafx.scene.shape.StrokeLineJoin.ROUND);
        StackPane closeIcon = new StackPane(svg);
        closeIcon.setStyle("-fx-cursor: hand;");
        closeIcon.setOnMouseClicked(event -> stage.close());

        HBox closeButtonContainer = new HBox(closeIcon);
        closeButtonContainer.setAlignment(Pos.TOP_RIGHT);
        layout.getChildren().add(closeButtonContainer);

        // Label for the title
        Label label = new Label("Press a key to bind \"" + action + "\"");
        label.setStyle("-fx-font-family: \"Sofadi One\"; -fx-font-size: 24px; -fx-text-fill: #ffffff;");
        label.setAlignment(Pos.CENTER);
        layout.getChildren().add(label);

        // Key pressed box
        HBox keyPressed = new HBox();
        keyPressed.setAlignment(Pos.CENTER);
        keyPressed.setPrefSize(100, 50);
        keyPressed.setStyle("-fx-background-color: #9B73AD; -fx-font-size: 24px; -fx-text-fill: #ffffff;");
        keyPressed.getChildren().add(new Label(bindingLabel.getText()));
        layout.getChildren().add(keyPressed);

        Scene scene = new Scene(layout);

        scene.setOnKeyPressed(event -> {
            keyPressed.getChildren().clear();
            keyPressed.getChildren().add(new Label(event.getCode().toString()));
            if (Controls.update(action, event.getCode().toString())) {
                this.generateRows();
                stage.close();
            } else {
                keyPressed.getChildren().clear();
                keyPressed.getChildren().add(new Label("Key already in use"));
                Audio.errorSoundEffect();
            }
        });

        stage.setScene(scene);
        stage.initStyle(javafx.stage.StageStyle.UNDECORATED);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.generateRows();
    }

    @FXML
    public void onBackButtonClick() throws IOException {
        Audio.buttonSoundEffect();
        Main.stage.setScene(SettingsScreen.getScene());
    }
}
