package model;

import common.Common;

public class Line implements Object {

    private int x1;
    private int x2;
    private int y1;
    private int y2;

    public Line(int x1, int y1, int x2, int y2) {
        if (x1 != x2 && y1 != y2) {
            throw new IllegalArgumentException("Currently does not support diagonal line");
        }
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

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    @Override
    public boolean equals(java.lang.Object line) {
        if (this == line) return true;
        if (line == null || getClass() != line.getClass()) return false;
        if (x1 != ((Line) line).x1) return false;
        if (x2 != ((Line) line).x2) return false;
        if (y1 != ((Line) line).y1) return false;
        return y2 == ((Line) line).y2;
    }
}