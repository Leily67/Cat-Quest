package com.catquest.game;

import com.catquest.game.rooms.EndRoom;
import com.catquest.game.rooms.RandomRoom;
import com.catquest.game.rooms.SpawnRoom;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Level {
    private final List<Room> rooms = new ArrayList<>();
    private int[][] matrix;
    private static final int MIN_ROOMS = 6;
    private static final int MAX_ROOMS = 12;
    private final int[] dx = {-1, 0, 1, 0};
    private final int[] dy = {0, -1, 0, 1};
    private final World world;
    public static boolean EXECUTED_FROM_TESTS = false;

    public enum MatrixPointType {
        EMPTY(0),
        RANDOM(1),
        SPAWN(2),
        END(3);

        private final int value;

        MatrixPointType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static MatrixPointType get(int value) {
            return Arrays.stream(MatrixPointType.values())
                    .filter(matrixRoomType -> matrixRoomType.getValue() == value)
                    .findFirst()
                    .orElse(null);
        }
    }

    public Level(World world) {
        this.world = world;
        this.initializeMatrix();
        this.generateWithBlankMatrix();
        this.write();
    }

    public Level(World world, int[][] matrix) {
        this.world = world;
        this.matrix = matrix;
        this.generateWithMatrixAlreadyFill();
        this.write();
    }

    private void write() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(Game.SEED_MATRIX_FILE, true));

            for (int[] row : this.matrix) {
                for (int column : row) {
                    writer.write(String.valueOf(column));
                    writer.write(" ");
                }
                writer.newLine();
            }

            writer.write("-".repeat(15));
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("An error occurred while writing the map to file.");
        }
    }

    private void initializeMatrix() {
        int rows = 6;
        int columns = 6;
        this.matrix = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            this.matrix[i][0] = MatrixPointType.EMPTY.getValue();
            this.matrix[i][columns - 1] = MatrixPointType.EMPTY.getValue();
        }
    }

    private void generateWithMatrixAlreadyFill() {
        SpawnRoom spawn = null;
        EndRoom end = null;

        for (int r = 0; r < this.matrix.length; r++) {
            for (int c = 0; c < this.matrix[0].length; c++) {
                int type = this.matrix[r][c];
                if (type == MatrixPointType.RANDOM.getValue()) {
                    this.rooms.add(new RandomRoom(r, c));
                } else if (type == MatrixPointType.SPAWN.getValue()) {
                    spawn = new SpawnRoom(r, c);
                } else if (type == MatrixPointType.END.getValue()) {
                    end = new EndRoom(r, c);
                }
            }
        }

        if (spawn == null || end == null) {
            throw new RuntimeException("The matrix is not valid.");
        }

        // On ajoute la salle de départ
        this.update(spawn, this.getEndOrSpawnDoors(spawn.getRow(), spawn.getColumn(), spawn));
        this.rooms.addFirst(spawn);

        // On ajoute la salle de fin
        this.update(end, this.getEndOrSpawnDoors(end.getRow(), end.getColumn(), end));
        this.rooms.addLast(end);

        this.generateDoorsOfRandomRooms();
    }

    private void generateWithBlankMatrix() {
        int targetRooms = (int) (Math.random() * (MAX_ROOMS - MIN_ROOMS)) + MIN_ROOMS;
        int initialRow = (int) (Math.random() * this.matrix.length);
        int initialColumn = (int) (Math.random() * this.matrix[0].length);

        // On place la première salle au hasard dans la matrice
        this.matrix[initialRow][initialColumn] = MatrixPointType.SPAWN.getValue();
        SpawnRoom spawn = new SpawnRoom(initialRow, initialColumn);
        this.rooms.add(spawn);

        int generatedRooms = 1;

        // On génère des salles aléatoires jusqu'à ce qu'on atteigne le nombre de salles cible,
        // Cette méthode permet de générer des salles aléatoires qui sont adjacentes à une salle existante,
        // et qui démarreront à partir de la salle de départ
        while (generatedRooms < targetRooms) {
            int r = (int) (Math.random() * this.matrix.length);
            int c = (int) (Math.random() * this.matrix[0].length);

            if (this.matrix[r][c] == MatrixPointType.EMPTY.getValue() && isAdjacent(r, c, false)) {
                // Dans le cas où une salle peut être générée,
                // On la place dans la matrice et on l'ajoute à la liste
                this.matrix[r][c] = MatrixPointType.RANDOM.getValue();
                this.rooms.add(new RandomRoom(r, c));
                generatedRooms++;
            }
        }

        this.update(spawn, this.getEndOrSpawnDoors(spawn.getRow(), spawn.getColumn(), spawn));

        // Après avoir généré toutes les salles aléatoires, on génère la salle de fin
        // Elle doit être adjacente à au moins une salle existante hormis la salle de départ
        // Cette boucle s'arrête lorsque la salle de fin est générée
        do {
            int r = (int) (Math.random() * this.matrix.length);
            int c = (int) (Math.random() * this.matrix[0].length);

            if (this.matrix[r][c] == MatrixPointType.EMPTY.getValue() && isAdjacent(r, c, true)) {
                // Dans le cas où une salle de fin peut être générée,
                // On la place dans la matrice et on l'ajoute à la liste
                if (countAdjacentOnes(r, c) > 0) {
                    this.matrix[r][c] = MatrixPointType.END.getValue();
                    EndRoom room = new EndRoom(r, c);
                    this.update(room, this.getEndOrSpawnDoors(r, c, room));
                    this.rooms.add(room);
                }
            }
        } while (!hasEndRoom());

        this.generateDoorsOfRandomRooms();
    }

    private void generateDoorsOfRandomRooms() {
        for (Room room : this.rooms) {
            if (room instanceof RandomRoom) {
                List<Door> doors = new ArrayList<>();
                for (int i = 0; i < dx.length; i++) {
                    int r = room.getRow() + this.dx[i];
                    int c = room.getColumn() + this.dy[i];
                    if (this.isValidIndex(r, c)) {
                        if (this.matrix[r][c] != MatrixPointType.EMPTY.getValue()) {
                            doors.add(new Door(room, this.getRoomAt(r, c), Door.Position.get(i)));
                        }
                    }
                }
                if (doors.isEmpty()) {
                    throw new RuntimeException("A random room has no doors.");
                }
                this.update(room, doors);
            }
        }
    }

    private List<Door> getEndOrSpawnDoors(int row, int column, Room room) {
        List<Door> doors = new ArrayList<>();
        for (int i = 0; i < dx.length; i++) {
            int r = row + this.dx[i];
            int c = column + this.dy[i];
            if (this.isValidIndex(r, c)) {
                if (this.matrix[r][c] == MatrixPointType.RANDOM.getValue()) {
                    doors.add(new Door(room, this.getRoomAt(r, c), Door.Position.get(i)));
                }
            }
        }
        return doors;
    }

    private void update(Room room, List<Door> doors) {
        room.setDoors(doors);
        if (!EXECUTED_FROM_TESTS) {
            room.setBackground(this.world.getType().getLabel(), String.valueOf(doors.size()));
        }
    }

    private boolean isValidIndex(int row, int column) {
        return row >= 0 && row < this.matrix.length && column >= 0 && column < this.matrix[0].length;
    }

    private boolean isAdjacent(int row, int column, boolean isEndRoom) {
        for (int i = 0; i < dx.length; i++) {
            int r = row + this.dx[i];
            int c = column + this.dy[i];
            if (r >= 0 && r < this.matrix.length && c >= 0 && c < this.matrix[0].length) {
                int type = this.matrix[r][c];
                if (isEndRoom && type == MatrixPointType.RANDOM.getValue()) {
                    return true;
                } else if (!isEndRoom && (type == MatrixPointType.RANDOM.getValue() || type == MatrixPointType.SPAWN.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    private int countAdjacentOnes(int row, int column) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int r = row + i;
                int c = column + j;
                if (r >= 0 && r < this.matrix.length && c >= 0 && c < this.matrix[0].length) {
                    if (this.matrix[r][c] == MatrixPointType.RANDOM.getValue()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private boolean hasEndRoom() {
        for (int[] row : this.matrix) {
            for (int column : row) {
                if (column == MatrixPointType.END.getValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[][] getMatrix() {
        return this.matrix;
    }

    private Room getRoomAt(int r, int c) {
        for (Room room : rooms) {
            if (room.getRow() == r && room.getColumn() == c) {
                return room;
            }
        }
        return null;
    }

    public List<Room> getRooms(){
        return rooms;
    }
}
