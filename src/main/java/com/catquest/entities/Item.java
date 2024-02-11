package com.catquest.entities;

import javafx.scene.image.Image;

public abstract class Item {
    protected String name;
    protected int atk = 0;
    protected int def = 0;
    protected double speed = 0;
    private final Image itemImg;

    public Item(String name, Image img) {
        this.name = name;
        this.itemImg = img;
    }

    public Image getItemImg() {
        return itemImg;
    }

    public void giveStats(Cat cat) {
        cat.setStats(cat.getAtk() + this.atk,cat.getDef() + this.def, cat.getSpeed() + this.speed);
    }
}
