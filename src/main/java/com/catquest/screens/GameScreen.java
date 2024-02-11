package com.catquest.screens;

import javafx.scene.Scene;
import java.io.IOException;

public class GameScreen extends Screen {

    public static Scene getScene() throws IOException {
        Scene scene = buildScene("game-screen");
        scene.getRoot().requestFocus();
        System.out.println("Game screen loaded.");
        return scene;
    }
}