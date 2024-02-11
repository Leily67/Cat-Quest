package com.catquest.controllers;

import com.catquest.Main;
import com.catquest.entities.Cat;
import com.catquest.entities.Enemy;
import com.catquest.entities.enemies.Dmystan;
import com.catquest.entities.enemies.ElonMusk;
import com.catquest.entities.enemies.UsainBolt;
import com.catquest.entities.items.Armor;
import com.catquest.entities.items.Boots;
import com.catquest.entities.items.Sword;
import com.catquest.game.Door;
import com.catquest.game.Game;
import com.catquest.game.Menu;
import com.catquest.game.rooms.EndRoom;
import com.catquest.game.rooms.SpawnRoom;
import com.catquest.helpers.Audio;
import com.catquest.helpers.Controls;
import com.catquest.helpers.Movement;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MovementController {
    public static Game game;
    private final BooleanProperty upPressed = new SimpleBooleanProperty();
    private final BooleanProperty downPressed = new SimpleBooleanProperty();
    private final BooleanProperty leftPressed = new SimpleBooleanProperty();
    private final BooleanProperty rightPressed = new SimpleBooleanProperty();
    private final BooleanProperty upAttackPressed = new SimpleBooleanProperty();
    private final BooleanProperty downAttackPressed = new SimpleBooleanProperty();
    private final BooleanProperty leftAttackPressed = new SimpleBooleanProperty();
    private final BooleanProperty rightAttackPressed = new SimpleBooleanProperty();
    private final BooleanProperty dodgePressed = new SimpleBooleanProperty();
    private final BooleanBinding keyPressed = upPressed.or(leftPressed).or(downPressed).or(rightPressed).or(dodgePressed).or(upAttackPressed).or(downAttackPressed).or(leftAttackPressed).or(rightAttackPressed);
    private final Image face1 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(Cat.IMAGE_PATH + "face1.png")));
    private final Image face2 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(Cat.IMAGE_PATH + "face2.png")));
    private final Image face3 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(Cat.IMAGE_PATH + "face3.png")));
    private final Image back1 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(Cat.IMAGE_PATH + "back1.png")));
    private final Image back2 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(Cat.IMAGE_PATH + "back2.png")));
    private final Image back3 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(Cat.IMAGE_PATH + "back3.png")));
    private final Image left1 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(Cat.IMAGE_PATH + "left1.png")));
    private final Image left2 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(Cat.IMAGE_PATH + "left2.png")));
    private final Image left3 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(Cat.IMAGE_PATH + "left3.png")));
    private final Image right1 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(Cat.IMAGE_PATH + "right1.png")));
    private final Image right2 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(Cat.IMAGE_PATH + "right2.png")));
    private final Image right3 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(Cat.IMAGE_PATH + "right3.png")));
    private final Image back4 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(Cat.IMAGE_PATH + "attack/back4.png")));
    private final Image face4 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(Cat.IMAGE_PATH + "attack/face4.png")));
    private final Image left4 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(Cat.IMAGE_PATH + "attack/left4.png")));
    private final Image right4 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(Cat.IMAGE_PATH + "attack/right4.png")));
    private int animationStep = 1;
    private final Image Obstacles = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/entities/barrel.png")));
    private final Image npc = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/npc.png")));
    //    private final Image portal = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/entities/cat.png")));
    private final Image Spikes = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/entities/spikes.png")));
    private final Image Skull = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/entities/skull.png")));
    @FXML
    private ImageView sprite;
    @FXML
    private AnchorPane scene;
    @FXML
    private ImageView room;
    @FXML
    private GridPane roomObstacles;
    @FXML
    private GridPane minimap;
    protected List<Integer> xCoordinates = new ArrayList<>();
    protected List<Integer> yCoordinates = new ArrayList<>();
    @FXML
    private ProgressBar healthBar;
    @FXML
    private Text currentHp;
    @FXML
    private Text maxHp;
    @FXML
    private Text playerName;
    @FXML
    private Text currentDamage;
    @FXML
    private Text currentDefense;
    @FXML
    private Text currentSpeed;
    @FXML
    private Text location;
    @FXML
    private Text score;
    @FXML
    private VBox dialog;
    @FXML
    private Text dialogText;
    @FXML
    private Text xp;

    public void initialize(
            ImageView sprite,
            AnchorPane scene,
            ImageView room,
            GridPane roomObstacles,
            GridPane minimap,
            ProgressBar healthBar,
            Text currentHp,
            Text maxHp,
            Text currentDamage,
            Text currentDefense,
            Text currentSpeed,
            Text location,
            Text score,
            VBox dialog,
            Text dialogText,
            Text xp
    ) {
        this.room = room;
        this.sprite = sprite;
        this.scene = scene;
        this.minimap = minimap;
        this.roomObstacles = roomObstacles;
        this.location = location;
        this.healthBar = healthBar;
        this.currentHp = currentHp;
        this.maxHp = maxHp;
        this.currentDamage = currentDamage;
        this.currentDefense = currentDefense;
        this.currentSpeed = currentSpeed;
        this.score = score;
        this.dialog = dialog;
        this.dialogText = dialogText;
        this.xp = xp;
    }

    public void initialize(ImageView sprite, AnchorPane scene, ImageView room,VBox dialog, Text dialogText) {
        this.sprite = sprite;
        this.room = room;
        this.scene = scene;
        this.dialog = dialog;
        this.dialogText = dialogText;
    }

    public void makeSpriteMovable() {
        this.setSpriteSpawnPosition(null);
        this.registerKeyListeners();
        keyPressed.addListener(((observableValue, state, t1) -> {
            if (!state) {
                timer.start();
            } else {
                timer.stop();
            }
        }));
    }

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            double movementSpeed = game.getPlayer().getSpeed();

//            System.out.println("Sprite is moving");
            if (upPressed.get()) {
                updateSpriteAnimation(back1, back2, back3, null);
                if (sprite.getLayoutY() > Movement.TOP_LIMIT && !obstacleExist(movementSpeed, "top")) {
                    if (dodgePressed.get()) {
                        sprite.setLayoutY(sprite.getLayoutY() - movementSpeed * 2.5);
                    } else {
                        sprite.setLayoutY(sprite.getLayoutY() - movementSpeed);
                    }
                    closeToNPC();
                    onEntities();
                }
            }

            if (downPressed.get()) {
                updateSpriteAnimation(face1, face2, face3, null);
                if (sprite.getLayoutY() < Movement.BOTTOM_LIMIT && !obstacleExist(movementSpeed, "bottom")) {
                    if (dodgePressed.get()) {
                        sprite.setLayoutY(sprite.getLayoutY() + movementSpeed * 2.5);
                    } else {
                        sprite.setLayoutY(sprite.getLayoutY() + movementSpeed);
                    }
                    closeToNPC();
                    onEntities();
                }
            }

            if (leftPressed.get()) {
                updateSpriteAnimation(left1, left2, left3, null);
                if (sprite.getLayoutX() > Movement.LEFT_LIMIT && !obstacleExist(movementSpeed, "left")) {
                    if (dodgePressed.get()) {
                        sprite.setLayoutX(sprite.getLayoutX() - movementSpeed * 2.5);
                    } else {
                        sprite.setLayoutX(sprite.getLayoutX() - movementSpeed);
                    }
                    closeToNPC();
                    onEntities();
                }
            }

            if (rightPressed.get()) {
                updateSpriteAnimation(right1, right2, right3, null);
                if (sprite.getLayoutX() < Movement.RIGHT_LIMIT && !obstacleExist(movementSpeed, "right")) {
                    if (dodgePressed.get()) {
                        sprite.setLayoutX(sprite.getLayoutX() + movementSpeed * 2.5);
                    } else {
                        sprite.setLayoutX(sprite.getLayoutX() + movementSpeed);
                    }
                    closeToNPC();
                    onEntities();
                }
            }

            if (upAttackPressed.get()) {
                updateSpriteAnimation(back1, back2, back3, back4);
//                System.out.println("atk up button");
                atkEnemies("top");
            }

            if (downAttackPressed.get()) {
                updateSpriteAnimation(face1, face2, face3, face4);
//                System.out.println("atk down button");
                atkEnemies("bottom");
            }

            if (leftAttackPressed.get()) {
                updateSpriteAnimation(left1, left2, left3, left4);
//                System.out.println("atk left button");
                atkEnemies("left");
            }

            if (rightAttackPressed.get()) {
                updateSpriteAnimation(right1, right2, right3, right4);
//                System.out.println("atk right button");
                atkEnemies("right");
            }

            // Update the player position in the game once the sprite has moved
            game.getPlayer().setPositions((int) sprite.getLayoutX(), (int) sprite.getLayoutY());

            if (!game.getIsFinished() && game.getCurrentRoom() != null) {
                if (upPressed.get() && game.getCurrentRoom().hasDoorAt(Door.Position.TOP) && sprite.getLayoutY() <= Movement.TOP_LIMIT) {
                    int[] area = Door.Position.TOP.area();
                    if (sprite.getLayoutX() >= area[0] && sprite.getLayoutX() <= area[1] && game.getCurrentRoom().isOpen()) {
                        setSpriteSpawnPosition(Door.Position.BOTTOM);
                        game.changeRoom(Door.Position.TOP, room, minimap);
                        updateGrid();
                        updateCollision();
                    }
                }
                if (downPressed.get() && game.getCurrentRoom().hasDoorAt(Door.Position.BOTTOM) && sprite.getLayoutY() >= Movement.BOTTOM_LIMIT) {
                    int[] area = Door.Position.BOTTOM.area();
                    if (sprite.getLayoutX() >= area[0] && sprite.getLayoutX() <= area[1] && game.getCurrentRoom().isOpen()) {
                        setSpriteSpawnPosition(Door.Position.TOP);
                        game.changeRoom(Door.Position.BOTTOM, room, minimap);
                        updateGrid();
                        updateCollision();
                    }
                }
                if (leftPressed.get() && game.getCurrentRoom().hasDoorAt(Door.Position.LEFT) && sprite.getLayoutX() <= Movement.LEFT_LIMIT) {
                    int[] area = Door.Position.LEFT.area();
                    if (sprite.getLayoutY() >= area[0] && sprite.getLayoutY() <= area[1] && game.getCurrentRoom().isOpen()) {
                        setSpriteSpawnPosition(Door.Position.RIGHT);
                        game.changeRoom(Door.Position.LEFT, room, minimap);
                        updateGrid();
                        updateCollision();
                    }
                }
                if (rightPressed.get() && game.getCurrentRoom().hasDoorAt(Door.Position.RIGHT) && sprite.getLayoutX() >= Movement.RIGHT_LIMIT) {
                    int[] area = Door.Position.RIGHT.area();
                    if (sprite.getLayoutY() >= area[0] && sprite.getLayoutY() <= area[1] && game.getCurrentRoom().isOpen()) {
                        setSpriteSpawnPosition(Door.Position.LEFT);
                        game.changeRoom(Door.Position.RIGHT, room, minimap);
                        updateGrid();
                        updateCollision();
                    }
                }
            }
        }

    };

    private void updateSpriteAnimation(Image frame1, Image frame2, Image frame3, Image frame4) {
        switch (animationStep % 3) {
            case 0:
                sprite.setImage(frame1);
                break;
            case 1:
                sprite.setImage(frame2);
                break;
            case 2:
                sprite.setImage(frame3);
                break;
        }
        if (frame4 != null) {
            Audio.startCatAttackSoundEffect();
            sprite.setImage(frame4);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    sprite.setImage(frame2);
                }
            }, 100);
        }
        animationStep++;
    }

    private void setSpriteSpawnPosition(Door.Position position) {
        if (position == null) {
            // The sprite spawn at the center of the screen if no position is given
            sprite.setLayoutX(game.getPlayer().getX());
            sprite.setLayoutY(game.getPlayer().getY());
        } else {
            sprite.setLayoutX(position.getX());
            sprite.setLayoutY(position.getY());
        }
    }

    private void registerKeyListeners() {
        this.scene.setOnKeyPressed(e -> this.checkForKeyCode(e.getCode(), true));
        this.scene.setOnKeyReleased(e -> this.checkForKeyCode(e.getCode(), false));
    }

    private void checkForKeyCode(KeyCode code, boolean isPressed) {
        String codeString = code.toString();
        if (codeString.equals(Controls.bindings.get("Move Up"))) {
            upPressed.set(isPressed);
        }
        if (codeString.equals(Controls.bindings.get("Move Left"))) {
            leftPressed.set(isPressed);
        }
        if (codeString.equals(Controls.bindings.get("Move Down"))) {
            downPressed.set(isPressed);
        }
        if (codeString.equals(Controls.bindings.get("Move Right"))) {
            rightPressed.set(isPressed);
        }
        if (codeString.equals(Controls.bindings.get("Dodge"))) {
            dodgePressed.set(isPressed);
        }
        if (codeString.equals(Controls.bindings.get("Up Attack"))) {
            upAttackPressed.set(isPressed);
        }
        if (codeString.equals(Controls.bindings.get("Left Attack"))) {
            leftAttackPressed.set(isPressed);
        }
        if (codeString.equals(Controls.bindings.get("Down Attack"))) {
            downAttackPressed.set(isPressed);
        }
        if (codeString.equals(Controls.bindings.get("Right Attack"))) {
            rightAttackPressed.set(isPressed);
        }
        if (codeString.equals(Controls.bindings.get("Open Menu"))) {
            try {
                Menu.show();
            } catch (IOException ioException) {
                System.out.println("Error opening menu");
            }
        }
    }

    private void updateGrid() {
        if (roomObstacles == null) return;
        roomObstacles.getChildren().clear();
        Random random = new Random();
        if (game.getCurrentRoom().isGenerated() && !(game.getCurrentRoom() instanceof SpawnRoom) && !(game.getCurrentRoom() instanceof EndRoom)) {
            System.out.println("enter old room-----------------");
            xCoordinates.clear();
            yCoordinates.clear();
//            System.out.println("generate from old room");
//            System.out.println("BEFORE CLEAR ENEMY LIST");
//
//            System.out.println("ID list:" +game.getCurrentRoom().getEntitiesOnPosition());
//            System.out.println("xpos: " +game.getCurrentRoom().getxRoomPosition());
//            System.out.println("ypos: " +game.getCurrentRoom().getyRoomPosition());

//            game.getCurrentRoom().clearEnemy();

//            System.out.println("AFTER CLEAR ENEMY LIST");
//
//            System.out.println("ID list:" +game.getCurrentRoom().getEntitiesOnPosition());
//            System.out.println("xpos: " +game.getCurrentRoom().getxRoomPosition());
//            System.out.println("ypos: " +game.getCurrentRoom().getyRoomPosition());


            for (int i = 0; i < game.getCurrentRoom().getEntitiesOnPosition().size(); i++) {
                int row = game.getCurrentRoom().getxRoomPosition().get(i);
                int col = game.getCurrentRoom().getyRoomPosition().get(i);
                //System.out.println("xpos" +game.getCurrentRoom().getyRoomPosition());
                //System.out.println("ypos" + game.getCurrentRoom().getyRoomPosition());
                if (game.getCurrentRoom().getEntitiesOnPosition().get(i) == 1) {
                    generateImage(Obstacles, row, col, 50, 50);
                }
                if (game.getCurrentRoom().getEntitiesOnPosition().get(i) == 2) {
                    generateImage(Spikes, row, col, 50, 50);
                }
                if (game.getCurrentRoom().getEntitiesOnPosition().get(i) == 4) {
                    generateImage(Skull, row, col, 50, 50);
                }
                if (game.getCurrentRoom().getEntitiesOnPosition().get(i) == 0) {
                    generateImage(Skull, row, col, 50, 50);
                }
                if (game.getCurrentRoom().getEntitiesOnPosition().get(i) >= 8) {
                    generateImage(game.getCurrentRoom().getItem(), row, col, 50, 50);
                }
            }
            game.getCurrentRoom().clearEnemy();
        } else if (game.getCurrentRoom() instanceof SpawnRoom) {
            System.out.println("enter old room-----------------");
            roomObstacles.getChildren().clear();
            generateImage(game.getCurrentWorld().getPortal(), 4, 3, 200, 150);
            generateImage(npc, 0, 7, 100, 100);
        } else if (game.getCurrentRoom() instanceof EndRoom) {
            System.out.println("enter old room-----------------");
            roomObstacles.getChildren().clear();
            generateImage(game.getCurrentWorld().getPortal(), 4, 3, 200, 150);
            game.getCurrentRoom().addEntitiesOnPosition(6);
        } else if (random.nextInt(5) == 0 && !game.getCurrentRoom().isGenerated()) {
            System.out.println("enter new room-----------------");
            xCoordinates.clear();
            yCoordinates.clear();
            int itemChoice = random.nextInt(3);
            Image item = null;
            switch (itemChoice) {
                case 0:
                    item = new Sword().getItemImg();
                    break;
                case 1:
                    item = new Boots().getItemImg();
                    break;
                case 2:
                    item = new Armor().getItemImg();
                    break;

            }
            //generateImage(game.getCurrentWorld().getPortal(), 4, 3, 150, 150);
//             game.getCurrentRoom().addEntitiesOnPosition(6);
            game.getCurrentRoom().setItem(item);
            generateImage(item, 4, 4, 50, 50);
            game.getCurrentRoom().addEntitiesOnPosition(8 + itemChoice);

            generateImage(Obstacles, 1, 1, 50, 50);
            generateImage(Obstacles, 1, 2, 50, 50);
            generateImage(Obstacles, 1, 6, 50, 50);
            generateImage(Obstacles, 1, 7, 50, 50);

            generateImage(Obstacles, 2, 1, 50, 50);
            generateImage(Obstacles, 2, 2, 50, 50);
            generateImage(Obstacles, 2, 6, 50, 50);
            generateImage(Obstacles, 2, 7, 50, 50);

            generateImage(Obstacles, 6, 1, 50, 50);
            generateImage(Obstacles, 6, 2, 50, 50);
            generateImage(Obstacles, 6, 6, 50, 50);
            generateImage(Obstacles, 6, 7, 50, 50);

            generateImage(Obstacles, 7, 1, 50, 50);
            generateImage(Obstacles, 7, 2, 50, 50);
            generateImage(Obstacles, 7, 6, 50, 50);
            generateImage(Obstacles, 7, 7, 50, 50);

            for (int i = 0; i < 16; i++) {
                game.getCurrentRoom().addEntitiesOnPosition(1);
            }
            game.getCurrentRoom().setxRoomPosition(xCoordinates);
            game.getCurrentRoom().setyRoomPosition(yCoordinates);
            game.getCurrentRoom().setGenerated(true);
        } else {
            System.out.println("enter new room-----------------");
            xCoordinates.clear();
            yCoordinates.clear();
            int enemyNb = random.nextInt(1, 4);
            int max = enemyNb;
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    if ((row == 0 && col == 4) || (row == 4 && col == 0) || (row == 4 && col == 8) || (row == 8 && col == 4)) {
//                        System.out.println("impossible obstacles"); IN FRONT OF DOORS
                    } else if (random.nextInt(7) == 0 && enemyNb > 0) { //ENEMY GENERATION
                        game.getCurrentRoom().close();
                        game.getCurrentRoom().addEntitiesOnPosition(4);
                        Enemy d = null;
                        int num = random.nextInt(3);
                        if (num == 0) {
                            d = new Dmystan();
                        } else if (num == 1) {
                            d = new UsainBolt();
                        } else {
                            d = new ElonMusk();
                        }
                        generateImage(d.getEnemyImg(), row, col, 50, 50);
                        d.setRow(row);
                        d.setCol(col);
                        game.getCurrentRoom().addListEnemiesClass(d);
                        System.out.println(("added an enemy ! current enemy nb: "+game.getCurrentRoom().getListEnemiesClass().size()));
                        System.out.println(("his HP: "+game.getCurrentRoom().getListEnemiesClass().get((enemyNb-max)*-1).getHp()));
                        enemyNb--;
                    } else if (random.nextInt(30) == 5) { //BARREL GENERATION
                        generateImage(Obstacles, row, col, 50, 50);
                        game.getCurrentRoom().addEntitiesOnPosition(1);
                    } else if (random.nextInt(20) == 5) { //SPIKES GENERATION
                        generateImage(Spikes, row, col, 50, 50);
                        game.getCurrentRoom().addEntitiesOnPosition(2);
                    }
                }
            }
            game.getCurrentRoom().setxRoomPosition(xCoordinates);
            game.getCurrentRoom().setyRoomPosition(yCoordinates);
            game.getCurrentRoom().setGenerated(true);
        }
    }

    private void updateCollision() {
        game.getCurrentRoom().clearList();
        if(game.getCurrentRoom().getEntitiesOnPosition().isEmpty()){
            return;
        }
        System.out.println("LOOPING ON: "+game.getCurrentRoom().getEntitiesOnPosition());
        for (int i = 0; i < game.getCurrentRoom().getEntitiesOnPosition().size(); i++) {
            if(roomObstacles.getChildren().get(i) instanceof ImageView imageView) {
//                System.out.println("index is:"+i);
                int col = GridPane.getColumnIndex(imageView);
                int row = GridPane.getRowIndex(imageView);
//                System.out.println("IMAGEVIEW FOUND at "+  "(" + col+"," + row+")");
                int xmin = 650 + col * 55;
                int xmax = 730 + col * 55;
                int ymin = 230 + row * 55;
                int ymax = 330 + row * 55;
//                System.out.println("ImageView found at position (" + col + ", " + row + ")");
//                System.out.println("Position: "+xmin+" "+xmax+" "+ymin+" "+ymax);
                if (game.getCurrentRoom().getEntitiesOnPosition().get(i) == 1) { // UPDATE BARREL COLLISION
//                    System.out.println("BARREL FOUND AT: " +  "(" + col+"," + row+")");
                    game.getCurrentRoom().addNewObstacle(xmin, xmax, ymin, ymax);
                }
                if (game.getCurrentRoom().getEntitiesOnPosition().get(i) == 2) { // UPDATE SPIKES COLLISION
//                    System.out.println("SPIKES FOUND AT: " + "(" + col+"," + row+")");
                    game.getCurrentRoom().addNewSpikes(xmin, xmax, ymin, ymax);
                }
                if (game.getCurrentRoom().getEntitiesOnPosition().get(i) == 4) { // UPDATE enemies COLLISION
                    game.getCurrentRoom().addListEnemies(xmin, xmax, ymin, ymax);
//                Dmystan d = new Dmystan();
//                d.setCol(col);
//                d.setRow(row);
//                game.getCurrentRoom().addListEnemiesClass(d);
                }
                if (game.getCurrentRoom().getEntitiesOnPosition().get(i) == 6) { // UPDATE PORTAL COLLISION
                    game.getCurrentRoom().addItemPos(xmin, xmax, ymin, ymax);
                }
                if (game.getCurrentRoom().getEntitiesOnPosition().get(i) >= 8) { //UPDATE ITEMS COLLISION
                    game.getCurrentRoom().addItemPos(xmin, xmax, ymin, ymax);
                }
            }

        }
//        System.out.println("ID list:" +game.getCurrentRoom().getEntitiesOnPosition());
//        System.out.println("xpos: " +game.getCurrentRoom().getxRoomPosition());
//        System.out.println("ypos: " +game.getCurrentRoom().getyRoomPosition());
//        System.out.println("List of obstacle with each point xmin,xmax,ymin,ymax");
//        System.out.println(game.getCurrentRoom().getListObstacle().stream().map(Arrays::toString).collect(Collectors.joining(" , ", "[ ", " ]")));
//        System.out.println("List of spikes with each point xmin,xmax,ymin,ymax");
//        System.out.println(game.getCurrentRoom().getListSpikes().stream().map(Arrays::toString).collect(Collectors.joining(" , ", "[ ", " ]")));
//        System.out.println("ITEM POSITION with each point xmin,xmax,ymin,ymax");
//        System.out.println(game.getCurrentRoom().getItemPos().stream().map(Arrays::toString).collect(Collectors.joining(" , ", "[ ", " ]")));

    }
    public boolean atkEnemies(String direction){ //used when attacking
        //check all position
        boolean exist = false;

        if (!this.enemiesRemain()) {
            return exist;
        }

        double xPos = sprite.getLayoutX();
        double yPos = sprite.getLayoutY();

        if(game.getCurrentRoom().getListEnemies().isEmpty()){
            game.getCurrentRoom().open();
            return exist;
        }

        boolean killed = false;
        int count = 0;
        switch(direction){
            case "top":
                for(int[] array: game.getCurrentRoom().getListEnemies()) {
                    if (xPos > array[0] && xPos < array[1] && yPos -20 < array[3] && yPos >= array[3] ) {
                        System.out.println("CAN atk");
                        //enemy lost hp from cat
                        atk(count);
                        exist = true;
                        if(game.getCurrentRoom().getListEnemiesClass().get(count).getHp()<=0){
                            killed = true;

                            int col = game.getCurrentRoom().getListEnemiesClass().get(count).getCol();
                            int row = game.getCurrentRoom().getListEnemiesClass().get(count).getRow();
//                            System.out.println("position of enemy ("+col+","+row+")");
//                            System.out.println("ID(4): "+game.getCurrentRoom().getEntitiesOnPosition());
//                            System.out.println("position x: "+game.getCurrentRoom().getxRoomPosition());
//                            System.out.println("position y: "+game.getCurrentRoom().getyRoomPosition());
                            for (int i = 0; i < roomObstacles.getChildren().size(); i++) {
                                if (GridPane.getColumnIndex(roomObstacles.getChildren().get(i)) == col && GridPane.getRowIndex(roomObstacles.getChildren().get(i)) == row) {

                                    roomObstacles.getChildren().get(i).setOpacity(0);
//                                    generateImage(Skull,row,col,50,50);

                                }
                            }
//                            game.getCurrentRoom().clearOneEnemy(count);
                            //remove in list enemy ID AND CLASS
                        }
                    }
                    count ++;
                }
                break;
            case "bottom":
                for(int[] array: game.getCurrentRoom().getListEnemies()) {
                    if (xPos > array[0] && xPos < array[1] && yPos + 20 > array[2] && yPos <= array[2]) {

                        System.out.println("can atk");
                        //enemy lost hp from cat
//                        System.out.println(game.getCurrentRoom().getListEnemiesClass().get(count).getHp());
                        atk(count);
//                        System.out.println(game.getCurrentRoom().getListEnemiesClass().get(count).getHp());
                        exist = true;
                        if(game.getCurrentRoom().getListEnemiesClass().get(count).getHp()<=0){
                            killed = true;
                            System.out.println("ENEMY dead");
                            int col = game.getCurrentRoom().getListEnemiesClass().get(count).getCol();
                            int row = game.getCurrentRoom().getListEnemiesClass().get(count).getRow();
//                            System.out.println("position of enemy ("+col+","+row+")");
//                            System.out.println("ID(4): "+game.getCurrentRoom().getEntitiesOnPosition());
//                            System.out.println("position x: "+game.getCurrentRoom().getxRoomPosition());
//                            System.out.println("position y: "+game.getCurrentRoom().getyRoomPosition());
                            for (int i = 0; i < roomObstacles.getChildren().size(); i++) {
                                if (GridPane.getColumnIndex(roomObstacles.getChildren().get(i)) == col && GridPane.getRowIndex(roomObstacles.getChildren().get(i)) == row) {
                                    roomObstacles.getChildren().get(i).setOpacity(0);
                                    game.getCurrentRoom().clearOneEnemy(count);
//                                    generateImage(Skull,row,col,50,50);
                                }
                            }
//                            game.getCurrentRoom().clearOneEnemy(count);
                            //remove in list enemy ID AND CLASS
                        }
                    }
                    count ++;
                }
                break;
            case "left":
                // code block
                for(int[] array: game.getCurrentRoom().getListEnemies()) {
                    if (yPos > array[2] && yPos < array[3] && xPos - 20 < array[1] && xPos >= array[1]) {
                        System.out.println("can atk");
                        //enemy lost hp from cat
//                        System.out.println(game.getCurrentRoom().getListEnemiesClass().get(count).getHp());
                        atk(count);
//                        System.out.println(game.getCurrentRoom().getListEnemiesClass().get(count).getHp());
                        exist = true;
                        if(game.getCurrentRoom().getListEnemiesClass().get(count).getHp()<=0){
                            killed = true;

                            int col = game.getCurrentRoom().getListEnemiesClass().get(count).getCol();
                            int row = game.getCurrentRoom().getListEnemiesClass().get(count).getRow();
//                            System.out.println("position of enemy ("+col+","+row+")");
//                            System.out.println("ID(4): "+game.getCurrentRoom().getEntitiesOnPosition());
//                            System.out.println("position x: "+game.getCurrentRoom().getxRoomPosition());
//                            System.out.println("position y: "+game.getCurrentRoom().getyRoomPosition());
                            System.out.println("position of enemy ("+col+","+row+")");
                            for (int i = 0; i < roomObstacles.getChildren().size(); i++) {
                                if (GridPane.getColumnIndex(roomObstacles.getChildren().get(i)) == col && GridPane.getRowIndex(roomObstacles.getChildren().get(i)) == row) {
                                    roomObstacles.getChildren().get(i).setOpacity(0);
                                    game.getCurrentRoom().clearOneEnemy(count);
                                }
                            }
//                            game.getCurrentRoom().clearOneEnemy(count);
                            //remove in list enemy ID AND CLASS
                        }
                    }
                    count ++;
                }
                break;
            case "right":
                // code block
                for(int[] array: game.getCurrentRoom().getListEnemies()) {
                    if (yPos > array[2] && yPos < array[3] && xPos + 20 > array[0] && xPos <= array[0]) {
                        System.out.println("can atk");
                        //enemy lost hp from cat
//                        System.out.println(game.getCurrentRoom().getListEnemiesClass().get(count).getHp());
                        atk(count);
//                        System.out.println(game.getCurrentRoom().getListEnemiesClass().get(count).getHp());
                        exist = true;
                        if(game.getCurrentRoom().getListEnemiesClass().get(count).getHp()<=0){
                            killed = true;

                            int col = game.getCurrentRoom().getListEnemiesClass().get(count).getCol();
                            int row = game.getCurrentRoom().getListEnemiesClass().get(count).getRow();
//                            System.out.println("position of enemy ("+col+","+row+")");
//                            System.out.println("ID(4): "+game.getCurrentRoom().getEntitiesOnPosition());
//                            System.out.println("position x: "+game.getCurrentRoom().getxRoomPosition());
//                            System.out.println("position y: "+game.getCurrentRoom().getyRoomPosition());
                            for (int i = 0; i < roomObstacles.getChildren().size(); i++) {
                                if (GridPane.getColumnIndex(roomObstacles.getChildren().get(i)) == col && GridPane.getRowIndex(roomObstacles.getChildren().get(i)) == row) {
                                    roomObstacles.getChildren().get(i).setOpacity(0);
                                    game.getCurrentRoom().clearOneEnemy(count);

                                }
                            }
                        }
                    }
                    count ++;
                }
                break;
        }

        if (killed) {
            System.out.println("Enemy killed, increase score and xp");
            game.setScore(game.getScore() + 30);
            GameController.updateScore(this.score);
            game.getPlayer().increaseXp(game.getPlayer().getXp() + 50);
            GameController.updateXp(this.xp);
            GameController.updateStats(healthBar, currentHp, maxHp, currentDamage, currentDefense, currentSpeed);
        }

        if (!this.enemiesRemain()) {
            game.getCurrentRoom().open();
        }

        return exist;
    }

    private boolean enemiesRemain(){
        boolean remain = false;
        for(int i = 0; i < game.getCurrentRoom().getListEnemiesClass().size(); i++){
            if(game.getCurrentRoom().getListEnemiesClass().get(i).getHp() > 0){
                remain = true;
            }
        }
        return remain;
    }

    public boolean obstacleExist(double movementSpeed, String direction) {
        boolean exist = false;

        double xPos = sprite.getLayoutX();
        double yPos = sprite.getLayoutY();

        switch (direction) {
            case "top":
                for (int[] array : game.getCurrentRoom().getListObstacle()) {
                    if (xPos > array[0] && xPos < array[1] && yPos - movementSpeed < array[3] && yPos >= array[3]) {
                        sprite.setLayoutY(array[3]);
                        exist = true;
                        break;
                    }
                }
                break;
            case "bottom":
                for (int[] array : game.getCurrentRoom().getListObstacle()) {
                    if (xPos > array[0] && xPos < array[1] && yPos + movementSpeed > array[2] && yPos <= array[2]) {
//                        System.out.println("to bot collision");
//                        System.out.println(xPos + " " + yPos + " " + Arrays.toString(array));
                        sprite.setLayoutY(array[2]);
                        exist = true;
                    }
                }
                break;
            case "left":
                // code block
                for (int[] array : game.getCurrentRoom().getListObstacle()) {
                    if (yPos > array[2] && yPos < array[3] && xPos - movementSpeed < array[1] && xPos >= array[1]) {
//                        System.out.println("to left collision");
//                        System.out.println(xPos + " " + yPos + " " + Arrays.toString(array));
                        sprite.setLayoutX(array[1]);
                        exist = true;
                    }
                }
                break;
            case "right":
                // code block
                for (int[] array : game.getCurrentRoom().getListObstacle()) {
                    if (yPos > array[2] && yPos < array[3] && xPos + movementSpeed > array[0] && xPos <= array[0]) {
//                        System.out.println("to right collision");
//                        System.out.println(xPos + " " + yPos + " " + Arrays.toString(array));
                        sprite.setLayoutX(array[0]);
                        exist = true;
                    }
                }
                break;
            default:

        }
        return exist;
    }

    public void onEntities() {
        onEntitiesDamaging();
        onItem();
        onEndingPortal();
    }

    public void onEntitiesDamaging() {

        double xPos = sprite.getLayoutX();
        double yPos = sprite.getLayoutY();

        for (int[] array : game.getCurrentRoom().getListSpikes()) { // when sprite go above spikes
            if (xPos > array[0] && xPos < array[1] && yPos > array[2] && yPos < array[3]) {
                if (new Random().nextInt(20) == 0) {
                    game.getPlayer().takeDmg(1);
                    GameController.updateHealthBar(this.healthBar, this.currentHp);
                }
                break;
            }
        }

        for(int[] array: game.getCurrentRoom().getListEnemies()) { // when sprite go above spikes

            if (xPos > array[0] && xPos < array[1] && yPos > array[2] && yPos < array[3]) {

                if (new Random().nextInt(20) == 0) {
                    game.getPlayer().takeDmg(1);
                    GameController.updateHealthBar(this.healthBar, this.currentHp);
                }
                break;
            }
        }
    }

    public void onItem() {
        if (roomObstacles == null) return;
        boolean exist = false;

        double xPos = sprite.getLayoutX();
        double yPos = sprite.getLayoutY();
//        System.out.println("size of Item pos: "+game.getCurrentRoom().getItemPos().size());
        if (!game.getCurrentRoom().getItemPos().isEmpty()) {
//            System.out.println(game.getCurrentRoom().getItemPos().stream().map(Arrays::toString).collect(Collectors.joining(" , ", "[ ", " ]")));
            for (int[] array : game.getCurrentRoom().getItemPos()) { //when sprite take item
                if (xPos > array[0] && xPos < array[1] && yPos > array[2] && yPos < array[3]) {
                    for (int i = 0; i < roomObstacles.getChildren().size(); i++) {
                        if (GridPane.getColumnIndex(roomObstacles.getChildren().get(i)) == 4 && GridPane.getRowIndex(roomObstacles.getChildren().get(i)) == 4) {
//                        System.out.println("atk:"+ game.getPlayer().getAtk());
//                        System.out.println("speed:"+ game.getPlayer().getSpeed());
//                        System.out.println("def:"+ game.getPlayer().getDef());

                            boolean update = false;
                            if (game.getCurrentRoom().getEntitiesOnPosition().get(i) == 8) {
//                                System.out.println("ATK BOOST");
                                game.getPlayer().take(new Sword());
                                update = true;
                            }
                            if (game.getCurrentRoom().getEntitiesOnPosition().get(i) == 9) {
//                                System.out.println("SPEED BOOST");
                                game.getPlayer().take(new Boots());
                                update = true;
                            }
                            if (game.getCurrentRoom().getEntitiesOnPosition().get(i) == 10) {
//                                System.out.println("DEF BOOST");
                                game.getPlayer().take(new Armor());
                                update = true;
                            }

                            if (update) {
                                GameController.updateStats(this.healthBar, this.currentHp, this.maxHp, this.currentDamage, this.currentDefense, this.currentSpeed);
                            }

                            roomObstacles.getChildren().get(i).setOpacity(0);
                            game.getCurrentRoom().itemTaked();
//
//                        System.out.println("ITEM TAKED");
//                        System.out.println("atk:"+ game.getPlayer().getAtk());
//                        System.out.println("speed:"+ game.getPlayer().getSpeed());
//                        System.out.println("def:"+ game.getPlayer().getDef());
                        }
                    }
                    exist = true;
                    System.out.println(game.getCurrentRoom().getItemPos().stream().map(Arrays::toString).collect(Collectors.joining(" , ", "[ ", " ]")));
                    break;
                }
            }
        }
    }

    public void closeToNPC() {
        double x = sprite.getLayoutX();
        double y = sprite.getLayoutY();
        int[] area = new int[]{1080, 350};
        int gap = 30;

        if (game.getCurrentRoom() instanceof SpawnRoom &&
                (x > area[0] - gap && x < area[0] + gap) &&
                (y > area[1] - gap && y < area[1] + gap)) {
            if (!GameController.isDialogOpen) {
                GameController.launchDialogWithNPC(dialog, dialogText, game.getCurrentRoom());
                GameController.isDialogOpen = true;
            }
        }
    }

    public void onEndingPortal() {
        double xPos = sprite.getLayoutX();
        double yPos = sprite.getLayoutY();

        for (int[] array : game.getCurrentRoom().getItemPos()) { // when sprite go above portal
            if (xPos > array[0] && xPos < array[1] && yPos > array[2] && yPos < array[3] && game.getCurrentRoom().isOpen()) {
                game.playerHasFinishedLevel(room, minimap, location, sprite, healthBar, currentHp, maxHp, currentDamage, currentDefense, currentSpeed);
                break;
            }
        }
    }

    public void generateImage(Image img, int row, int col, int height, int width) {
        ImageView myImageview = new ImageView();
        myImageview.setImage(img);
        myImageview.setFitHeight(height);
        myImageview.setFitWidth(width);
        roomObstacles.add(myImageview, col, row);
        xCoordinates.add(row);
        yCoordinates.add(col);
    }

    public void atk(int count){
        if (!game.getCurrentRoom().getItemPos().isEmpty()) {
            return;
        }
        game.getCurrentRoom().getListEnemiesClass().get(count).takeDmg(game.getPlayer().getAtk());
    }
}