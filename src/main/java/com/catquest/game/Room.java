package com.catquest.game;

import com.catquest.Main;
import com.catquest.entities.Enemy;
import javafx.scene.image.*;
import javafx.scene.SnapshotParameters;
import javafx.scene.paint.Color;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.*;
import java.util.Objects;

public abstract class Room {
    private static int instance = 1;
    private final int id = instance;
    protected boolean isOpen = true;
    protected Type type;
    protected List<Door> doors;
    protected boolean[] doorsPositions = new boolean[4];
    protected int column;
    protected int row;
    protected Image background;
    protected boolean generated=false;
    protected List<Integer> xRoomPosition=new ArrayList<>();
    protected List<Integer> yRoomPosition=new ArrayList<>();
    protected List<Integer> entitiesID=new ArrayList<>(); // 1:barrel 2:spikes 3:?? 4:enemy 5: 6:portal 7:npc 8:Sword 9:Boots 10:Armor
    protected List<int[]> listObstacle=new ArrayList<>();
    protected List<int[]> listSpikes=new ArrayList<>();
    protected List<int[]> listEnemies=new ArrayList<>();
    protected List<Enemy> listEnemiesClass=new ArrayList<>();
    protected List<int[]> itemPos=new ArrayList<>();

    protected Image item=null;

    public Room(int row, int column) {
        instance++;
        this.column = column;
        this.row = row;
        this.initializeDoorsPositions();
    }

    public List<Integer> getEntitiesOnPosition() {
        return entitiesID;
    }
    public void addEntitiesOnPosition(int entitiesID) {
        this.entitiesID.add(entitiesID);
    }
    public List<Integer> getxRoomPosition() {
        return xRoomPosition;
    }
    public void setxRoomPosition(List<Integer> xRoomPosition) {
        this.xRoomPosition.addAll(xRoomPosition);
    }

    public List<Integer> getyRoomPosition() {
        return yRoomPosition;
    }

    public void setyRoomPosition(List<Integer> yRoomPosition) {
        this.yRoomPosition.addAll(yRoomPosition);
    }

    public boolean isGenerated() {
        return generated;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }

    public Image getItem() {
        return item;
    }

    public void setItem(Image item) {
        this.item = item;
    }

    public enum Type {
        SPAWN,
        END,
        RANDOM
    }

    public void addNewObstacle(int xmin, int xmax, int ymin, int ymax) {
        listObstacle.add(new int[]{xmin,xmax,ymin,ymax});
    }

    public List<int[]> getListObstacle() {
        return listObstacle;
    }

    public void addNewSpikes(int xmin, int xmax, int ymin, int ymax) {
        listSpikes.add(new int[]{xmin,xmax,ymin,ymax});
    }

    public List<int[]> getListSpikes() {
        return listSpikes;
    }
    public void addListEnemies(int xmin, int xmax, int ymin, int ymax) {
        listEnemies.add(new int[]{xmin,xmax,ymin,ymax});
    }
    public List<int[]> getListEnemies() {
        return listEnemies;
    }

    public void addListEnemiesClass(Enemy enemy) {
        listEnemiesClass.add(enemy);
    }
    public List<Enemy> getListEnemiesClass() {
        return listEnemiesClass;
    }

    public void addItemPos(int xmin, int xmax, int ymin, int ymax) {
        itemPos.add(new int[]{xmin,xmax,ymin,ymax});
    }
    public void itemTaked() {
        itemPos.clear();
        int index=-1;
        for (int i = 0; i < entitiesID.size(); i++) {
            if (entitiesID.get(i).equals(8) || entitiesID.get(i).equals(9)|| entitiesID.get(i).equals(10)) {
                index = i;
            }
        }
        entitiesID.removeIf(integer -> integer.equals(8));
        entitiesID.removeIf(integer -> integer.equals(9));
        entitiesID.removeIf(integer -> integer.equals(10));//removes id
        if(index !=-1){
            xRoomPosition.remove(index);
            yRoomPosition.remove(index);
        }

    }

    public void clearEnemy(){
        if(entitiesID.isEmpty()){
            return;
        }
        for (int i = 0; i < entitiesID.size(); i++) {
            if (entitiesID.get(i).equals(4)) {
                entitiesID.set(i,0);
            }
        }
    }
    public void clearOneEnemy(int index){
        if(entitiesID.isEmpty()){
            return;
        }
        for (int i = 0; i < entitiesID.size(); i++) {
            if (entitiesID.get(i).equals(4)) {
                if(index<=0){
                    entitiesID.set(i,0);
                    break;
                }
                index--;
            }
        }
    }

//    public void clearOneEnemy(int count){
//        listEnemiesClass.remove(count);
//    }
    public List<int[]> getItemPos() {
        return itemPos;
    }

    public void clearList() {
        this.listObstacle.clear();
        this.listSpikes.clear();
        this.listEnemies.clear();
    }

    private void initializeDoorsPositions() {
        Arrays.fill(this.doorsPositions, false);
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setDoors(List<Door> doors) {
        this.doors = doors;
    }

    public List<Door> getDoors() {
        return doors;
    }

    public void open() {
        isOpen = true;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void close() {
        isOpen = false;
    }

    public Image getBackground() {
        return this.background;
    }

    public void setBackground(String world, String filename) {
        this.setDoorsPositions();
        this.background = new Image(Objects.requireNonNull(
                Main.class.getResourceAsStream("images/worlds/" + world + "/" + this.formatFilename(filename) + ".png")
        ));
        ImageView imageView = getImageView();
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        this.background = imageView.snapshot(params, null);
    }

    private String formatFilename(String filename) {
        // If the room has doors on the top and bottom only, or left and right only, then we need to add a "v" to the filename
        if ((this.doorsPositions[0] && this.doorsPositions[2] && !this.doorsPositions[1] && !this.doorsPositions[3]) ||
                (this.doorsPositions[1] && this.doorsPositions[3] && !this.doorsPositions[0] && !this.doorsPositions[2])) {
            filename = filename + "v";
        }
        return filename;
    }

    private void setDoorsPositions() {
        for (Door door : this.doors) {
            switch (door.getPosition()) {
                case TOP:
                    this.doorsPositions[0] = true;
                    break;
                case LEFT:
                    this.doorsPositions[1] = true;
                    break;
                case BOTTOM:
                    this.doorsPositions[2] = true;
                    break;
                case RIGHT:
                    this.doorsPositions[3] = true;
                    break;
            }
        }
    }

    private ImageView getImageView() {
        double rotation = 0;
        // Check if the room has only 1 door and then rotate the image accordingly
        if (this.doorsPositions[0] && !this.doorsPositions[1] && !this.doorsPositions[2] && !this.doorsPositions[3]) {
            // Top door is present
            rotation = 180;
        } else if (!this.doorsPositions[0] && this.doorsPositions[1] && !this.doorsPositions[2] && !this.doorsPositions[3]) {
            // Left door is present
            rotation = 90;
        } if (!this.doorsPositions[0] && !this.doorsPositions[1] && !this.doorsPositions[2] && this.doorsPositions[3]) {
           // Right door is present
            rotation = -90;
        }
        // If the room has doors on the left and the right only, then we need to rotate the image by 90 degrees
        else if (this.doorsPositions[1] && this.doorsPositions[3] && !this.doorsPositions[0] && !this.doorsPositions[2]) {
            // Left and right doors are present
            rotation = 90;
        }
        // Check if the room has 2 doors on adjacent walls and then rotate the image accordingly
        else if (this.doorsPositions[0] && this.doorsPositions[1] && !this.doorsPositions[2] && !this.doorsPositions[3]) {
            // Top and left doors are present
            rotation = 180;
        } else if (!this.doorsPositions[0] && this.doorsPositions[1] && this.doorsPositions[2] && !this.doorsPositions[3]) {
            // Left and bottom doors are present
            rotation = 90;
        } else if (this.doorsPositions[0] && !this.doorsPositions[1] && !this.doorsPositions[2] && this.doorsPositions[3]) {
            // Top and right doors are present
            rotation = -90;
        }
        // Check if the room has 3 doors and then rotate the image accordingly
        else  if (!this.doorsPositions[0] && this.doorsPositions[1] && this.doorsPositions[2] && this.doorsPositions[3]) {
            // Left, bottom and right doors are present
            rotation = -90;
        } else if (this.doorsPositions[0] && !this.doorsPositions[1] && this.doorsPositions[2] && this.doorsPositions[3]) {
            // Top, bottom and right doors are present
            rotation = 180;
        } else if (this.doorsPositions[0] && this.doorsPositions[1] && !this.doorsPositions[2] && this.doorsPositions[3]) {
            // Top, left and right doors are present
            rotation = 90;
        }

        ImageView imageView = new ImageView(this.background);
        imageView.setRotate(rotation);
        return imageView;
    }

    public boolean hasDoorAt(Door.Position position) {
        for (Door door : this.doors) {
            if (door.getPosition() == position) {
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }

}
