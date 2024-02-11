package com.catquest.entities;

import com.catquest.Main;
import javafx.scene.image.Image;

import java.util.Objects;

public class Boss extends Enemy {
    public Boss(int atk, int def, int hp, double speed) {
        super(atk, def, hp, speed,new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/entities/skull.png"))));
    }
}
