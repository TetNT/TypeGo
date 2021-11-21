package com.tetsoft.typego.utils;

public enum ScreenOrientation {
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
