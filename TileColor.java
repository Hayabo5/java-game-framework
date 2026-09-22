package model;

import java.awt.Color;

public enum TileColor {
    DARK_PINK(new Color(200, 0, 100)),
    LIGHT_PINK(new Color(255, 182, 193)),
    PURPLE(new Color(160, 90, 200)),
    DARK_GRAY(new Color(100, 100, 100)),
    LIGHT_GRAY(new Color(200, 200, 200)),
    MINT(new Color(170, 255, 200));

    private final Color awtColor;

    TileColor(Color awtColor) {
        this.awtColor = awtColor;
    }

    public Color getAwtColor() {
        return awtColor;
    }
}
