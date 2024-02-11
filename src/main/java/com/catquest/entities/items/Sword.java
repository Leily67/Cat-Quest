package com.catquest.entities.items;

import com.catquest.Main;
import com.catquest.entities.Item;
import javafx.scene.image.Image;

import java.util.Objects;

public final class Sword extends Item {

    public Sword() {
        super("Sword",new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/items/sword.png"))));
        this.atk = 3;
    }
}
