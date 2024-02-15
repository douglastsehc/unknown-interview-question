package com.CIBC.question1;

import org.CIBC.question1.MathUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MathUtillsTests {

    @Test
    public void testIsEqual() {
        Assertions.assertTrue(MathUtils.isEqual(1.0f, 1.0f));
        Assertions.assertTrue(MathUtils.isEqual(0.0f, 0.0f));
        Assertions.assertTrue(MathUtils.isEqual(-1.0f, -1.0f));

        Assertions.assertFalse(MathUtils.isEqual(1.0f, 1.00002f));
        Assertions.assertFalse(MathUtils.isEqual(0.0f, 0.00002f));
        Assertions.assertFalse(MathUtils.isEqual(-1.0f, -1.00002f));
    }

    @Test
    public void testIsLessThan() {
        Assertions.assertTrue(MathUtils.isLessThan(0.0f, 1.0f));
        Assertions.assertTrue(MathUtils.isLessThan(-1.0f, 0.0f));

        Assertions.assertFalse(MathUtils.isLessThan(1.0f, 1.0f));
        Assertions.assertFalse(MathUtils.isLessThan(1.0f, 0.0f));
        Assertions.assertFalse(MathUtils.isLessThan(0.0f, -1.0f));
    }

    @Test
    public void testIsGreaterThan() {
        Assertions.assertTrue(MathUtils.isGreaterThan(1.0f, 0.0f));
        Assertions.assertTrue(MathUtils.isGreaterThan(0.0f, -1.0f));

        Assertions.assertFalse(MathUtils.isGreaterThan(1.0f, 1.0f));
        Assertions.assertFalse(MathUtils.isGreaterThan(0.0f, 1.0f));
        Assertions.assertFalse(MathUtils.isGreaterThan(-1.0f, 0.0f));
    }
}
