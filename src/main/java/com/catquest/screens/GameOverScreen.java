package com.catquest.screens;

import javafx.scene.Node;
import javafx.scene.Scene;

import java.io.IOException;

public class GameOverScreen extends Screen {

    public static Scene getScene() throws IOException {
        return buildScene("game-over-screen");
    }
}
