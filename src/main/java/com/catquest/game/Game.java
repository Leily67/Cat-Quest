package com.catquest.game;

import com.catquest.Main;
import com.catquest.controllers.EndController;
import com.catquest.controllers.GameController;
import com.catquest.controllers.MovementController;
import com.catquest.entities.Cat;
import com.catquest.exceptions.SaveFileUnreadable;
import com.catquest.game.rooms.EndRoom;
import com.catquest.game.tasks.DialogWriter;
import com.catquest.helpers.Movement;
import com.catquest.helpers.Seed;
import com.catquest.screens.EndScreen;
import com.catquest.screens.GameScreen;
import com.catquest.screens.NewGameScreen;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.controlsfx.control.spreadsheet.Grid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Game {
    private String gameSeed = "";
    private int currentWorld = World.Type.BLUE.getValue();
    private int currentLevelId = 0;
    private Room currentRoom;
    private int stopwatch = 0;
    private int score = 0;
    private Cat player;
    private final List<World> worlds = new ArrayList<>();
    private final Save save = new Save(this);
    private final Seed seed = new Seed(this);
    public final static String SEED_MATRIX_FILE = "src/main/resources/com/catquest/map.txt";
    public static boolean EXECUTED_FROM_TESTS = false;
    private boolean isFinished = false;

    private void load() {
        try {
            this.save.load();
            this.start(true);
        } catch (SaveFileUnreadable e) {
            this.redirect();
        }
    }

    private void start(boolean fromSave) {
        this.initialize(fromSave);
        if (!EXECUTED_FROM_TESTS) {
            this.show(this.getIsFinished());
        }
    }

    private void redirect() {
        if (EXECUTED_FROM_TESTS) return;
        try {
            Loader.hide();
            Main.stage.setScene(NewGameScreen.getScene(true));
        } catch (IOException ex) {
            System.out.println("An error occurred while loading the new game screen.");
        }
    }

    private void initializeSavedMap() throws SaveFileUnreadable {
        int[][][][] matrix = this.seed.parse();
        for (int i = 0; i < World.Type.values().length; i++) {
            World world = new World(World.Type.get(i), matrix[i]);
            this.worlds.add(world);
        }
    }

    private void initializeNewGame() {
        this.seed.clear();
        for (int i = 0; i < World.Type.values().length; i++) {
            World world = new World(World.Type.get(i));
            this.worlds.add(world);
        }
        this.seed.make();
    }

    private void initialize(boolean fromSave) {
        if (fromSave) {
            try {
                System.out.println("Loading save file...");
                this.initializeSavedMap();
            } catch (SaveFileUnreadable e) {
                this.redirect();
            }
        } else {
            System.out.println("Creating new game...");
            this.initializeNewGame();
            this.save();
        }
        this.initializeCurrentRoom();
    }

    private void show(boolean isFinished) {
        try {
            Loader.hide();
            GameController.game = this;
            MovementController.game = this;
            System.out.println("Showing game screen...");
            System.out.println("Current room: " + this.getCurrentRoom());
            System.out.println("Current level: " + (this.getCurrentLevelId() + 1));
            System.out.println("Current world: " + this.getCurrentWorld().getType().getLabel());
            System.out.println("Current player: " + this.getPlayer().getName());
            System.out.println("Is finished game: " + isFinished);
            Main.stage.setScene(isFinished ? EndScreen.getScene() : GameScreen.getScene());
        } catch (IOException e) {
            this.redirect();
        }
    }

    /**
     * Create a new game with the given player object
     * @param player The player object
     */
    public static void create(Cat player) {
        new Game(player);
    }

    /**
     * Create a game from the save file
     */
    public static void createFromSave() {
        new Game();
    }

    public Game(Cat player) {
        this.player = player;
        this.start(false);
    }

    public Game() {
        this.load();
    }

    public Seed seed() {
        return seed;
    }

    public String getGameSeed() {
        return gameSeed;
    }

    public World getCurrentWorld() {
        return this.worlds.get(this.currentWorld);
    }

    public int getCurrentWorldId() {
        return currentWorld;
    }

    public void setCurrentWorld(int currentWorld) {
        this.currentWorld = currentWorld;
    }

    public void setCurrentWorldId(int currentWorld) {
        this.currentWorld = currentWorld;
    }

    public int getCurrentLevelId() {
        return currentLevelId;
    }

    public Level getCurrentLevel() {
        return this.getCurrentWorld().getLevels().get(this.currentLevelId);
    }

    public void setCurrentLevelId(int currentLevelId) {
        this.currentLevelId = currentLevelId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStopwatch() {
        return stopwatch;
    }

    public void setStopwatch(int stopwatch) {
        this.stopwatch = stopwatch;
    }

    public void setGameSeed(String gameSeed) {
        this.gameSeed = gameSeed;
    }

    public Cat getPlayer() {
        return this.player;
    }

    public void setPlayer(Cat player) {
        if (player != null) {
            this.player = player;
        }
    }

    public List<World> getWorlds() {
        return worlds;
    }

    public void initializeCurrentRoom() {
        this.currentRoom =  worlds.get(this.currentWorld).getLevels().get(this.currentLevelId).getRooms().get(0);
    }

    public void changeRoom(Door.Position position, ImageView room, GridPane minimap) {
        for (Door door : this.currentRoom.getDoors()) {
            if (door.getPosition() == position && door.getRoom() == this.currentRoom && this.currentRoom.isOpen()) {
                this.currentRoom = door.behind();
                room.setImage(this.getCurrentRoom().getBackground());
                GameController.showMinimap(minimap);
            }
        }
    }

    public void save() {
        this.save.make();
    }

    public Room getCurrentRoom(){
        return currentRoom;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public boolean getIsFinished() {
        return this.isFinished;
    }
  
    public void playerHasFinishedLevel(ImageView room, GridPane minimap, Text location, ImageView sprite, ProgressBar healthBar, Text currentHp, Text maxHp, Text currentDamage, Text currentDefense, Text currentSpeed) {
        if (this.currentLevelId < this.getCurrentWorld().getLevels().size() - 1) {
            this.player.setHp(this.player.getHp() + 12);
            GameController.updateStats(healthBar, currentHp, maxHp, currentDamage, currentDefense, currentSpeed);
            this.currentLevelId++;
        } else {
            if (this.currentWorld == this.getWorlds().size() - 1) {
                this.setIsFinished(true);
                try {
                    Main.stage.setScene(EndScreen.getScene());
                } catch (IOException e) {
                    System.out.println("An error occurred while loading the end screen.");
                }
            } else {
                this.player.setHp(this.player.getMaxHp());
                GameController.updateStats(healthBar, currentHp, maxHp, currentDamage, currentDefense, currentSpeed);
                this.currentWorld++;
                this.currentLevelId = 0;
                DialogWriter.initializeOrUpdateSentence();
            }
        }

        if (!isFinished) {
            this.initializeCurrentRoom();
            room.setImage(this.getCurrentRoom().getBackground());
            GameController.showMinimap(minimap);
            GameController.updateLocation(location);
            sprite.setLayoutX(Movement.CENTER_X);
            sprite.setLayoutY(Movement.CENTER_Y);
        }

        this.save();
    }
}
