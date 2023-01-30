package command;

import common.Common;
import exception.InvalidCommandParamsException;

public class CreateLineCommand implements CreateEntityCommand {
    private static final String helperMessage = "L x1 y1 x2 y2 should create a line from (x1,y1) to (x2,y2) and will be drawn using the 'x' character.";
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public CreateLineCommand(String... params) {
        if (params.length < 4)
            throw new InvalidCommandParamsException("Create line command expects 4 params", helperMessage);
        try {

            x1 = Common.toPositiveInt(params[0]);
            y1 = Common.toPositiveInt(params[1]);
            x2 = Common.toPositiveInt(params[2]);
            y2 = Common.toPositiveInt(params[3]);

        } catch (IllegalArgumentException e) {
            throw new InvalidCommandParamsException("Number should be bigger than 0", helperMessage);
        }
        if (x1 != x2 && y1 != y2) {
            throw new InvalidCommandParamsException("Currently does not support diagonal line", helperMessage);
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