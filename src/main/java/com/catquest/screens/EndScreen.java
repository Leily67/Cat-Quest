package com.catquest.screens;

import javafx.scene.Scene;

import java.io.IOException;

public class EndScreen extends Screen {
    public static Scene getScene() throws IOException {
        Scene scene = buildScene("end-screen");
        scene.getRoot().requestFocus();
        return scene;
    }
}
