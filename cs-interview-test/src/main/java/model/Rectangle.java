package model;

import common.Common;

public class Rectangle implements Object {
    private int x1;
    private int x2;
    private int y1;
    private int y2;

    public Rectangle(int x1, int y1, int x2, int y2) {
        Common.shouldAllPositive(x1, y1, x2, y2);
        if ((y1 == y2 && x1 > x2) || (x1 == x2 && y1 > y2) ) {
            this.x1 = x2;
            this.x2 = x1;
            this.y1 = y2;
            this.y2 = y1;
        } else {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    @Override
    public boolean equals(java.lang.Object rectangle) {
        if (this == rectangle) return true;
        if (rectangle == null || getClass() != rectangle.getClass()) return false;
        if (x1 != ((Rectangle) rectangle).x1) return false;
        if (x2 != ((Rectangle) rectangle).x2) return false;
        if (y1 != ((Rectangle) rectangle).y1) return false;
        return y2 == ((Rectangle) rectangle).y2;
    }

}