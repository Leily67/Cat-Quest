package com.catquest.entities.enemies;

import com.catquest.Main;
import com.catquest.entities.Enemy;
import javafx.scene.image.Image;

import java.util.Objects;

public final class ElonMusk extends Enemy {
    public ElonMusk() {
        super(10, 0, 30, .5f, new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/entities/boss/second/moove/face6.png"))));
    }
}
