package model;

import common.Common;

public class Point {
    private int x;
    private int y;

    public Point(int x1, int y1) {
        Common.shouldAllNonNegative(x1, y1);
        this.x = x1;
        this.y = y1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}