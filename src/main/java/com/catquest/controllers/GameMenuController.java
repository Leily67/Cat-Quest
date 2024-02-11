package com.catquest.controllers;

import com.catquest.Main;
import com.catquest.game.Menu;
import com.catquest.helpers.Audio;
import com.catquest.screens.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameMenuController implements Initializable {
    public void initialize(URL var1, ResourceBundle var2) {
    }

    @FXML
    private void handleAudioButtonAction() throws IOException {
        Audio.buttonSoundEffect();
        Main.stage.setScene(GameMenuAudioScreen.getScene());
    }

    @FXML
    private void handleExitButtonAction() {
        Audio.buttonSoundEffect();
        GameController.game.save();
        System.exit(0);
    }

    @FXML
    private void handleControlButtonAction() throws IOException {
        Audio.buttonSoundEffect();
        Main.stage.setScene(GameMenuControlsScreen.getScene());
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        Audio.buttonSoundEffect();
        Menu.hide();
    }
}
