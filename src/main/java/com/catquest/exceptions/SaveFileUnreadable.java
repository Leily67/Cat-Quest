package com.catquest.exceptions;

public class SaveFileUnreadable extends Exception {
    public SaveFileUnreadable() {
        super("The game save file is unreadable.");
    }
}
