package com.catquest.game.rooms;

import com.catquest.Main;
import com.catquest.game.Room;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class RandomRoom extends Room {

    private final Image Obstacles = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/entities/skull.png")));

    public RandomRoom(int row, int column) {
        super(row, column);
        this.setType(Room.Type.RANDOM);
    }
}
