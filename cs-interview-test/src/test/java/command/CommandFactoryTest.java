package command;

import exception.InvalidCommandException;
import exception.InvalidCommandParamsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandFactoryTest {

    private CommandFactory commandFactory;

    @BeforeEach
    public void setUp()  {
        commandFactory = new CommandFactory();
    }

    @Test
    public void Should_pass_with_Q_command()  {
        commandFactory.getCommand("Q");
    }

    @Test
    public void Should_throw_error_when_wrong_string_command()  {
        Assertions.assertThrows(InvalidCommandException.class, () -> {
            commandFactory.getCommand("A");
        });
    }

    @Test
    public void Should_pass_with_correct_C_input_positive_integer()  {
        Command command = commandFactory.getCommand("C 20 4");
        Assertions.assertEquals(command.getClass(), CreateCommand.class);
    }

    @Test
    public void Should_throw_error_when_C_command_with_negative_integer()  {
        Assertions.assertThrows(InvalidCommandParamsException.class, () -> {
            commandFactory.getCommand("C 20 -4");
        });
    }

    @Test
    public void Should_pass_with_L_command_with_positive_integer()  {
        Command command = commandFactory.getCommand("L 1 2 1 22");
        Assertions.assertEquals(command.getClass(), CreateLineCommand.class);
    }

    @Test
    public void Should_throw_error_when_L_command_with_negative_integer()  {
        Assertions.assertThrows(InvalidCommandParamsException.class, () -> {
            commandFactory.getCommand("L 1 2 1 -22");
        });

    }

    @Test
    public void Should_pass_with_R_command_with_positive_integer()  {
        Command command = commandFactory.getCommand("R 14 1 18 3");
        Assertions.assertEquals(command.getClass(), CreateRectangleCommand.class);
    }

    @Test
    public void Should_throw_error_when_R_command_with_negative_integer()  {
        Assertions.assertThrows(InvalidCommandParamsException.class, () -> {
            commandFactory.getCommand("R 14 1 18 -3");
        });
    }

    @Test
    public void Should_pass_with_B_command_with_correct_input()  {
        Command command = commandFactory.getCommand("B 1 3 o");
        Assertions.assertEquals(command.getClass(), BucketFillCommand.class);
    }

    @Test
    public void Should_throw_error_when_B_command_with_wrong_string_input()  {
        Assertions.assertThrows(InvalidCommandParamsException.class, () -> {
            commandFactory.getCommand("B 1 3 oo");
        });
    }


}