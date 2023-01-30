package command;


import common.Common;
import exception.InvalidCommandParamsException;

public class CreateRectangleCommand implements CreateEntityCommand {
    private static final String helpMessage = "R x1 y1 x2 y2 Should create a new rectangle, whose upper left corner is (x1,y1) and lower right corner is (x2,y2)";
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public CreateRectangleCommand(String... params) {
        if (params.length < 4)
            throw new InvalidCommandParamsException("Draw Rectangle command expects 4 params", helpMessage);
        try {
            x1 = Common.toPositiveInt(params[0]);
            y1 = Common.toPositiveInt(params[1]);
            x2 = Common.toPositiveInt(params[2]);
            y2 = Common.toPositiveInt(params[3]);
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandParamsException("Number can not be negative", helpMessage);
        }
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

}
