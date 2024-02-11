package com.catquest.game;

import com.catquest.controllers.MovementController;
import com.catquest.helpers.Movement;
import javafx.util.Pair;

public class Door {
    private final static int DOOR_GAP = 40;
    protected Position position;
    protected Room room;
    protected Room to;

    public enum Position {
        TOP(0, Movement.CENTER_X, Movement.TOP_LIMIT + DOOR_GAP),
        LEFT(1, Movement.LEFT_LIMIT + DOOR_GAP, Movement.CENTER_Y),
        BOTTOM(2, Movement.CENTER_X, Movement.BOTTOM_LIMIT - DOOR_GAP),
        RIGHT(3, Movement.RIGHT_LIMIT - DOOR_GAP, Movement.CENTER_Y);

        private final int id;
        private final int x;
        private final int y;

        Position(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        /**
         * This function check if the sprite is in the area of the door (40 pixels around the door)
         */
        public int[] area() {
            // If its left or right return y area
            if (this.id == 1 || this.id == 3) {
                return new int[]{this.y - 35, this.y + 35};
            }
            return new int[]{this.x - 50, this.x + 50};
        }

        public static Position get(int id) {
            for (Position position : Position.values()) {
                if (position.id == id) {
                    return position;
                }
            }
            return null;
        }
    }

    public Door(Room room, Room to, Position position) {
        this.room = room;
        this.to = to;
        this.position = position;
    }

    public Position getPosition() {
        return this.position;
    }

    public Room behind() {
        return this.to;
    }

    public Room getRoom() {
        return this.room;
    }
}
