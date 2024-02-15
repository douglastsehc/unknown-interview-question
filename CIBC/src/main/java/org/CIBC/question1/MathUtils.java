package org.CIBC.question1;

public class MathUtils {
    public static final float FLOAT_COMPARE_PRECISION = 0.00001f;

    public static boolean isEqual(float val1, float val2) {
        return Math.abs(val1 - val2) < FLOAT_COMPARE_PRECISION;
    }

    public static boolean isLessThan(float val1, float val2) {
        return isEqual(val1, val2) ? false : val1 < val2;
    }

    public static boolean isGreaterThan(float val1, float val2) {
        return isEqual(val1, val2) ? false : val1 > val2;
    }

}
