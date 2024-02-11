package com.catquest.controllers;

import com.catquest.Main;
import com.catquest.helpers.Audio;
import com.catquest.screens.HomeScreen;
import com.catquest.screens.NewGameScreen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmNewGameController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onCancelButtonClick() throws IOException {
        Audio.buttonSoundEffect();
        Main.stage.setScene(HomeScreen.getScene());
    }


    @FXML
    public void onContinueButtonClick() throws IOException {
        Audio.buttonSoundEffect();
        Main.stage.setScene(NewGameScreen.getScene());
    }
}
