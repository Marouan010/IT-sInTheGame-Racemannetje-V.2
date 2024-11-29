import nl.saxion.app.SaxionApp;

import java.awt.Color;

public class ObstacleCar {
    private int x, y;
    private int speed;
    private final int width = 50;
    private final int height = 100;

    public ObstacleCar(int x, int y, int obstacleSpeed) {
        this.x = x;
        this.y = y;
    }

    public void update() {;
        y += speed; // Move down
    }

    public void draw() {
        SaxionApp.setFill(Color.BLUE);
        SaxionApp.drawRectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
