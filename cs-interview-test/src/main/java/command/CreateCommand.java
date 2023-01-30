package command;


import common.Common;
import exception.InvalidCommandParamsException;

public class CreateCommand implements Command {

    private static String helperMessage = "C w h should create a canvas of width w and height h. w, h should be bigger than 0";
    private int height;
    private int width;

    public CreateCommand(String... params) {
        if (params.length < 2)
            throw new InvalidCommandParamsException("Create command expects to have 2 params", helperMessage);
        try {
            width = Common.toPositiveInt(params[0]);
            height = Common.toPositiveInt(params[1]);
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandParamsException("Number must bigger or equal to 0", helperMessage);
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}