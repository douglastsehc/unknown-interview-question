package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AreaTest {
    @Test
    public void should_pass_the_test_with_correct_area_input()  {
        new Area(1, 2, 'o');
    }

    @ParameterizedTest(name = "Should fail the test with input with  negative input {0} {1} {2}, will throw exceptions")
    @CsvSource({"-1,1,o", "1,-1,o"})
    public void Should_throw_error_when_input_with_negative_integer(int firstInput, int secondInput, char thirdInput)  {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Area(firstInput, secondInput, thirdInput);
        });

    }

}