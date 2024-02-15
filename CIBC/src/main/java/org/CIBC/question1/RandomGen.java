package org.CIBC.question1;


import org.apache.commons.lang.math.RandomUtils;

import java.util.*;

public class RandomGen {
    final private int[] randomNums;
    final private float[] probabilities;
    private float[] cumulativeProbabilities;

    public RandomGen(final int[] randomNums, final float[] probabilities) {
        this.randomNums = randomNums;
        this.probabilities = probabilities;
        init();
    }

    private void init() {
        if (randomNums == null || probabilities == null) {
            throw new IllegalArgumentException("randomNums and probabilities cannot be non-empty");
        }
        cumulativeProbabilities = new float[probabilities.length];
        if (randomNums.length != probabilities.length) {
            throw new IllegalArgumentException("The array lengths are not consistent");
        }
        if (randomNums.length == 0 && probabilities.length == 0) {
            throw new IllegalArgumentException("The arrays length is not legal, it must have at least 1");
        }
        HashMap<Integer, Boolean> checkIsThereAnyRepeatedElementMap = new HashMap<>();
        for (int i = 0; i < probabilities.length; i++) {
            if (Float.isNaN(probabilities[i])) {
                throw new IllegalArgumentException("Probability cannot be NaN");
            }
            if (Float.isInfinite(probabilities[i])) {
                throw new IllegalArgumentException("Probability cannot be infinite");
            }
            if (MathUtils.isEqual(probabilities[i], 0.0f)) {
                throw new IllegalArgumentException("Probability cannot be zero");
            }
            if (MathUtils.isLessThan(probabilities[i], 0.0f)) {
                throw new IllegalArgumentException("Probability cannot be smaller than zero");
            }
            if (MathUtils.isGreaterThan(probabilities[i], 1.0f)) {
                throw new IllegalArgumentException("Probability cannot be greater than 1");
            }

            if (i == 0) {
                cumulativeProbabilities[i] = probabilities[i];
            } else {
                cumulativeProbabilities[i] = probabilities[i] + cumulativeProbabilities[i - 1];
            }
            if (MathUtils.isGreaterThan(cumulativeProbabilities[i], 1.0f)) {
                throw new IllegalArgumentException("cumulativeProbabilities cannot be greater than 1");
            }
            if (checkIsThereAnyRepeatedElementMap.containsKey(randomNums[i])) {
                throw new IllegalArgumentException("There are duplicate random number");
            } else {
                checkIsThereAnyRepeatedElementMap.put(randomNums[i], true);
            }
        }
        if (MathUtils.isLessThan(cumulativeProbabilities[probabilities.length - 1], 1.0f)) {
            throw new IllegalArgumentException("Cumulative total probability cannot be smaller than 1");
        }

    }

    /**
     * Returns one of the randomNums. When this method is called
     * multiple times over a long period, it should return the
     * numbers roughly with the initialized probabilities.
     */
    public int nextNum() {
        float targetProbability = RandomUtils.nextFloat();
        int low = 0;
        int high = randomNums.length - 1;

        while (low < high) {
            int mid = (low + high) >>> 1;
            if (targetProbability <= cumulativeProbabilities[mid]) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return randomNums[low];
    }


    public float[] getCumulativeProbabilities() {
        return cumulativeProbabilities;
    }

}