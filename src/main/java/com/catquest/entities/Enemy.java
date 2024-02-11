package com.catquest.entities;

import com.catquest.controllers.MovementController;
import com.catquest.game.Game;
import com.catquest.helpers.Movement;
import javafx.scene.image.Image;

public abstract class Enemy extends Character {

    public static final String SPRITE_PATH = "images/entities/boss";
    protected Image enemyImg;
    protected int row;
    protected int col;
    public Enemy(int atk, int def, int hp, double speed,Image img) {
        super(atk, def, hp, speed);
        this.enemyImg = img;
     /**/   this.updateStatsCorrespondingToCurrentWorld();
    }

    private void updateStatsCorrespondingToCurrentWorld() {
        Game game = MovementController.game;
        if (game.getCurrentWorldId() == 1) {
            this.setHp(this.getHp() * 2);
            System.out.println("HP: " + this.getHp());
        } else {
            this.setHp(this.getHp() * 3);
            System.out.println("HP: " + this.getHp());
        }
    }

    public void setHp(int hp){
        super.hp += hp;
    }
    public Image getEnemyImg() {
        return enemyImg;
    }

    public void setEnemyImg(Image enemyImg) {
        this.enemyImg = enemyImg;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
