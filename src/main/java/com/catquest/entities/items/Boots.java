package com.catquest.entities.items;

import com.catquest.Main;
import com.catquest.entities.Item;
import javafx.scene.image.Image;

import java.util.Objects;

public final class Boots extends Item {
    public Boots() {
        super("Magic Boots!",new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/items/boots.png"))));
        this.speed = 0.5;
    }
}
