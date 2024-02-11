package com.catquest.game.tasks;

import com.catquest.controllers.GameController;
import com.catquest.entities.Cat;
import com.catquest.game.Game;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.TimerTask;

public class DialogWriter extends TimerTask {
    private static String sentence = "";
    private String s;
    @FXML
    private final Text dialogText;

    public static void initializeOrUpdateSentence() {
        if (GameController.game.getIsFinished()) {
            sentence = """
                    Congratulations, you have finished the game!
                    
                    You can be proud of yourself, you are a real cat adventurer!
                    
                    I hope you enjoyed playing this game, and I hope to see you again soon!
                    
                    Goodbye!
                    """;
        } else if (GameController.game.getCurrentWorldId() == 0) {
            sentence = """
                Welcome to Cat Quest!

                You are a cat who got lost in a cave, and you must find your way out. You will encounter many enemies along the way, but you can defeat them by attacking them. You can also find items that will help you on your journey.

                Your quest is to find your way out of the cave the fastest as possible, and you will be rewarded with a high score.

                Good luck!""";
        } else if (GameController.game.getCurrentWorldId() == 1) {
            sentence = """
                    Well played!
                    
                    If you come so far, it means that you are a good player. But the game is not over yet, you still have to find your way out of the cave.
                    
                    For this world you will need to be more careful, because the enemies are stronger and you will need to find more items to defeat them.
                    
                    Good luck for the next world!
                    """;
        } else if (GameController.game.getCurrentWorldId() == 2) {
            sentence = """
                    Wow, impressive!
                    
                    Tell me your secret, how did you manage to get so far? I'm sure you are cheating!
                    
                    Anyway, you are now in the last world, and it will be the hardest one. You will need to be very careful, because the enemies are very strong and you will need to find a lot of items to defeat them.
                    
                    Good luck for the last world!
                    """;
        }
    }

    public static String getCurrentSentence() {
        return sentence;
    }

    public DialogWriter(Text dialogText) {
        this.dialogText = dialogText;
        this.s = sentence;
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            if (this.s.isEmpty() || dialogText == null) {
                this.cancel();
                return;
            }
            dialogText.setText(dialogText.getText() + this.s.charAt(0));
            this.s = this.s.substring(1);
        });
    }
}
