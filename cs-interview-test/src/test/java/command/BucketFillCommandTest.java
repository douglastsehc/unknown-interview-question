package command;

import exception.InvalidCommandParamsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class BucketFillCommandTest {
    @Test
    public void Should_pass_the_test_with_3_input_first_two_is_postive_integer_and_last_is_a_char() {
        new BucketFillCommand( "1", "1", "o");
    }

    @ParameterizedTest(name = "Should fail the test with different input with negative integer either at first or second parameter {0} integer and {1}, will throw exceptions")
    @CsvSource({"-1,1,o", "1,-1,o"})
    public void Should_throw_error_when_include_negative_input(String firstInput, String secondInput, String thirdinput) {
        Assertions.assertThrows(InvalidCommandParamsException.class, () -> {
            new BucketFillCommand( firstInput, secondInput, thirdinput);
        });

    }

    @Test
    public void Should_throw_error_when_with_not_enough_parameter(){
        Assertions.assertThrows(InvalidCommandParamsException.class, () -> {
            new BucketFillCommand( "1", "1");
        });
    }

    @Test
    public void Should_throw_error_when_with_one_parameter() {
        Assertions.assertThrows(InvalidCommandParamsException.class, () -> {
            new BucketFillCommand( "1");
        });
    }

    @Test
    public void Should_throw_error_when_with_no_parameter() {
        Assertions.assertThrows(InvalidCommandParamsException.class, () -> {
            new BucketFillCommand( );
        });
    }

}