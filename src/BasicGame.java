import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;



import java.awt.*;

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
    int initY = -300;

    double fastTrackSpeed = track.speed * 1.5;
    int normalTrackSpeed = track.speed;
    double slowTrackSpeed = track.speed * 0.5;

    @Override
    public void init() {
        Sfx.backgroundsound();

        for (int j = 0; j < 3; j++) {
            Coin firstCoin = new Coin(10, initY, 70, 70, 1);
            initY-= 55;
            spawn.spawnedCoins.add(firstCoin);
            SaxionApp.drawImage("resource/coin 70-70.png", firstCoin.x, firstCoin.y, firstCoin.width, firstCoin.height);
        }


        player.boundingBox = new Rectangle(BasicGame.screenWidth / 2 - 42, BasicGame.screenHeight - player.height - 15, 65, 140);


        spawn.spawnedObjects.add(firstCar);
        SaxionApp.drawImage("resource/auto.png", firstCar.x, firstCar.y, firstCar.width, firstCar.height);


    }

    @Override
    public void loop() {
        frames++;

        player.decreaseFuelAcceleration();
        if (player.fuel == 0) {
            track.speed = 0;
            player.speed = 0;
            timer.timerStop();
        } else
            timer.updateTimer();

        SaxionApp.clear();

        spawn.coin();
        spawn.object();

        track.draw();
        for (int j = 0; j < spawn.spawnedCoins.size(); j++) {
            SaxionApp.drawImage(spawn.spawnedCoins.get(j).coinImage, spawn.spawnedCoins.get(j).x, spawn.spawnedCoins.get(j).y, spawn.spawnedCoins.get(j).width, spawn.spawnedCoins.get(j).height);
            spawn.spawnedCoins.get(j).y += (track.speed - 3);
        } // update coins
        for (int i = 0; i < spawn.spawnedObjects.size(); i++) {
            SaxionApp.drawImage(spawn.spawnedObjects.get(i).carType, spawn.spawnedObjects.get(i).x, spawn.spawnedObjects.get(i).y, spawn.spawnedObjects.get(i).width, spawn.spawnedObjects.get(i).height);
            spawn.spawnedObjects.get(i).y = spawn.spawnedObjects.get(i).y - spawn.spawnedObjects.get(i).speed + track.speed;
        } // update objects
        player.draw();

        String currentTime = timer.getTime();
        SaxionApp.drawText(" " + currentTime, 10, 30, 40);

        SaxionApp.drawText(String.valueOf(track.speed), 200, 100, 50); // trackspeed debug

        if (player.upPressed) {
            track.speed = (int) fastTrackSpeed;
        } else if (player.downPressed) {
            track.speed = (int) slowTrackSpeed;
        } else {
            track.speed = normalTrackSpeed;
        }

    }

    @Override
    public void keyboardEvent(KeyboardEvent keyboardEvent) {

        //toeter methode

        Sfx.toeter(keyboardEvent);
        Sfx.remmen(keyboardEvent);


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