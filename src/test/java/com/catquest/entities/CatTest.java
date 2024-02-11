package com.catquest.entities;

import com.catquest.entities.items.Armor;
import com.catquest.entities.items.Boots;
import com.catquest.entities.items.Sword;
import com.catquest.enums.Level;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CatTest {
    @Test
    public void catHasName() {
        Cat cat = new Cat("Player");
        assertEquals("Player", cat.getName());
    }

    @Test
    public void catHasDefaultLevel() {
        Cat cat = new Cat("Player");
        assertEquals(Level.LEVEL_1, cat.getLevel());
        assertEquals(Level.LEVEL_1.getLabel(), cat.getLevelLabel());
        assertEquals(Level.LEVEL_1.getXpRequired(), cat.getXp());
    }

    @Test
    public void catHasDefaultStats() {
        Cat cat = new Cat("Player");
        assertEquals(5, cat.getAtk());
        assertEquals(0, cat.getDef());
        assertEquals(100, cat.getHp());
        assertEquals(1, cat.getSpeed());
    }

    @Test
    public void canTakeItems() {
        Cat cat = new Cat("Player");
        cat.take(new Armor());
        cat.take(new Boots());
        cat.take(new Sword());
        assertEquals(3, cat.getItems().size());
        for (Class<? extends Item> itemClass : cat.getItems().keySet()) {
            assertEquals(1, cat.getItems().get(itemClass));
        }
    }
}
