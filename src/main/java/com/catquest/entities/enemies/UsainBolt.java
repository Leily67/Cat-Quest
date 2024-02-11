package com.catquest.entities.enemies;

import com.catquest.Main;
import com.catquest.entities.Enemy;
import javafx.scene.image.Image;

import java.util.Objects;

public final class UsainBolt extends Enemy {
    public UsainBolt() {
        super(10, 0, 20, 2,new Image(Objects.requireNonNull(Main.class.getResourceAsStream(Enemy.SPRITE_PATH + "/third/moove/face1.png"))));
    }
}
