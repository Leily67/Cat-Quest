package com.catquest.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.catquest.helpers.Controls;
import org.junit.jupiter.api.Test;

public class ControlsTest {

    private static final String key = Controls.bindings.keySet().iterator().next();

    @Test
    public void cantPutExistingKey() {
        Controls.reset();
        String value = Controls.bindings.get(key);
        Controls.bindings.put(Controls.bindings.keySet().toArray()[1].toString(), value);
        assertEquals(value, Controls.bindings.get(key));
    }

    @Test
    public void canPutNewValidKey() {
        Controls.reset();
        Controls.update(key, "U");
        assertEquals("U", Controls.bindings.get(key));
        Controls.initialize();
        assertEquals("U", Controls.bindings.get(key));
    }

    @Test
    public void newKeyIsSavedInJsonFile() {
        Controls.reset();
        Controls.update(key, "O");
        Controls.initialize();
        assertEquals("O", Controls.bindings.get(key));
    }
}
