package com.catquest.game;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GenerationTest {
    @BeforeAll
    public static void setUp() {
        Game.EXECUTED_FROM_TESTS = true;
        Level.EXECUTED_FROM_TESTS = true;
    }

    @Test
    public void matrixTypeAreCorresponding() {
        assertEquals(0, Level.MatrixPointType.EMPTY.getValue());
        assertEquals(1, Level.MatrixPointType.RANDOM.getValue());
        assertEquals(2, Level.MatrixPointType.SPAWN.getValue());
        assertEquals(3, Level.MatrixPointType.END.getValue());
    }

    @Test
    public void matrixHasCorrectSize() {
        Level level = new Level(new World(World.Type.BROWN));
        int[][] matrix = level.getMatrix();
        int rows = matrix.length;
        int columns = matrix[0].length;
        assertEquals(6, rows);
        assertEquals(6, columns);
    }

    @Test
    public void matrixHasCorrectNumberOfRooms() {
        Level level = new Level(new World(World.Type.BLUE));
        List<Room> rooms = level.getRooms();
        int numberOfRooms = rooms.size();
        assertTrue(numberOfRooms >= 6 && numberOfRooms <= 12);

    }

    @Test
    public void canCreateLevelsFromSeed() {
        assertDoesNotThrow(() -> {
            String seed = "111121.003111.000001.000001.000000.000000#000000.000000.001100.001110.000211.000013#000000.000000.300000.111000.110000.200000#000000.000000.000001.001211.000311.000000#000000.000000.000000.003102.000101.000111@000000.000013.000010.001110.000011.001121#000000.000000.031000.001100.000110.011200#000000.000000.000000.001000.001300.112100#031111.001211.001000.000000.000000.000000#121100.113000.110000.000000.000000.000000@112100.101110.000130.000000.000000.000000#000000.000000.130000.110000.100000.120000#000000.001000.111100.311000.020000.010000#000000.011200.001110.000030.000000.000000#000100.211100.011000.030000.000000.000000";
            Game game = new Game();
            game.setGameSeed(seed);
            int[][][][] matrix = game.seed().parse();
            for (int i = 0; i < World.Type.values().length; i++) {
                new World(World.Type.get(i), matrix[i]);
            }
        });
    }
}
