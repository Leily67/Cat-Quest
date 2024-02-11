package com.catquest.game;

import com.catquest.entities.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import com.catquest.enums.ItemEnum;
import com.catquest.exceptions.SaveFileUnreadable;
import com.catquest.helpers.Json;
import org.json.simple.JSONObject;

public class Save {
    private final static String SAVE_FILE_PATH = "src/main/resources/com/catquest/save.json";

    private final Game game;

    public Save(Game game) {
        this.game = game;
    }

    /**
     * Create a save file with the current game information
     */
    public void make() {
        HashMap<String, Object> save = new HashMap<>();
        save.put("cat", getCatObject());

        // Populate game information
        save.put("isFinished", this.game.getIsFinished());
        save.put("seed", this.game.getGameSeed());
        save.put("currentWorld", this.game.getCurrentWorldId());
        save.put("currentLevel", this.game.getCurrentLevelId());
        save.put("score", this.game.getScore());
        save.put("stopwatch", this.game.getStopwatch());
        save.put("created_at", System.currentTimeMillis());

        try {
            FileWriter file = new FileWriter(SAVE_FILE_PATH);
            file.write(new JSONObject(save).toJSONString());
            file.flush();
            file.close();
            System.out.println("Save json file created.");
        } catch (IOException e) {
            System.out.println("An error occurred while creating the save file.");
        }
    }

    /**
     * Transform the cat object into a HashMap to be used in the save file
     *
     * @return HashMap of cat information
     */
    private HashMap<String, Object> getCatObject() {
        Cat player = this.game.getPlayer();

        HashMap<String, Object> cat = new HashMap<>();
        cat.put("name", player.getName());
        cat.put("level", player.getLevelLabel());
        cat.put("xp", player.getXp());
        cat.put("hp", player.getHp());
        cat.put("maxHp", player.getMaxHp());

        HashMap<String, Integer> items = new HashMap<>();
        for (Class<? extends Item> item : player.getItems().keySet()) {
            items.put(item.getSimpleName(), player.getItems().get(item));
        }
        cat.put("items", items);

        HashMap<String, Object> stats = new HashMap<>();
        stats.put("atk", player.getAtk());
        stats.put("def", player.getDef());
        stats.put("speed", player.getSpeed());
        cat.put("stats", stats);
        return cat;
    }

    private int parseInt(Object value) {
        return Integer.parseInt(value.toString());
    }

    /**
     * Retrieve the save file and load the game information if it exists and is valid
     */
    public void load() throws SaveFileUnreadable {
        if (!Save.exists()) throw new SaveFileUnreadable();

        try {
            JSONObject save = Json.parse(SAVE_FILE_PATH);
            this.game.setIsFinished((Boolean) save.get("isFinished"));
            this.game.setStopwatch(parseInt(save.get("stopwatch")));
            this.game.setGameSeed((String) save.get("seed"));
            this.game.setCurrentWorldId(parseInt(save.get("currentWorld")));
            this.game.setCurrentLevelId(parseInt(save.get("currentLevel")));
            this.game.setScore(parseInt(save.get("score")));
            JSONObject cat = (JSONObject) save.get("cat");
            Cat player = new Cat((String) cat.get("name"));
            player.setXp(parseInt(cat.get("xp")));
            player.setLevel((String) cat.get("level"));
            player.setHp(parseInt(cat.get("hp")));
            player.setMaxHp(parseInt(cat.get("maxHp")));
            JSONObject stats = (JSONObject) cat.get("stats");
            player.setStats(parseInt(stats.get("atk")), parseInt(stats.get("def")), (double) stats.get("speed"));
            this.game.setPlayer(player);
        } catch (Exception e) {
            throw new SaveFileUnreadable();
        }
    }

    public static void clear() {
        File file = new File(SAVE_FILE_PATH);
        file.delete();
    }

    public static boolean exists() {
        return new File(SAVE_FILE_PATH).exists();
    }
}
