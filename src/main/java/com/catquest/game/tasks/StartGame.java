package com.catquest.game.tasks;

import com.catquest.entities.Cat;
import com.catquest.game.Game;
import javafx.application.Platform;
import javafx.scene.text.Text;

import java.util.TimerTask;

public class StartGame extends TimerTask {
    private boolean isNew;
    private String playerName;

    public StartGame(boolean isNew) {
        this.isNew = isNew;
    }

    public StartGame(boolean isNew, String playerName) {
        this.isNew = isNew;
        this.playerName = playerName;
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            if (isNew) {
                Game.create(new Cat(this.playerName));
            } else {
                Game.createFromSave();
            }
        });
    }
}
