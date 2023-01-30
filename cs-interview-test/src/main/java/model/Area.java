package model;

import common.Common;

public class Area implements Object {
    private int  x;
    private int  y;
    private char character;

    public Area(int x1, int y1, char character) {
        Common.shouldAllPositive(x1, y1);
        this.x = x1;
        this.y = y1;
        this.character = character;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getCharacter() {
        return character;
    }

    @Override
    public boolean equals(java.lang.Object area) {
        if (this == area) return true;
        if (area == null || getClass() != area.getClass()) return false;
        if (x != ((Area) area).x) return false;
        if (y != ((Area) area).y) return false;
        return character == ((Area) area).character;
    }

}