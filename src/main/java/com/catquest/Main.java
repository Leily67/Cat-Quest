package com.catquest;

import com.catquest.game.Room;
import com.catquest.game.rooms.SpawnRoom;
import com.catquest.helpers.Audio;
import com.catquest.helpers.Controls;
import com.catquest.helpers.View;
import com.catquest.screens.HomeScreen;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;


public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) {
        Main.stage = stage;

        // Initialize the different helpers
        View.initialize();
        Controls.initialize();
        Audio.initialize();

        try {
            stage.setScene(HomeScreen.getScene());
            stage.setTitle("Cat Quest");
            stage.setResizable(false);
            stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/icon.png"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        launch();
    }
}