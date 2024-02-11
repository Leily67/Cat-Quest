package com.catquest.game.rooms;

import com.catquest.game.Room;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.spreadsheet.Grid;

public class SpawnRoom extends Room {
    public SpawnRoom(int row, int column) {
        super(row, column);
        this.setType(Room.Type.SPAWN);
        xRoomPosition.add(7);
        yRoomPosition.add(0);
    }


}
