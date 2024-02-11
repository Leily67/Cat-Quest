package com.catquest.settings;

import com.catquest.helpers.Audio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AudioTest {
    @BeforeAll
    public static void setUp() {
        Audio.EXECUTED_FROM_TESTS = true;
    }

    @Test
    public void retrieveMusicVolumeFromJSON() {
        Audio.reset();
        Audio.musicVolume = 90;
        Audio.save();
        assertEquals(90, Audio.musicVolume);
    }

    @Test
    public void changeMusicVolume() {
        Audio.reset();
        Audio.musicVolume += 5;
        assertEquals(55, Audio.musicVolume);
        Audio.save();
        assertEquals(55, Audio.musicVolume);
    }

    @Test
    public void changeEffectsVolume() {
        Audio.reset();
        Audio.effectsVolume -= 5;
        assertEquals(45, Audio.effectsVolume);
        Audio.save();
        assertEquals(45, Audio.effectsVolume);
    }
}
