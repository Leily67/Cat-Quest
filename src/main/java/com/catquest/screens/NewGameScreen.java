package com.catquest.screens;

import com.catquest.Main;
import com.catquest.helpers.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

public class NewGameScreen extends Screen {

    public static Scene getScene() throws IOException {
        return buildScene("new-game-screen");
    }

    public static Scene getScene(boolean withFlash) throws IOException {
        Scene scene = buildScene("new-game-screen");
        Node flashNode = scene.getRoot().lookup("#flash");
        flashNode.setVisible(true);
        return scene;
    }
}
