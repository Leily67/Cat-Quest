package com.catquest.controllers;

import com.catquest.entities.*;
import com.catquest.game.Game;
import com.catquest.helpers.Audio;
import com.catquest.game.Loader;
import com.catquest.game.tasks.StartGame;
import com.catquest.screens.HomeScreen;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.catquest.Main;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;

public class NewGameController {

    public TextField name;
    public Text flash;

    @FXML
    protected void onStartButtonClick() {
        Audio.startGameSoundEffect();
        if (name.getText().isEmpty()) {
            name.setPromptText("Hey you need a name :<");
        } else {
            Loader.show();
            new Timer().schedule(new StartGame(true, name.getText()), 100);
        }
    }

    @FXML
    protected void onReturnButtonClick() throws IOException {
        Audio.buttonSoundEffect();
        Main.stage.setScene(HomeScreen.getScene());
    }
}