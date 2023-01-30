package command;

import exception.InvalidCommandException;
import exception.InvalidCommandParamsException;

import java.util.Arrays;

public class CommandFactory {
    public Command getCommand(String commandLine) throws InvalidCommandException, InvalidCommandParamsException {
        String[] split = commandLine.trim().split(" ");
        String mainCommand = split[0].toUpperCase();
        String[] params = Arrays.copyOfRange(split, 1, split.length);
        switch (mainCommand) {
            case "C":
                return new CreateCommand(params);
            case "L":
                return new CreateLineCommand(params);
            case "R":
                return new CreateRectangleCommand(params);
            case "B":
                return new BucketFillCommand(params);
            case "Q":
                return new QuitCommand();
            default:
                throw new InvalidCommandException();
        }
    }
}