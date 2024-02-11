package com.catquest.game;

import com.catquest.Main;
import com.catquest.entities.Cat;
import javafx.scene.image.Image;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class World {
    protected List<Level> levels = new ArrayList<>();
    private final int MAX_LEVELS = 5;
    private Type type;
    private int[][][] matrix;
    private String label;

    public enum Type {
        BLUE("blue", 0),
        BROWN("brown", 1),
        GREEN("green", 2);

        private final int value;
        private final String label;

        Type(String label, int value) {
            this.value = value;
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        public int getValue() {
            return value;
        }

        public static Type get(int value) {
            for (Type type : Type.values()) {
                if (type.getValue() == value) {
                    return type;
                }
            }
            return null;
        }

    }

    public World(Type type) {
        this.type = type;
        this.setLabel();
        this.generateLevels();
    }

    public World(Type type, int[][][] matrix) {
        this.type = type;
        this.matrix = matrix;
        this.setLabel();
        this.generateLevels();
    }

    private void setLabel() {
        String label = this.type.getLabel();
        this.label = label.substring(0, 1).toUpperCase() + label.substring(1) + " World";
    }

    private void generateLevels() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(Game.SEED_MATRIX_FILE, true));
            writer.write("Generating levels of world...");
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        for (int i = 0; i < MAX_LEVELS; i++) {
            if (this.matrix == null) {
                levels.add(new Level(this));
            } else {
                levels.add(new Level(this, this.matrix[i]));
            }
        }
    }

    public String getLabel() {
        return this.label;
    }

    public List<Level> getLevels() {
        return this.levels;
    }

    public Type getType() {
        return this.type;
    }

    public Image getPortal() {
        return new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/worlds/" + this.getType().getLabel() + "/portal.png")));
    }
}
