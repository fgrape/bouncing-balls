package main;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        final BouncingBalls bb = new BouncingBalls();

        new Thread(new Runnable() {
            @Override
            public void run() {
                bb.go();
            }
        }).start();

        Random r = new Random();
        while (true) {
            int i = r.nextInt(16);
            switch (i) {
                case 1:
                    bb.setSpeed(1f);
                    break;
                case 2:
                    bb.setSpeed(5f);
                    break;
                case 3:
                    bb.setSpeed(10f);
                    break;
                case 4:
                    bb.setColor("red");
                    break;
                case 5:
                    bb.setColor("blue");
                    break;
                case 6:
                    bb.setColor("yellow");
                    break;
                case 7:
                    bb.setColor("cage");
                    break;
                case 8:
                    bb.setNumberOfBalls(1);
                    break;
                case 9:
                    bb.setNumberOfBalls(5);
                    break;
                case 10:
                    bb.setNumberOfBalls(10);
                    break;
                case 11:
                    bb.setNumberOfBalls(15);
                    break;
                case 12:
                    bb.setRadii(10, 30);
                    break;
                case 13:
                    bb.setRadii(15, 35);
                    break;
                case 14:
                    bb.setRadii(20, 40);
                    break;
                case 15:
                    bb.setRadii(20, 50);
                    break;
            }
            sleepSafe(500);
        }
    }

    private static void sleepSafe(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
