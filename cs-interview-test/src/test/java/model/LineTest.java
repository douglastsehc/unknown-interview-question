package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineTest {
    @Test
    public void Should_pass_the_test_with_positive_line_input()  {
        new Line(1, 2, 1, 3);
    }

    @Test
    public void Should_pass_the_test_with_two_line_create_and_they_are_equal()  {
        Line line1 = new Line(1, 2, 1, 5);
        Line line2 = new Line(1, 2, 1, 5);
        assertEquals(line1, line2);
    }

    @ParameterizedTest(name = "Should throw error when the test include negative input {0} {1} {2} {3}, will throw exceptions")
    @CsvSource({"-1,2,1,2", "1,-2,1,2", "1,2,-1,2","1,2,1,-2"})
    public void Should_throw_error_when_the_test_include_negative_input( int x1, int y1, int x2, int y2)  {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Line(x1, y1, x2, y2);
        });
    }

    @Test
    public void Should_throw_error_when_the_line_is_not_equal()  {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Line(1, 2, 3, 4);
        });
    }

}