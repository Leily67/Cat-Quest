package com.catquest.game.rooms;

import com.catquest.game.Room;

public class EndRoom extends Room {
    public EndRoom(int row, int column) {
        super(row, column);
        this.setType(Room.Type.END);
    }

}
