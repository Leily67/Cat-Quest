package com.catquest.entities;

import com.catquest.Main;
import com.catquest.entities.items.Armor;
import com.catquest.entities.items.Boots;
import com.catquest.entities.items.Sword;
import com.catquest.enums.Level;
import com.catquest.game.Game;
import com.catquest.helpers.Movement;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Objects;

public final class Cat extends Character {
    private final String name;
    private Level level = Level.LEVEL_1;
    private int xp = 0;
    private int x = Movement.CENTER_X;
    private int y = Movement.CENTER_Y;
    private final HashMap<Class<? extends Item>, Integer> items = new HashMap<>();
    private final Image defaultImage;
    public static String IMAGE_PATH = "images/cats/brown/";

    /**
     * Increase the xp of the cat and check if the cat has leveled up
     *
     * @param xp The amount of xp to add to the cat
     */
    public void increaseXp(int xp) {
        this.xp += xp;
        System.out.println("You gained " + xp + " xp! Your xp is now " + this.xp + "/" + this.level.getXpRequired());
        this.checkLevelUp();
        if (this.xp > this.level.getXpRequired()) {
            this.xp = this.level.getXpRequired();
        }
    }

    /**
     * Check if the cat has leveled up and update the level and xp accordingly
     */
    private void checkLevelUp() {
        if (this.xp >= this.level.getXpToLevelUp()) {
            if (this.level.isLast()) {
                System.out.println("You have reached the max level!");
                return;
            }
            this.level = this.level.next();
            this.level.applyStats(this);
            this.xp = 0;
        }
    }

    private void initializeItems() {
        this.items.put(Armor.class, 0);
        this.items.put(Boots.class, 0);
        this.items.put(Sword.class, 0);
    }

    public Cat(String name) {
        super(5, 0, 100, 1);
        this.name = name;
        if (name.equalsIgnoreCase("mimi")) {
            System.out.println("You found the easter egg!");
            Cat.IMAGE_PATH = "images/cats/white/";
            // Increase the cat's stats
            this.hp += 20;
            this.maxHp += 20;
            this.setStats(10, 10, 1.5);
        }
        System.out.println(Cat.IMAGE_PATH);
        this.defaultImage = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(IMAGE_PATH + "face2.png")));
        this.initializeItems();
    }

    public String getName() {
        return name;
    }

    public void take(Item item) {
        this.items.put(item.getClass(), this.items.getOrDefault(item.getClass(), 0) + 1);
        item.giveStats(this);
    }

    public HashMap<Class<? extends Item>, Integer> getItems() {
        return items;
    }

    public int getXp() {
        return this.xp;
    }

    public Level getLevel() {
        return this.level;
    }

    public String getLevelLabel() {
        return this.level.getLabel();
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setLevel(String level) {
        this.level = Level.get(level);
    }

    public void setStats(int atk, int def, double speed) {
        this.atk = atk;
        this.def = def;
        this.speed = speed;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return this.x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return this.y;
    }

    public void setPositions(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public Image getDefaultImage() {
        return this.defaultImage;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
