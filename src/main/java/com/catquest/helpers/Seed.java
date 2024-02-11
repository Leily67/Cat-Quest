package com.catquest.helpers;

import com.catquest.exceptions.SaveFileUnreadable;
import com.catquest.game.Game;
import com.catquest.game.Level;
import com.catquest.game.World;

import java.io.File;
import java.util.Arrays;

public class Seed {
    private final Game game;

    public Seed(Game game) {
        this.game = game;
    }

    /**
     * The seed is a 2D array of integers that represents the world map
     */
    public void make() {
        StringBuilder builder = new StringBuilder();
        for (World world : this.game.getWorlds()) {
            for (Level level : world.getLevels()) {
                int[][] matrix = level.getMatrix();
                for (int[] row : matrix) {
                    for (int column : row) {
                        builder.append(column);
                    }
                    if (row != matrix[matrix.length - 1]) {
                        builder.append(".");
                    }
                }
                if (level != world.getLevels().get(world.getLevels().size() - 1)) {
                    builder.append("#");
                }
            }
            if (world != this.game.getWorlds().get(this.game.getWorlds().size() - 1)) {
                builder.append("@");
            }
        }
        this.game.setGameSeed(builder.toString());
    }

    /**
     * This function is used to parse a seed into a 2D array of integers
     * The seed represent the whole game map (all the worlds and levels)
     * So it's an array of arrays of arrays of integers
     *
     * @return The seed as a 3D array of integers
     */
    public int[][][][] parse() throws SaveFileUnreadable {
        try {
            String seed = this.game.getGameSeed();
            String[] worlds = seed.split("@");
            int[][][][] matrix = new int[worlds.length][][][];
            for (int i = 0; i < worlds.length; i++) {
                String[] levels = worlds[i].split("#");
                matrix[i] = new int[levels.length][][];
                for (int j = 0; j < levels.length; j++) {
                    String[] rows = levels[j].split("\\.");
                    matrix[i][j] = new int[rows.length][];
                    for (int k = 0; k < rows.length; k++) {
                        String[] columns = rows[k].split("");
                        matrix[i][j][k] = new int[columns.length];
                        for (int l = 0; l < columns.length; l++) {
                            matrix[i][j][k][l] = Integer.parseInt(columns[l]);
                        }
                    }
                }
            }
            return matrix;
        } catch (Exception e) {
             throw new SaveFileUnreadable();
        }
    }

    public void clear() {
        File file = new File(Game.SEED_MATRIX_FILE);
        file.delete();
    }
}
