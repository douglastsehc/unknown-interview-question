package command;


import common.Common;
import exception.InvalidCommandParamsException;

public class BucketFillCommand implements CreateEntityCommand {
    private static final String helperMessage = "B x y c should fill the area connected to (x,y)";
    private int  x;
    private int  y;
    private char character;

    public BucketFillCommand(String... params) {
        if (params.length < 3)
            throw new InvalidCommandParamsException("Area fill with exact 3 params", helperMessage);
        if (params[2].length() != 1)
            throw new InvalidCommandParamsException("Color character should only be 1 character", helperMessage);
        try {
            x = Common.toPositiveInt(params[0]);
            y = Common.toPositiveInt(params[1]);
            character = params[2].charAt(0);
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandParamsException("x or y should bigger than 0", helperMessage);
        }
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

}