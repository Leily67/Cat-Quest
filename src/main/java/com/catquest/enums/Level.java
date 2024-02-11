package com.catquest.enums;

import com.catquest.entities.Cat;

import java.util.HashMap;

/**
 * The Level enum represents the level of a Cat. Each level has a label, xpRequired, and stats.
 * Pattern of increasing xpRequired is 50 * (level - 1).
 */
public enum Level {
    LEVEL_1("1",800, 0, 0, 0),
    LEVEL_2("2", 2000, 1, 0, 0),
    LEVEL_3("3", 2500, 1, 0, 0),
    LEVEL_4("4",3000, 0, 1, 0),
    LEVEL_5("5",3500, 2, 0, 10),
    LEVEL_6("6",4000, 2, 5, 5),
    LEVEL_7("7",4500, 0, 0, 5),
    LEVEL_8("8",5000, 5, 0, 5),
    LEVEL_9("9",5500, 3, 5, 5),
    LEVEL_10("10",6000, 15, 15, 15);

    private final String label;
    private final int xpRequired;
    private final HashMap<String, Integer> stats = new HashMap<>();

    Level(String label, int xpRequired, int atk, int def, int hp) {
        this.label = label;
        this.xpRequired = xpRequired;
        this.stats.put("atk", atk);
        this.stats.put("def", def);
        this.stats.put("hp", hp);
    }

    public String getLabel() {
        return label;
    }

    public int getXpRequired() {
        return xpRequired;
    }

    public static Level get(String label) {
        for (Level level : Level.values()) {
            if (level.getLabel().equals(label)) {
                return level;
            }
        }
        return null;
    }

    public void applyStats(Cat player) {
        player.setStats(player.getAtk() + this.stats.get("atk"), player.getDef() + this.stats.get("def"), player.getSpeed());
        player.setMaxHp(player.getMaxHp() + this.stats.get("hp"));
    }

    public boolean isLast() {
        return this.ordinal() == Level.values().length - 1;
    }

    public Level previous() {
        return Level.values()[this.ordinal() - 1];
    }

    public Level next() {
        if (this.isLast()) {
            return this;
        }
        return Level.values()[this.ordinal() + 1];
    }

    public int getXpToLevelUp() {
        return this.getXpRequired();
    }

    public HashMap<String, Integer> getStats() {
        return stats;
    }
}