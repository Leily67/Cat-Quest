package com.catquest.controllers;

import com.catquest.Main;
import com.catquest.helpers.Audio;
import com.catquest.screens.GameMenuScreen;
import com.catquest.screens.SettingsScreen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameMenuAudioController implements Initializable {
    @FXML private Label musicVolumeLabel;
    @FXML private Label effectsVolumeLabel;

    public void initialize(URL var1, ResourceBundle var2) {
        this.musicVolumeLabel.setText(String.valueOf(Audio.musicVolume));
        this.effectsVolumeLabel.setText(String.valueOf(Audio.effectsVolume));
    }

    @FXML
    private void decreaseMusicVolume() {
        Audio.musicVolume = Math.max(Audio.musicVolume - 5, 0);
        musicVolumeLabel.setText(String.valueOf(Audio.musicVolume));
        Audio.save();
    }

    @FXML
    private void increaseMusicVolume() {
        Audio.musicVolume = Math.min(Audio.musicVolume + 5, 100);
        musicVolumeLabel.setText(String.valueOf(Audio.musicVolume));
        Audio.save();
    }

    @FXML
    private void decreaseEffectsVolume() {
        Audio.effectsVolume = Math.max(Audio.effectsVolume - 5, 0);
        effectsVolumeLabel.setText(String.valueOf(Audio.effectsVolume));
        Audio.save();
    }

    @FXML
    private void increaseEffectsVolume() {
        Audio.effectsVolume = Math.min(Audio.effectsVolume + 5, 100);
        effectsVolumeLabel.setText(String.valueOf(Audio.effectsVolume));
        Audio.save();
    }

    @FXML
    private void handleBackButtonAction() throws IOException {
        Audio.buttonSoundEffect();
        Main.stage.setScene(GameMenuScreen.getScene());
    }
}
