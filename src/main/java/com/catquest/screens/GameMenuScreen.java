package com.catquest.screens;

import javafx.scene.Scene;

import java.io.IOException;

public class GameMenuScreen extends Screen {
    public static Scene getScene() throws IOException {
        return buildScene("game-menu-screen");
    }
}
