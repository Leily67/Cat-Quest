package com.catquest.controllers;

import com.catquest.Main;
import com.catquest.game.Game;
import com.catquest.game.Loader;
import com.catquest.game.Save;
import com.catquest.helpers.Audio;
import com.catquest.game.tasks.StartGame;
import com.catquest.screens.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;

public class HomeController implements Initializable {

    @FXML
    private VBox buttonContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!Save.exists()) {
            buttonContainer.getChildren().remove(0);
        }
    }

    @FXML
    protected void onContinueButtonClick() throws IOException {
        Audio.startGameSoundEffect();
        if (Save.exists()) {
            Loader.show();
            new Timer().schedule(new StartGame(false), 1000);
        }
    }

    @FXML
    protected void onNewGameButtonClick() throws IOException {
        Audio.buttonSoundEffect();
        if (!Save.exists()) {
            Main.stage.setScene(NewGameScreen.getScene());
        } else {
            Main.stage.setScene(ConfirmNewGameScreen.getScene());
        }
    }

    @FXML
    protected void onSettingsButtonClick() throws IOException {
        Audio.buttonSoundEffect();
        Main.stage.setScene(SettingsScreen.getScene());
    }

    @FXML
    protected void onQuitButtonClick() {
        Audio.buttonSoundEffect();
        System.exit(0);
    }
}