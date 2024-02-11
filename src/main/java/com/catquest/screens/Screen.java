package com.catquest.screens;

import com.catquest.Main;
import com.catquest.helpers.View;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Screen {

    protected static String SCREEN_PATH = "screens/";

    protected static String getFXMLPath(String filename) {
        return SCREEN_PATH + filename + ".fxml";
    }

    protected static Scene buildScene(String filename) throws IOException {
        FXMLLoader root = new FXMLLoader(Main.class.getResource(getFXMLPath(filename)));
        Parent layout = root.load();
        return new Scene(layout, View.getWidthForCurrentScreen(), View.getHeightForCurrentScreen());
    }


}
