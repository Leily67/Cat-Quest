package com.catquest.controllers;

import com.catquest.Main;
import com.catquest.helpers.Audio;
import com.catquest.screens.AudioScreen;
import com.catquest.screens.ControlsScreen;
import com.catquest.screens.HomeScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private void handleAudioButtonAction() throws IOException {
        Audio.buttonSoundEffect();
        Main.stage.setScene(AudioScreen.getScene());
    }

    @FXML
    private void handleControlButtonAction() throws IOException {
        Audio.buttonSoundEffect();
        Main.stage.setScene(ControlsScreen.getScene());
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        Audio.buttonSoundEffect();
        Main.stage.setScene(HomeScreen.getScene());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}