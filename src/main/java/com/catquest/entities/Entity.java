package com.catquest.entities;

public interface Entity {
    boolean attack();
    void moveRight();
    void moveLeft();
    void moveUp();
    void moveDown();
    int getAtk();
    int getDef();
    int getHp();
    double getSpeed();
    int getMaxHp();

}
