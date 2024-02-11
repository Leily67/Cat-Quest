package com.catquest.entities.items;

import com.catquest.Main;
import com.catquest.entities.Item;
import javafx.scene.image.Image;

import java.util.Objects;

public final class Armor extends Item {
    public Armor() {
        super("Armor",new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/items/armor.png"))));
        this.def = 3;
    }
}
