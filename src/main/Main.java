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
			int i = r.nextInt(4);
			if (i == 0) {
				bb.setColor("red");
			} else if (i == 1) {
				bb.setColor("yellow");
			} else if (i == 2) {
				bb.setColor("blue");
			} else if (i == 3) {
				bb.setColor("cage");
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
