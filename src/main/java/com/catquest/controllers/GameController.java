package com.catquest.controllers;

import com.catquest.game.Game;
import com.catquest.game.Level;
import com.catquest.game.Menu;
import com.catquest.game.Room;
import com.catquest.game.tasks.DialogWriter;
import com.catquest.game.tasks.Stopwatch;
import com.catquest.helpers.Movement;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class GameController implements Initializable {
    public static Game game;
    @FXML
    public VBox dialog;
    @FXML
    private AnchorPane scene;
    @FXML
    private ImageView cat;
    @FXML
    private ImageView room;
    @FXML
    private Text stopwatch;
    @FXML
    private GridPane roomObstacles;
    @FXML
    private GridPane minimap;
    private Timer timer;
    private final MovementController movement = new MovementController();
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
    private Text location;
    @FXML
    private Text score;
    @FXML
    private Text dialogText;
    @FXML
    private Text xp;
    public static boolean isDialogOpen = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DialogWriter.initializeOrUpdateSentence();
        this.cat.setImage(game.getPlayer().getDefaultImage());
        this.room.setImage(game.getCurrentRoom().getBackground());

        // Affichage du premier portail
        ImageView portal = new ImageView();
        portal.setImage(game.getCurrentWorld().getPortal());
        portal.setFitHeight(200);
        portal.setFitWidth(150);
        roomObstacles.add(portal,3,4);

        movement.initialize(
                cat,
                scene,
                room,
                roomObstacles,
                minimap,
                healthBar,
                currentHp,
                maxHp,
                currentDamage,
                currentDefense,
                currentSpeed,
                location,
                score,
                dialog,
                dialogText,
                xp
        );

        movement.makeSpriteMovable();

        showMinimap(this.minimap);

        // Give the scene to the menu class
        Menu.scene = this.scene.getScene();

        // Initialize player statistics
        this.initializeLocation();
        this.initializePlayerName();
        this.initializeStats();
        this.initializeXp();
        this.initializeScore();

        // Launch tasks
        this.startStopwatch();
    }

    public static void launchDialogWithNPC(VBox dialog, Text dialogText, Room room) {
        if (dialog == null || dialogText == null || room == null) return;
        int period = 30;
        room.close();
        dialogText.setText("");
        dialog.opacityProperty().setValue(1);
        Timer timer = new Timer();
        timer.schedule(new DialogWriter(dialogText), 0, period);
        int delay = DialogWriter.getCurrentSentence().trim().length() * period + 3250;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    System.out.println("Dialog closed, room opened");
                    dialog.opacityProperty().setValue(0);
                    GameController.isDialogOpen = false;
                    room.open();
                });
            }
        }, delay);
    }

    public void initializeXp() {
        this.xp.setText(
                "Level " + game.getPlayer().getLevelLabel() + " - " + game.getPlayer().getXp() + "/" + game.getPlayer().getLevel().getXpToLevelUp() + " xp"
        );
    }

    public void initializePlayerName() {
        this.playerName.setText(game.getPlayer().getName());
    }

    public void initializeLocation() {
        this.location.setText(game.getCurrentWorld().getLabel() + " - Level " + (game.getCurrentLevelId() + 1));
    }

    public void initializeScore() {
        this.score.setText(String.valueOf(game.getScore()));
    }

    public void initializeStats() {
        updateStatsElements(healthBar, currentHp, maxHp, currentDamage, currentDefense, currentSpeed);
    }

    public static void updateXp(Text xp) {
        xp.setText(
                "Level " + game.getPlayer().getLevelLabel() + " - " + game.getPlayer().getXp() + "/" + game.getPlayer().getLevel().getXpToLevelUp() + " xp"
        );
    }

    public static void updatePlayerName(Text playerName) {
        playerName.setText(game.getPlayer().getName());
    }

    public static void updateLocation(Text location) {
        location.setText(game.getCurrentWorld().getLabel() + " - Level " + (game.getCurrentLevelId() + 1));
    }

    public static void updateScore(Text score) {
        score.setText(String.valueOf(game.getScore()));
    }

    public static void updateStats(ProgressBar healthBar, Text currentHp, Text maxHp, Text currentDamage, Text currentDefense, Text currentSpeed) {
        updateStatsElements(healthBar, currentHp, maxHp, currentDamage, currentDefense, currentSpeed);
    }

    public static void updateHealthBar(ProgressBar healthBar, Text currentHp) {
        healthBar.setProgress((double) game.getPlayer().getHp() / game.getPlayer().getMaxHp());
        currentHp.setText(String.valueOf(game.getPlayer().getHp()));
    }

    public static void showMinimap(GridPane minimap) {
        minimap.getChildren().clear();
        int[][] matrix = game.getCurrentLevel().getMatrix();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[1].length; j++) {
                Rectangle rectangle = new Rectangle(75, 75);
                rectangle.setFill(Paint.valueOf("#262626"));
                if (matrix[i][j] != Level.MatrixPointType.EMPTY.getValue()) {
                    rectangle.setFill(Paint.valueOf("#241044"));
                }
                if (game.getCurrentRoom().getRow() == i && game.getCurrentRoom().getColumn() == j) {
                    rectangle.setFill(Paint.valueOf("#2e1a5d"));
                }
                minimap.add(rectangle, j, i);
            }
        }
    }

    private static void updateStatsElements(ProgressBar healthBar, Text currentHp, Text maxHp, Text currentDamage, Text currentDefense, Text currentSpeed) {
        healthBar.setProgress((double) game.getPlayer().getHp() / game.getPlayer().getMaxHp());
        currentHp.setText(String.valueOf(game.getPlayer().getHp()));
        maxHp.setText(String.valueOf(game.getPlayer().getMaxHp()));
        currentDamage.setText(String.valueOf(game.getPlayer().getAtk()));
        currentDefense.setText(String.valueOf(game.getPlayer().getDef()));
        currentSpeed.setText(String.valueOf(game.getPlayer().getSpeed()));
    }

    private void startStopwatch() {
        this.timer = new Timer();
        this.timer.schedule(new Stopwatch(stopwatch, game), 0, 1000);
    }
}
