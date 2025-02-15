package ding.co.backendportfolio.chapter5.util;

import java.util.concurrent.ThreadLocalRandom;

public class FilterUtil {

    /**
     * (1) 비용: 1, 선택도: 0.5
     * => Score: 2
     */
    public static boolean filterScore2() {
        simulateCost(10);
        return ThreadLocalRandom.current().nextDouble() < 0.5;
    }

    /**
     * (2) 비용: 1, 선택도: 0.75
     * => Score: 4
     */
    public static boolean filterScore4() {
        simulateCost(10);
        return ThreadLocalRandom.current().nextDouble() < 0.75;
    }

    /**
     * (3) 비용: 10, 선택도: 0.5
     * => Score: 20
     */
    public static boolean filterScore20() {
        simulateCost(100);
        return ThreadLocalRandom.current().nextDouble() < 0.5;
    }

    /**
     * (4) 비용: 10, 선택도: 0.75
     * => Score: 40
     */
    public static boolean filterScore40() {
        simulateCost(100);
        return ThreadLocalRandom.current().nextDouble() < 0.75;
    }

    /**
     * 바쁜 루프 방식으로 'nanos'만큼 CPU 시간을 소모
     */
    private static void simulateCost(long nanos) {
        long start = System.nanoTime();
        while (System.nanoTime() - start < nanos) {
            // busy-wait
        }
    }
}
