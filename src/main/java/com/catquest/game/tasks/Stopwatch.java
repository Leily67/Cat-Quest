package com.catquest.game.tasks;

import com.catquest.game.Game;
import javafx.scene.text.Text;

import java.util.TimerTask;

public class Stopwatch extends TimerTask {

    private int time;
    private final Game game;
    private final Text stopwatch;

    public Stopwatch(Text stopwatch, Game game) {
        this.game = game;
        this.time = game.getStopwatch();
        this.stopwatch = stopwatch;
    }

    @Override
    public void run() {
        this.game.setStopwatch(time);
        stopwatch.setText(Stopwatch.formatTime(time++));
    }

    public static String formatTime(int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
