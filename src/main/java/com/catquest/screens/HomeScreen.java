package com.catquest.screens;

import com.catquest.Main;
import com.catquest.helpers.View;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeScreen extends Screen {
    public static Scene getScene() throws IOException {
        return buildScene("home-screen");
    }
}
