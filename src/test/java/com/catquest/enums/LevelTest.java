package com.catquest.enums;

import com.catquest.entities.Cat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LevelTest {
    @Test
    public void canGetNextLevel() {
        Level level = Level.LEVEL_1;
        assertEquals(Level.LEVEL_2, level.next());
        assertEquals(Level.LEVEL_2.getLabel(), level.next().getLabel());
    }

    @Test
    public void canGetCurrentLevelLabel() {
        Level level = Level.LEVEL_1;
        assertEquals("1", level.getLabel());
    }


    @Test
    public void canGetStats() {
        Level level = Level.LEVEL_1;
        assertEquals(0, level.getStats().get("atk"));
        assertEquals(0, level.getStats().get("def"));
        assertEquals(0, level.getStats().get("hp"));
    }

}
