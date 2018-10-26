package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import graphics.Ball;
import graphics.Display;

public class BouncingBalls {

    private final int MAX_NUMBER_OF_BALLS = 20;

    private Random random = new Random();

    private int numberOfBalls = 10;
    private Color backgroundColor = Color.black;
    private float speed = 5f;
    private int minRadius = 10;
    private int maxRadius = 50;
    private Color color = Color.red;
    private BufferedImage cage;

    private Display display;

    private ArrayList<Ball> balls = new ArrayList<>();

    public BouncingBalls() {
        try {
            cage = ImageIO.read(new File("cage.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void go() {
        display = new Display(800, 600);
        createBalls();
        mainLoop();
        display.destroy();
    }

    public void setNumberOfBalls(int numberOfBalls) {
        if (numberOfBalls < 1 || numberOfBalls > MAX_NUMBER_OF_BALLS) {
            throw new IllegalArgumentException("Value must be in [1, " + MAX_NUMBER_OF_BALLS + "]: " + numberOfBalls);
        }
        this.numberOfBalls = numberOfBalls;
    }

    public void setSpeed(float speed) {
        if (speed < 0 || speed > 15) {
            throw new IllegalArgumentException("Value must be in [0, 10]: " + speed);
        }
        this.speed = speed;
    }

    public void setRadii(int minRadius, int maxRadius) {
        if (minRadius < 10 || minRadius > 20) {
            throw new IllegalArgumentException("minRadius must be in [10, 20]: " + minRadius);
        }
        if (maxRadius < 30 || maxRadius > 50) {
            throw new IllegalArgumentException("maxRadius must be in [30, 50]: " + minRadius);
        }
        this.maxRadius = maxRadius;
        this.minRadius = minRadius;
        updateRadii();
    }

    private void updateRadii() {
        for (Ball ball : balls) {
            ball.radius = random.nextInt(maxRadius - minRadius) + minRadius;
        }
    }

    public void setColor(String color) {
        switch (color.toLowerCase()) {
            case "red":
                this.color = Color.red;
                break;
            case "blue":
                this.color = Color.blue;
                break;
            case "yellow":
                this.color = Color.yellow;
                break;
            case "cage":
                this.color = null;
                break;
            default:
                throw new IllegalArgumentException("Invalid color string: " + color);
        }

    }

    private void mainLoop() {
        while (!display.isCloseRequested()) {
            updatePhysics();
            draw(display.getGraphics());
            display.update();
            display.sync(60);
        }
    }

    private void createBalls() {
        for (int i = 0; i < MAX_NUMBER_OF_BALLS; i++) {

            int radius = random.nextInt(maxRadius - minRadius) + minRadius;

            int x = random.nextInt(display.width - radius * 2) + radius;
            int y = random.nextInt(display.height - radius * 2) + radius;

            float speedX = random.nextFloat();
            float speedY = random.nextFloat();

            Ball ball = new Ball(x, y, speedX, speedY, radius);
            balls.add(ball);
        }
    }

    private void updatePhysics() {
        for (int i = 0; i < numberOfBalls; i++) {
            Ball ball = balls.get(i);

            ball.x += ball.speedX * speed;
            ball.y += ball.speedY * speed;

            if (ball.x - ball.radius < 0) {
                ball.speedX = Math.abs(ball.speedX);
            } else if (ball.x + ball.radius > display.width) {
                ball.speedX = -Math.abs(ball.speedX);
            }
            if (ball.y - ball.radius < 0) {
                ball.speedY = Math.abs(ball.speedY);
            } else if (ball.y + ball.radius > display.height) {
                ball.speedY = -Math.abs(ball.speedY);
            }
        }
    }

    private void draw(Graphics2D g) {
        g.setBackground(backgroundColor);
        g.clearRect(0, 0, display.width, display.height);

        for (int i = 0; i < numberOfBalls; i++) {
            Ball ball = balls.get(i);
            g.setColor(color);

            int x = (int) (ball.x - ball.radius);
            int y = (int) (ball.y - ball.radius);
            int size = ball.radius * 2;
            if (color != null) {
                g.fillOval(x, y, size, size);
            } else {
                g.drawImage(cage, x, y, x + size, y + size, 0, 0, cage.getWidth(), cage.getHeight(), null);
            }
        }
    }

}