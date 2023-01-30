package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PointTest {
    @Test
    public void Should_pass_the_test_with_positive_integer_input()  {
        new Point(1, 2);
    }

    @ParameterizedTest(name = "Should throw error when the test include negative input {0} {1} {2}, will throw exceptions")
    @CsvSource({"-1,2", "1,-2"})
    public void Should_throw_error_when_include_negative_input(int x1, int y1)  {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Point(x1, y1);
        });
    }
}