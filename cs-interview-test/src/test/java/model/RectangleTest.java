package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class RectangleTest {
    @ParameterizedTest(name = "Should pass the test with positive input {0} {1} {2} {3}")
    @CsvSource({"1,2,5,3", "1,2,3,4"})
    public void Should_pass_the_test_with_positive_integer(int x1, int y1, int x2, int y2)  {
        new Rectangle(x1, y1, x2, y2);
    }

    @Test
    public void Should_pass_the_test_with_when_create_two_equal_rectangle()  {
        Rectangle line1 = new Rectangle(1, 2, 1, 3);
        Rectangle line2 = new Rectangle(1, 2, 1, 3);
        Assertions.assertEquals(line1, line2);
    }

    @ParameterizedTest(name = "Should throw error when the test include negative input {0} {1} {2} {3}, will throw exceptions")
    @CsvSource({"-1,2,1,2", "1,-2,1,2", "1,2,-1,2","1,2,1,-2"})
    public void Should_throw_error_when_the_rectangle_is_with_negative_input( int x1, int y1, int x2, int y2)  {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Rectangle(x1, y1, x2, y2);
        });

    }
}