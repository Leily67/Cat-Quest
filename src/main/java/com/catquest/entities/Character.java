package com.catquest.entities;

import com.catquest.Main;
import com.catquest.screens.GameOverScreen;

public abstract class Character implements Entity {
    protected int atk;
    protected int def;
    protected int hp;
    protected double speed;
    protected int maxHp;

    public Character(int atk, int def, int hp, double speed) {
        this.atk = atk;
        this.def = def;
        this.hp = hp;
        this.maxHp = hp;
        this.speed = speed;
    }

    @Override
    public boolean attack() {
        return false;
    }

    @Override
    public void moveRight() {}

    @Override
    public void moveLeft() {}

    @Override
    public void moveUp() {}

    @Override
    public void moveDown() {}

    @Override
    public int getAtk() {
        return this.atk;
    }

    @Override
    public int getDef() {
        return this.def;
    }

    @Override
    public int getHp() {
        return this.hp;
    }

    @Override
    public double getSpeed() {
        return this.speed;
    }

    @Override
    public int getMaxHp() {
        return this.maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }
    public void takeDmg(int dmg) {
        this.hp = Math.max(this.hp - dmg, 0);
        if (this.hp == 0 && this instanceof Cat) {
            try {
                Main.stage.setScene(GameOverScreen.getScene());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}
