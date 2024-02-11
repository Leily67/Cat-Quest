package com.catquest.game;

import com.catquest.Main;
import com.catquest.controllers.GameController;
import com.catquest.controllers.MovementController;
import com.catquest.helpers.Movement;
import com.catquest.screens.EndScreen;
import com.catquest.screens.GameMenuScreen;
import com.catquest.screens.GameScreen;
import javafx.scene.Scene;

import java.io.IOException;

public class Menu {
    public static Scene scene;

    public static void show() throws IOException {
        Main.stage.setScene(GameMenuScreen.getScene());
        Main.stage.show();
    }

    public static void hide() throws IOException {
        if (MovementController.game.getIsFinished()) {
            Main.stage.setScene(EndScreen.getScene());
        } else {
            GameController.game.getPlayer().setPositions(Movement.CENTER_X, Movement.CENTER_Y);
            GameController.game.initializeCurrentRoom();
            Main.stage.setScene(GameScreen.getScene());
        }
    }
}
