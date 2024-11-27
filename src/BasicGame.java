import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class BasicGame implements GameLoop {
    public static void main(String[] args) {
        SaxionApp.startGameLoop(new BasicGame(), 1000, 1000, 40);
    }

    private final int screenWidth = 1000;
    private final int screenHeight = 1000;
    private final int laneWidth = screenWidth / 5;

    private PlayerCar player;
    private final ArrayList<ObstacleCar> obstacles = new ArrayList<>();
    private int frameCount = 0;
    private int timer = 0;
    private boolean gameOver = false;

    @Override
    public void init() {
        player = new PlayerCar(screenWidth / 2 - 25, screenHeight - 150);
    }

    @Override
    public void loop() {
        if (!gameOver) {
            SaxionApp.clear();
            timer++;

            drawLanes();
            player.update();
            player.draw();
            spawnObstacles();
            updateObstacles();
            obstacles.removeIf(o -> o.getY() > screenHeight);
            checkCollision();




            SaxionApp.drawText("Time survived: " + timer / 40 + "s", 20, 30, 30);
        } else {
            SaxionApp.clear();
            SaxionApp.drawText("Game Over! Press R to Restart", screenWidth / 2 - 150, screenHeight / 2, 20);
        }
    }

    @Override
    public void keyboardEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_LEFT && keyboardEvent.isKeyPressed()) {
            player.moveLeft();
        }
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_RIGHT && keyboardEvent.isKeyPressed()) {
            player.moveRight();
        }
        if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_R && keyboardEvent.isKeyPressed() && gameOver) {
            restartGame();
        }
    }

    @Override
    public void mouseEvent(MouseEvent mouseEvent) {

    }

    private void drawLanes() {
        SaxionApp.setFill(Color.WHITE);
        for (int i = 1; i < 5; i++) {
            SaxionApp.drawLine(i * laneWidth, 0, i * laneWidth, screenHeight);
        }
    }

    private void spawnObstacles() {
        frameCount++;
        if (frameCount >= 40) {
            frameCount = 0;
            Random rand = new Random();
            int lane = rand.nextInt(5);
            int x = lane * laneWidth + laneWidth / 2 - 25;
            obstacles.add(new ObstacleCar(x, -100));
        }
    }

    private void updateObstacles() {
        for (ObstacleCar obstacle : obstacles) {
            obstacle.update();
            obstacle.draw();
        }
    }

    private void checkCollision() {
        for (ObstacleCar obstacle : obstacles) {
            if (player.collidesWith(obstacle)) {
                gameOver = true;
                return;
            }
        }
    }

    private void restartGame() {
        timer = 0;
        gameOver = false;
        obstacles.clear();
        player = new PlayerCar(screenWidth / 2 - 25, screenHeight - 150);
    }
}




