package com.catquest.helpers;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class View {

    private final static double MAX_WIDTH = 1920;
    private final static double MAX_HEIGHT = 1080;

    public static double currentScreenHeight = 0;

    public static double currentScreenWidth = 0;

    private static final Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

    public static void initialize() {
        currentScreenHeight = View.bounds.getHeight();
        currentScreenWidth = View.bounds.getWidth();
    }

    /**
     * This method returns the width of the current screen, or the default width if the screen is too small.
     *
     * @return the width of the current screen, or the default width if the screen is too small
     */
    public static double getWidthForCurrentScreen() {
        return Math.min(currentScreenWidth, MAX_WIDTH);
    }

    /**
     * This method returns the height of the current screen, or the default height if the screen is too small.
     *
     * @return the height of the current screen, or the default height if the screen is too small
     */
    public static double getHeightForCurrentScreen() {
       return Math.min(currentScreenHeight, MAX_HEIGHT) - 30;
    }
}
