package com.CIBC.question2;

import org.CIBC.question2.KingAndKnight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class KingAndKnightTest {
    KingAndKnight kingAndKnight = new KingAndKnight();

    @ParameterizedTest
    @CsvSource({"10,30", "6,14", "7,18", "11,35", "15,55", "16,61", "100,945",
            "10000,942820", "1000,29820", "21,91", "22,98", "0,0"})
    public void testCalculationWithDifferentExample(int days, long coins) {
        long res = kingAndKnight.calculation(days);
        Assertions.assertEquals(coins, res);
    }


    @Test
    public void testCalculationWithNegativeDays() {
        IllegalArgumentException throwException = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            kingAndKnight.calculation(-1);
        });
        Assertions.assertEquals("days cannot smaller than 0", throwException.getMessage());
    }

    @Test
    public void testCalculationWithMaxIntegerDays() {
        long startTime = System.nanoTime();
        Assertions.assertEquals(11383094935552L, kingAndKnight.calculation(Integer.MAX_VALUE));
        long stopTime = System.nanoTime();
        System.out.println(String.format("For executing simple calculation, it takes %d nanoseconds", stopTime - startTime));

    }
}
