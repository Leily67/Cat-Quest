package com.catquest.entities.enemies;

import com.catquest.Main;
import com.catquest.entities.Enemy;
import javafx.scene.image.Image;

import java.util.Objects;

public final class Dmystan extends Enemy {
    public Dmystan() {
        super(10, 0, 25, 1, new Image(Objects.requireNonNull(Main.class.getResourceAsStream(Enemy.SPRITE_PATH + "/first/moove/face6.png"))));
    }
}
