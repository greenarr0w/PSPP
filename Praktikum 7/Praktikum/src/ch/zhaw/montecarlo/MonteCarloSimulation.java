package ch.zhaw.montecarlo;

import ch.zhaw.random.HighQualityRandom;

public class MonteCarloSimulation {


    public static void main(String[] args) {
        HighQualityRandom randomGenerator = new HighQualityRandom(20000);
        int amount = 200000000;
        double x, y, distance;
        int count = 0;
        for (int i = 0; i < amount; i++) {
            x = randomGenerator.nextDouble();
            y = randomGenerator.nextDouble();
            distance = Math.sqrt((x * x) + (y * y));
            count = (distance < 1) ? (count + 1) : count;
        }
        double result = ((double) count) / amount * 4.0;
        System.out.println("PI result: " + result);
    }
}
