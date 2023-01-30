package model;

import exception.InvalidEntityException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CanvasImpl implements Canvas {
    private static final char LINE_CHAR = 'x';
    private static final char HORIZONTAL_EDGE_CHAR = '-';
    private static final char VERTICAL_EDGE_CHAR = '|';

    private final char[][] cachedCanvas;
    private final int width;
    private final int height;

    private LinkedList<Object> entities;
    private final String             horizontalEdge;

    public CanvasImpl(int w, int h) {
        entities = new LinkedList<>();
        width = w;
        height = h;
        cachedCanvas = new char[height][width];
        Arrays.stream(cachedCanvas).forEach(chars -> Arrays.fill(chars, ' '));
        horizontalEdge = Stream.generate(() -> String.valueOf(HORIZONTAL_EDGE_CHAR)).limit(width + 2).collect(Collectors.joining());
    }

    @Override
    public void addEntity(Object object) throws InvalidEntityException {
        entities.add(object);
        if (object instanceof Line) {
            addLine((Line) object);
        } else if (object instanceof Rectangle) {
            addRectangle((Rectangle) object);
        } else if (object instanceof Area) {
            addAreaFill((Area) object);
        }
    }

    @Override
    public String render() {
        StringBuilder builder = new StringBuilder();
        builder.append(horizontalEdge).append("\n");
        for (int i = 0; i < height; i++) {
            builder.append(VERTICAL_EDGE_CHAR);
            for (int j = 0; j < width; j++) {
                builder.append(cachedCanvas[i][j]);
            }
            builder.append(VERTICAL_EDGE_CHAR);
            builder.append("\n");
        }
        builder.append(horizontalEdge);
        return builder.toString();
    }

    private void addAreaFill(Area area) {
        if (isOutside(area.getX(), area.getY())) {
            throw new InvalidEntityException("Area fill point is outside of canvas");
        }
        fillArea(area.getX(), area.getY(), area.getCharacter());
    }

    private void addRectangle(Rectangle rec) {
        if (isOutside(rec.getX1(), rec.getY1())) {
            throw new InvalidEntityException("Rectangle is outside of canvas");
        }
        drawRectangle(rec.getX1(), rec.getY1(), rec.getX2(), rec.getY2());
    }

    private void addLine(Line line) {
        if (isOutside(line.getX1(), line.getY1())) {
            throw new InvalidEntityException("Line is outside of canvas");
        }
        if (line.getX2() >= width) {
            line.setX2(width);
        }
        if (line.getY2() >= height) {
            line.setY2(height);
        }
        drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }

    private void drawLine(int x1, int y1, int x2, int y2) {
        for (int row = y1 - 1; row <= y2 - 1 && row < height; row++) {
            for (int col = x1 - 1; col <= x2 - 1 && col < width; col++) {
                cachedCanvas[row][col] = CanvasImpl.LINE_CHAR;
            }
        }
    }

    private void fillArea(int x, int y, char mchar) {
        char originalChar = cachedCanvas[y - 1][x - 1];
        Stack<Point> stack = new Stack<>();
        stack.add(new Point(y - 1, x - 1));

        while (!stack.isEmpty()) {
            Point pop = stack.pop();
            if (cachedCanvas[pop.getX()][pop.getY()] == originalChar) {
                cachedCanvas[pop.getX()][pop.getY()] = mchar;
            }
            if (pop.getX() + 1 < height && cachedCanvas[pop.getX() + 1][pop.getY()] == originalChar) {
                stack.add(new Point(pop.getX() + 1, pop.getY()));
            }
            if (pop.getX() - 1 >= 0 && cachedCanvas[pop.getX() - 1][pop.getY()] == originalChar) {
                stack.add(new Point(pop.getX() - 1, pop.getY()));
            }
            if (pop.getY() + 1 < width && cachedCanvas[pop.getX()][pop.getY() + 1] == originalChar) {
                stack.add(new Point(pop.getX(), pop.getY() + 1));
            }
            if (pop.getY() - 1 >= 0 && cachedCanvas[pop.getX()][pop.getY() - 1] == originalChar) {
                stack.add(new Point(pop.getX(), pop.getY() - 1));
            }
        }
    }

    private void drawRectangle(int x1, int y1, int x2, int y2) {
        //top
        drawLine(x1, y1, x2, y1);

        //bottom
        drawLine(x2, y1, x2, y2);

        //right
        drawLine(x1, y1, x1, y2);

        //left
        drawLine(x1, y2, x2, y2);
    }

    private boolean isOutside(int x, int y) {
        return x < 1  || y < 1 || x >= width || y >= height;
    }
}
