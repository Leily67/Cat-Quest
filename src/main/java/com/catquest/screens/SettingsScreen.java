package com.catquest.screens;

import com.catquest.Main;
import com.catquest.helpers.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class SettingsScreen extends Screen {
    public static Scene getScene() throws IOException {
        return buildScene("settings-screen");
    }

}
