import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;

import java.awt.*;
import java.util.ArrayList;

public class BasicGame implements GameLoop {

    public static final int screenWidth = 670;
    public static final int screenHeight = 780;
    public static final int FPS = 90;
    public static int frames = 0;
    GameTimer timer = new GameTimer(FPS);

    public static void main(String[] args) {
        SaxionApp.startGameLoop(new BasicGame(), screenWidth, screenHeight, 1000 / FPS);
    }

    Player player = new Player();
    Track track = new Track();
    SpawnObjects spawn = new SpawnObjects();

    EnemyCar firstCar = new EnemyCar(10, -100, 65, 140, 1, 1);

    @Override
    public void init() {
        player.boundingBox = new Rectangle(BasicGame.screenWidth / 2 - 42, BasicGame.screenHeight - player.height - 15, 65, 140);


        spawn.spawnedCars.add(firstCar);
        SaxionApp.drawImage("resource/auto.png", firstCar.x, firstCar.y, firstCar.width, firstCar.height);
    }

    @Override
    public void loop() {
        frames++;

        if (frames % (FPS * 10) == 0) {
            track.speed++;
        }

        player.decreaseFuelAcceleration();
        if (player.fuel == 0) {
            track.speed = 0;
            player.speed = 0;
            timer.timerStop();
        } else
            timer.updateTimer();

        SaxionApp.clear();

        track.draw();
        player.draw();
        spawn.car();

        String currentTime = timer.getTime();
        SaxionApp.drawText(" " + currentTime, 10, 30, 40);

        SaxionApp.drawText(String.valueOf(track.speed), 200, 100, 50); // trackspeed debug

        // de rest van de code in de loop is gekopieerd uit spawnobjects, omdat de trackspeed en carspeed gewoon niet
        // willen updaten via de void methodes, dus plakte ik ze hier neer om dat ff te fixen
        for (int i = 0; i < spawn.spawnedCars.size(); i++) {
            SaxionApp.drawImage(spawn.spawnedCars.get(i).carType, spawn.spawnedCars.get(i).x, spawn.spawnedCars.get(i).y, spawn.spawnedCars.get(i).width, spawn.spawnedCars.get(i).height);
            spawn.spawnedCars.get(i).y = spawn.spawnedCars.get(i).y - spawn.spawnedCars.get(i).speed + track.speed;
        }

        if (player.upPressed & track.speed <= 24) {
            track.speed++;

        }
        if (player.downPressed & track.speed >= 9) {
            track.speed--;
        }

    }

    @Override
    public void keyboardEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.isKeyPressed()) {
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_W || keyboardEvent.getKeyCode() == 38) {
                player.upPressed = true;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_S || keyboardEvent.getKeyCode() == 40) {
                player.downPressed = true;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_A || keyboardEvent.getKeyCode() == 37) {
                player.leftPressed = true;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_D || keyboardEvent.getKeyCode() == 39) {
                player.rightPressed = true;
            }
        }

        if (!keyboardEvent.isKeyPressed()) {
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_W || keyboardEvent.getKeyCode() == 38) {
                player.upPressed = false;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_S || keyboardEvent.getKeyCode() == 40) {
                player.downPressed = false;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_A || keyboardEvent.getKeyCode() == 37) {
                player.leftPressed = false;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_D || keyboardEvent.getKeyCode() == 39) {
                player.rightPressed = false;
            }
        }
    }

    @Override
    public void mouseEvent(MouseEvent mouseEvent) {

    }
}