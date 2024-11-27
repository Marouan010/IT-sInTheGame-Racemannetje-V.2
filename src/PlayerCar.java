import nl.saxion.app.SaxionApp;

import java.awt.Color;

public class PlayerCar {
    private int x;
    private final int y;
    private final int width = 50;
    private final int height = 100;
    private int targetLane = 2;
    private final int laneWidth;

    public PlayerCar(int x, int y) {
        this.x = x;
        this.y = y;
        laneWidth = 1000 / 5;
    }

    public void update() {
        int targetX = targetLane * laneWidth + laneWidth / 2 - width / 2;
        int speed = 10;
        if (x < targetX) x = Math.min(targetX, x + speed);
        if (x > targetX) x = Math.max(targetX, x - speed);
    }

    public void draw() {
        SaxionApp.setFill(Color.RED);
        SaxionApp.drawRectangle(x, y, width, height);
    }

    public void moveLeft() {
        if (targetLane > 0) targetLane--;
    }

    public void moveRight() {
        if (targetLane < 4) targetLane++;
    }

    public boolean collidesWith(ObstacleCar obstacle) {
        return x < obstacle.getX() + obstacle.getWidth() &&
                x + width > obstacle.getX() &&
                y < obstacle.getY() + obstacle.getHeight() &&
                y + height > obstacle.getY();
    }
}
