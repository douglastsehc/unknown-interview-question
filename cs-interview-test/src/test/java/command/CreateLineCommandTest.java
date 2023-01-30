package command;

import exception.InvalidCommandParamsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CreateLineCommandTest {
    @Test
    public void Should_pass_the_test_with_correct_input()  {
        new CreateLineCommand("1", "1", "1", "2");
    }

    @ParameterizedTest(name = "Should fail the test with input with  negative input {0} {1} {2} {3}, will throw exceptions")
    @CsvSource({"-1,1,1,4", "1,-1,1,4"})
    public void Should_throw_error_when_include_negative_input(String x1, String y1, String x2, String y2)  {
        Assertions.assertThrows(InvalidCommandParamsException.class, () -> {
            new CreateLineCommand(x1,y1,x2,y2);
        });

    }

    @Test
    public void Should_throw_error_when_with_missing_parameter()  {
        Assertions.assertThrows(InvalidCommandParamsException.class, () -> {
            new CreateLineCommand("1", "1");
        });
    }

    @Test
    public void Should_throw_error_when_with_one_parameter()  {
        Assertions.assertThrows(InvalidCommandParamsException.class, () -> {
            new CreateLineCommand("1");
        });

    }

    @Test
    public void Should_throw_error_when_with_no_parameter()  {
        Assertions.assertThrows(InvalidCommandParamsException.class, () -> {
            new CreateLineCommand();
        });
    }
}