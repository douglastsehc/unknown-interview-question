import command.*;
import exception.InvalidCommandException;
import exception.InvalidCommandParamsException;
import model.Canvas;
import model.CanvasImpl;
import model.ObjectFactory;
import exception.InvalidEntityException;

import java.util.Scanner;

public class Main {
    private static CommandFactory commandFactory;
    private static ObjectFactory objectFactory;
    private static Canvas canvas;
    private static Scanner  scanner;

    public static void main(String [] args){
        commandFactory = new CommandFactory();
        objectFactory = new ObjectFactory();
        System.out.print("enter command: ");
        scanner = new Scanner(System.in);
        while (true) {
            process(scanner.nextLine());
            System.out.print("enter command: ");
        }
    }

    private static void process(String commandLine) {
        Command command = null;
        try {
            command = commandFactory.getCommand(commandLine);
        } catch (InvalidCommandException e) {
            System.out.println("Please enter a valid command.");
        } catch (InvalidCommandParamsException invalidCommandParams) {
            System.out.println("Command syntax is not correct: " + invalidCommandParams.getMessage());
            System.out.println("Refer to following correct syntax: \n" + invalidCommandParams.getHelpMessage());
        }

        if (command instanceof QuitCommand) {
            quit();
        }

        if (command instanceof CreateCommand) {
            createNewCanvas((CreateCommand) command);
            return;
        }

        if (command instanceof CreateEntityCommand) {
            draw((CreateEntityCommand) command);
        }
    }

    private static void draw(CreateEntityCommand command) {
        if (canvas == null) {
            System.out.println("You need to create a canvas first");
            return;
        }
        try {
            canvas.addEntity(objectFactory.getEntity(command));
            System.out.println(canvas.render());
        } catch (InvalidEntityException e) {
            System.out.println("Can not add the model to canvas: " + e.getMessage());
        }
    }

    private static void createNewCanvas(CreateCommand command) {
        canvas = new CanvasImpl(command.getWidth(), command.getHeight());
        System.out.println(canvas.render());
    }

    private static void quit() {
        scanner.close();
        System.out.println("Exiting...");
        System.exit(0);
    }
}
