package com.catquest.controllers;

import com.catquest.game.Game;
import com.catquest.game.tasks.DialogWriter;
import com.catquest.game.tasks.Stopwatch;
import com.catquest.helpers.Movement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class EndController implements Initializable {
    @FXML
    private Text stopwatch;
    @FXML
    private AnchorPane scene;
    @FXML
    private ImageView cat;
    @FXML
    private ImageView room;
    @FXML
    private ProgressBar healthBar;
    @FXML
    private Text currentHp;
    @FXML
    private Text maxHp;
    @FXML
    private Text playerName;
    @FXML
    private Text currentDamage;
    @FXML
    private Text currentDefense;
    @FXML
    private Text currentSpeed;
    @FXML
    private Text score;
    @FXML
    private Text dialogText;
    @FXML
    public VBox dialog;
    @FXML
    private Text xp;
    private final MovementController movement = new MovementController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DialogWriter.initializeOrUpdateSentence();
        Game game = GameController.game;
        this.cat.setImage(game.getPlayer().getDefaultImage());

        // Make the cat movable inside the end screen
        this.movement.initialize(this.cat, this.scene, this.room, this.dialog, this.dialogText);
        this.movement.makeSpriteMovable();

        // Set the cat's position to the center of the screen
        cat.setLayoutX(Movement.CENTER_X);
        cat.setLayoutY(Movement.CENTER_Y);

        // Initialize the end screen with the player's stats and score from the game
        this.stopwatch.setText(Stopwatch.formatTime(game.getStopwatch()));
        this.healthBar.setProgress((double) game.getPlayer().getHp() / game.getPlayer().getMaxHp());
        this.currentHp.setText(String.valueOf(game.getPlayer().getHp()));
        this.maxHp.setText(String.valueOf(game.getPlayer().getMaxHp()));
        this.playerName.setText(game.getPlayer().getName());
        this.currentDamage.setText(String.valueOf(game.getPlayer().getAtk()));
        this.currentDefense.setText(String.valueOf(game.getPlayer().getDef()));
        this.currentSpeed.setText(String.valueOf(game.getPlayer().getSpeed()));
        this.score.setText(String.valueOf(game.getScore()));
        this.xp.setText("Level " + game.getPlayer().getLevelLabel() + " - " + game.getPlayer().getXp() + "/" + game.getPlayer().getLevel().getXpToLevelUp() + " xp");
    }

}
