package com.tetsoft.typego.utils;

public class ScreenOrientation {

    private final Orientation orientation;

    public enum Orientation {
        PORTRAIT,
        LANDSCAPE
    }

    public ScreenOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return orientation;
    }

}
