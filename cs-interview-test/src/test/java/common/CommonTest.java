package common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommonTest {

    @Test
    public void Should_pass_the_test_when_it_is_positive_string_integer_input() {
        assertEquals(Common.toPositiveInt("111"), 111);
    }

    @Test
    public void Should_throw_error_when_input_is_0() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Common.toPositiveInt("0");
        });
    }

    @Test
    public void Should_throw_error_when_input_is_a_char() {
        Assertions.assertThrows(NumberFormatException.class, () -> {
            Common.toPositiveInt("a");
        });
    }

    @Test
    public void Should_pass_the_test_with_correct_input() {
        Common.shouldAllPositive(1, 2, 3, 4);
    }

    @ParameterizedTest(name = "Should fail the test include input with  negative input or zero {0} {1} {2} {3}, will throw exceptions")
    @CsvSource({"1,-2,3,4", "1,2,0,4"})
    public void Should_throw_error_when_input_is_negative_or_zero(int firstInput, int secondInput, int thirdInput, int fourthInput) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Common.shouldAllPositive(firstInput, secondInput, thirdInput, fourthInput);
        });
    }


    @Test
    public void Should_pass_the_test_with_all_non_negative_input(){
        Common.shouldAllNonNegative(1, 2, 3, 4);
    }

    @Test
    public void Should_pass_the_test_with_all_non_negative_input_with_0() {
        Common.shouldAllNonNegative(1, 2, 0, 4);
    }
}
