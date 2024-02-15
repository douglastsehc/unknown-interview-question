package com.CIBC.question1;

import org.CIBC.question1.MathUtils;
import org.CIBC.question1.RandomGen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class RandomGenTests {

    private static final int numTrials = 100;
    private static final int maxNumTrials = Integer.MAX_VALUE;
    private static Map<Integer, Integer> randomNumberCounterMap;

    @BeforeAll
    public static void init() {
        randomNumberCounterMap = new HashMap<>();
    }

    @Test
    public void testRandomNumbersNullInputs() {
        float[] probabilities = {0.7f, 0.3f};
        IllegalArgumentException throwException = Assertions.assertThrows(IllegalArgumentException.class, () -> new RandomGen(null, probabilities));
        Assertions.assertTrue(throwException.getMessage().contains("randomNums and probabilities cannot be non-empty"));
    }

    @Test
    public void testProbabilitiesNullInputs() {
        int[] randomNums = {100};
        IllegalArgumentException throwException = Assertions.assertThrows(IllegalArgumentException.class, () -> new RandomGen(randomNums, null));
        Assertions.assertTrue(throwException.getMessage().contains("randomNums and probabilities cannot be non-empty"));
    }

    @Test
    public void testDifferentSizeNumsAndProbabilities() {
        int[] randomNums = {100};
        float[] probabilities = {0.7f, 0.3f};
        IllegalArgumentException throwException = Assertions.assertThrows(IllegalArgumentException.class, () -> new RandomGen(randomNums, probabilities));
        Assertions.assertTrue(throwException.getMessage().contains("The array lengths are not consistent"));
    }

    @Test
    public void testEmptyArrays() {
        int[] emptyRandomNums = {};
        float[] emptyProbabilities = {};
        IllegalArgumentException throwException = Assertions.assertThrows(IllegalArgumentException.class, () -> new RandomGen(emptyRandomNums, emptyProbabilities));
        Assertions.assertTrue(throwException.getMessage().contains("The arrays length is not legal, it must have at least 1"));
    }

    @Test
    public void testProbabilitiesWithZeroWillThrowException() {
        int[] randomNums = {1, 2};
        float[] probabilities = {0.0f, 1.0f};
        IllegalArgumentException throwException = Assertions.assertThrows(IllegalArgumentException.class, () -> new RandomGen(randomNums, probabilities));
        Assertions.assertTrue(throwException.getMessage().contains("Probability cannot be zero"));
    }

    @Test
    public void testProbabilitiesIsNegative() {
        int[] randomNums = {1, 2};
        float[] probabilities = {-0.4f, -0.9f};
        IllegalArgumentException throwException = Assertions.assertThrows(IllegalArgumentException.class, () -> new RandomGen(randomNums, probabilities));
        Assertions.assertTrue(throwException.getMessage().contains("Probability cannot be smaller than zero"));
    }


    @Test
    public void testProbArrayHasNaN() {
        int[] randomNums = {1, 2};
        float[] probabilities = {Float.NaN, 0.05f};
        IllegalArgumentException throwException = Assertions.assertThrows(IllegalArgumentException.class, () -> new RandomGen(randomNums, probabilities));
        Assertions.assertTrue(throwException.getMessage().contains("Probability cannot be NaN"));
    }

    @Test
    public void testProbArrayHasPositiveInfinite() {
        int[] randomNums = {1, 2};
        float[] probabilities = {Float.POSITIVE_INFINITY, 0.05f};
        IllegalArgumentException throwException = Assertions.assertThrows(IllegalArgumentException.class, () -> new RandomGen(randomNums, probabilities));
        Assertions.assertTrue(throwException.getMessage().contains("Probability cannot be infinite"));
    }

    @Test
    public void testProbArrayHasNegativeInfinite() {
        int[] randomNums = {1, 2};
        float[] probabilities = {Float.NEGATIVE_INFINITY, 0.05f};
        IllegalArgumentException throwException = Assertions.assertThrows(IllegalArgumentException.class, () -> new RandomGen(randomNums, probabilities));
        Assertions.assertTrue(throwException.getMessage().contains("Probability cannot be infinite"));
    }

    @Test
    public void testProbabilityValueIsMoreThan1() {
        int[] randomNums = {1, 2};
        float[] probabilities = {1.1f, 2.8f};
        IllegalArgumentException throwException = Assertions.assertThrows(IllegalArgumentException.class, () -> new RandomGen(randomNums, probabilities));
        Assertions.assertTrue(throwException.getMessage().contains("Probability cannot be greater than 1"));
    }

    @Test
    public void testProbabilitiesTotalIsNotOne() {
        int[] randomNums = {1, 2};
        float[] probabilities = {0.1f, 0.8f};
        IllegalArgumentException throwException = Assertions.assertThrows(IllegalArgumentException.class, () -> new RandomGen(randomNums, probabilities));
        Assertions.assertTrue(throwException.getMessage().contains("Cumulative total probability cannot be smaller than 1"));
    }

    @Test
    public void testHasDuplicatedRandomNumber() {
        final int[] randomNums = {0, 1, 1, 3, 4};
        final float[] probabilities = {0.01f, 0.3f, 0.58f, 0.1f, 0.01f};
        IllegalArgumentException throwException = Assertions.assertThrows(IllegalArgumentException.class, () -> new RandomGen(randomNums, probabilities));
        Assertions.assertTrue(throwException.getMessage().contains("There are duplicate random number"));

    }

    @Test
    public void testCumulativeProbabilityValueSumIsOne() {
        final int[] randomNums = {0, 1, 2, 3, 4};
        final float[] probabilities = {0.01f, 0.3f, 0.58f, 0.1f, 0.01f};
        RandomGen randomGen = new RandomGen(randomNums, probabilities);
        final float[] actualRandomGenCumulativeProbabilities = randomGen.getCumulativeProbabilities();
        final float[] expectedCumulativeProbabilities = this.getCumulativeProbability(probabilities);
        for (int i = 0; i < expectedCumulativeProbabilities.length; i++) {
            Assertions.assertTrue(MathUtils.isEqual(expectedCumulativeProbabilities[i], actualRandomGenCumulativeProbabilities[i]));
        }
    }

    @Test
    public void testCumulativeProbabilityValueSumIsLargerThanOne() {
        final int[] randomNums = {0, 1, 2, 3, 4};
        final float[] probabilities = {0.01f, 0.3f, 0.58f, 0.1f, 0.02f};
        IllegalArgumentException throwException = Assertions.assertThrows(IllegalArgumentException.class, () -> new RandomGen(randomNums, probabilities));
        Assertions.assertTrue(throwException.getMessage().contains("cumulativeProbabilities cannot be greater than 1"));
    }

    @Test
    public void testNextNum() {
        int[] randomNums = {-1, 0, 1, 2, 3};
        float[] probabilities = {0.01f, 0.3f, 0.58f, 0.1f, 0.01f};
        RandomGen randomGen = new RandomGen(randomNums, probabilities);
        this.initHashMap(randomNums);
        for (int i = 0; i < numTrials; i++) {
            this.getAndIncrement(randomGen.nextNum());
        }
        this.printCounter();
        Assertions.assertEquals(numTrials, this.getTotalCounter());
    }

    @Test
    public void testNextNumStartWithPositiveNumber() {

        int[] randomNums = {100, 5, 200, 1, 10};
        float[] probabilities = {0.01f, 0.3f, 0.58f, 0.1f, 0.01f};
        RandomGen randomGen = new RandomGen(randomNums, probabilities);
        this.initHashMap(randomNums);
        for (int i = 0; i < numTrials; i++) {
            int nextNum = randomGen.nextNum();
            this.getAndIncrement(nextNum);
        }
        this.printCounter();
        Assertions.assertEquals(numTrials, this.getTotalCounter());
    }

    @Test
    public void testNextNumWithNegativeNumber() {

        int[] randomNums = {-100, -30, -500, -7, 9};
        float[] probabilities = {0.01f, 0.3f, 0.58f, 0.1f, 0.01f};
        RandomGen randomGen = new RandomGen(randomNums, probabilities);
        this.initHashMap(randomNums);
        for (int i = 0; i < numTrials; i++) {
            int nextNum = randomGen.nextNum();
            this.getAndIncrement(nextNum);
        }
        this.printCounter();
        Assertions.assertEquals(numTrials, this.getTotalCounter());
    }

    @Test
    public void testNextNumWithIntMaxValueAmountOfTimesWithSingleThread() {
        int[] randomNums = {-1, 0, 1, 2, 3};
        float[] probabilities = {0.01f, 0.3f, 0.58f, 0.1f, 0.01f};
        RandomGen randomGen = new RandomGen(randomNums, probabilities);
        this.initHashMap(randomNums);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < maxNumTrials; i++) {
            int nextNum = randomGen.nextNum();
            this.getAndIncrement(nextNum);
        }
        long endTime = System.currentTimeMillis();
        this.printCounter();
        System.out.println(String.format("It takes %d milliseconds", endTime - startTime));
        Assertions.assertEquals(maxNumTrials, this.getTotalCounter());
    }

    private void initHashMap(int[] randomNums) {
        randomNumberCounterMap = Arrays.stream(randomNums)
                .boxed()
                .collect(Collectors.toMap(
                        key -> key,
                        value -> 0
                ));
    }

    private void getAndIncrement(int num) {
        randomNumberCounterMap.put(num, randomNumberCounterMap.get(num) + 1);
    }

    private float[] getCumulativeProbability(float[] probabilities) {
        for (int i = 1; i < probabilities.length; i++) {
            probabilities[i] = probabilities[i - 1] + probabilities[i];
        }
        return probabilities;
    }

    private void printCounter() {
        randomNumberCounterMap.forEach((num, count) -> {
            System.out.println(num + ": " + count + " times");
        });
    }

    private int getTotalCounter() {
        AtomicInteger sum = new AtomicInteger();
        randomNumberCounterMap.forEach((key, value) -> {
            sum.addAndGet(value);
        });
        return sum.get();
    }
}
