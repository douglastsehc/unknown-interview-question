package org.CIBC.question2;

public class KingAndKnight {
    public long calculation(int days) {
        if (days < 0) {
            throw new IllegalArgumentException("days cannot smaller than 0");
        }
        if (days == 0) {
            return 0;
        }
        long coins = 0;
        int daysCounter;
        int coinPerDay;
        int currentDay = days;

        for (coinPerDay = 1, daysCounter = 1; daysCounter <= days; daysCounter += coinPerDay, coinPerDay++) {
            if (currentDay >= coinPerDay) {
                currentDay -= coinPerDay;
            } else {
                break;
            }
            coins += coinPerDay * coinPerDay;
        }
        coins += currentDay * coinPerDay;
        return coins;
    }
}
