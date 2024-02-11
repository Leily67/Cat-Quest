package com.catquest.helpers;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Controls {

    private static final String CONTROLS_FILE_PATH = "src/main/resources/com/catquest/controls.json";

    /**
     * A map of all the actions and their corresponding bindings
     */
    public static Map<String, String> bindings = new LinkedHashMap<>();

    private static final Map<String, String> defaultBindings = new LinkedHashMap<>();
  
    static {
        bindings.put("Move Up", "Z");
        bindings.put("Move Down", "S");
        bindings.put("Move Left", "Q");
        bindings.put("Move Right", "D");
        bindings.put("Up Attack", "UP");
        bindings.put("Down Attack", "DOWN");
        bindings.put("Left Attack", "LEFT");
        bindings.put("Right Attack", "RIGHT");
        bindings.put("Dodge", "SHIFT");
        bindings.put("Open Menu", "ESCAPE");
        defaultBindings.putAll(bindings);
    }

    /**
     * Initializes the controls by loading them from the config file
     */
    public static void initialize() {
        File file = new File(CONTROLS_FILE_PATH);
        if (!file.exists()) {
            Controls.save();
        }
        try {
            JSONObject controls = Json.parse(CONTROLS_FILE_PATH);
            for (Object key : controls.keySet()) {
                String action = (String) key;
                String binding = (String) controls.get(action);
                bindings.put(action, binding);
            }
        } catch (ParseException e) {
            System.out.println("Error parsing JSON file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update the binding for an action if the key is not already bound to another action and run the save method
     *
     * @param action The action that the key is bound to
     * @param binding The key that the action is bound to
     * @return True if the key was successfully bound to the action, false if the key is already bound to another action
     */
    public static boolean update(String action, String binding) {
        for (String key : bindings.keySet()) {
            if (bindings.get(key).equals(binding) && !key.equals(action)) {
                return false;
            }
        }
        Controls.bindings.put(action, binding);
        Controls.save();
        return true;
    }

    /**
     * Save the current controls to the config file (run after updating the controls)
     */
    private static void save() {
        HashMap<String, String> bindings = new HashMap<>();
        for (String key : Controls.bindings.keySet()) {
            bindings.put(key, Controls.bindings.get(key));
        }

        try (FileWriter writer = new FileWriter(CONTROLS_FILE_PATH)) {
            writer.write(new JSONObject(bindings).toJSONString());
        } catch (IOException e) {
            System.out.println("Error saving controls to JSON file");
        }
    }

    /**
     * Reset the controls to their default values
     */
    public static void reset() {
        Controls.bindings.clear();
        Controls.bindings.putAll(defaultBindings);
        Controls.save();
    }
}
