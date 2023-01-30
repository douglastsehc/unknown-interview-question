package command;

import exception.InvalidCommandParamsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CreateCanvasCommandTest {
    @Test
    public void Should_pass_with_positive_canvas_command()  {
        new CreateCommand("1", "1");
    }

    @ParameterizedTest(name = "Should fail with negative input at either first input {0} or  second input {1}")
    @CsvSource({"-1,1", "1,-1"})
    public void Should_throw_error_when_with_negative_input(String weight, String height)  {
        Assertions.assertThrows(InvalidCommandParamsException.class, () -> {
            new CreateCommand(weight, height);
        });
    }


    @Test
    public void Should_throw_error_when_with_just_one_parameter()  {
        Assertions.assertThrows(InvalidCommandParamsException.class, () -> {
            new CreateCommand("1");
        });
    }

    @Test
    public void Should_throw_error_when_with_no_input_parameter()  {
        Assertions.assertThrows(InvalidCommandParamsException.class, () -> {
            new CreateCommand();
        });
    }
}