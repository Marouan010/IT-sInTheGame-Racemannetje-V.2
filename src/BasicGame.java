import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class BasicGame implements GameLoop {

    public static final int screenWidth = 670;
    public static final int screenHeight = 780;
    public static final int FPS = 90;
    public static int frames = 0;
    GameTimer timer = new GameTimer();

    public static void main(String[] args) {
        SaxionApp.startGameLoop(new BasicGame(), screenWidth, screenHeight, 1000 / FPS);
    }

    String currentScreen = "startscreen";
    Rectangle startButtonBounds;
    //Rectangle leaderboardButtonBounds; Nog niet geïmplementeerd
    static Player player = new Player();
    static Track track = new Track();
    SpawnObjects spawn = new SpawnObjects();

    EnemyCar firstCar = new EnemyCar(10, -2000, 65, 140, 1, SaxionApp.getRandomValueBetween(1, 6));

    double fastTrackSpeed = track.speed * 1.5;
    int normalTrackSpeed = track.speed;

    boolean timerDebounce = false;
    boolean powerupDebounce = false;
    int difficultyIncreaseTimer = 30;
    boolean gameOver = false;

//    int deathScreenX = -screenWidth;
//    int deathScreenSpeed = 10;
//    boolean deathScreenArrived = false;
//
//    boolean gameOver = false;

    static boolean doubleCoins = false;
    static boolean infiniteFuel = false;
    static boolean ghost = false;

    @Override
    public void init() {
        Sfx.backgroundsound();

        startButtonBounds = new Rectangle(224, 300, 225, 105);

        int randomTrack = SaxionApp.getRandomValueBetween(1, 6);
        int initY = -1000;
        for (int j = 0; j < 3; j++) {
            Coin firstCoin = new Coin(10, initY, 70, 70, randomTrack);
            initY -= 55;
            spawn.spawnedCoins.add(firstCoin);
            SaxionApp.drawImage("resource/coin 70-70.png", firstCoin.x, firstCoin.y, firstCoin.width, firstCoin.height);
        }

        spawn.spawnedObjects.add(firstCar);
        SaxionApp.drawImage("resource/auto.png", firstCar.x, firstCar.y, firstCar.width, firstCar.height);

    }

    @Override
    public void loop() {
        switch (currentScreen) {
            case "startscreen" -> startScreenLoop();
            case "gamescreen" -> gameScreenLoop();
            case "deathscreen" -> deathScreenLoop();
//            case "resultscreen" -> resultScreenLoop();
        }

    }

    public void startScreenLoop() {
        SaxionApp.clear();

        SaxionApp.setBorderColor(Color.red);
        SaxionApp.drawRectangle(startButtonBounds.x, startButtonBounds.y, startButtonBounds.width, startButtonBounds.height);
        SaxionApp.drawImage("resource/Startscherm placeholder.png", 0, 0, 670, 780);


    }

    public void deathScreenLoop() {

        SaxionApp.drawImage("resource/Wasted.png", 0, 0, screenWidth, screenHeight);
        currentScreen = "deathscreen";


        drawNumberAsImages(timer.getTime(), 475, 370, 50, 65);
        drawNumberAsImages(String.valueOf(player.collectedCoins), 525, 470, 50, 65);
        drawNumberAsImages(String.valueOf(player.carsPassed), 418, 568, 50, 65);
    }

//    public void resultScreenLoop(){
//        SaxionApp.clear();
//    }

    public void drawNumberAsImages(String number, int x, int y, int digitWidth, int digitHeight) {

        String[] numberImages = new String[10];
        for (int i = 0; i <= 9; i++) {
            numberImages[i] = "resource/Font numbers/" + i + ".png";
        }

        int currentX = x;
        int spacing = -17;

        for (char dubbelePunt : number.toCharArray()) {
            if (dubbelePunt == ':') {
                SaxionApp.drawImage("resource/Font numbers/font dubbele punt.png", currentX, y, digitWidth, digitHeight);
            } else {
                int digit = Character.getNumericValue(dubbelePunt);
                SaxionApp.drawImage(numberImages[digit], currentX, y, digitWidth, digitHeight);
            }
            currentX += digitWidth + spacing;
        }
    }


    public void gameScreenLoop() {

        SaxionApp.clear();

        frames++;

        if (!infiniteFuel) {
            player.decreaseFuelAcceleration();
        }

        if (player.fuel == 0) {

            track.speed = 0;
            player.speed = 0;
            timer.timerStop();


        } else
            timer.updateTimer();

        spawn.coin();
        if (player.fuel == player.maxFuel / 2 || player.fuel == player.maxFuel / 4 || player.fuel == player.maxFuel * 3 / 4) {
            if (!infiniteFuel) {
                spawn.fuel();
            }
        }
        if (player.collectedCoins % 50 == 0 && !powerupDebounce && player.collectedCoins != 0) {
            spawn.powerup();
            powerupDebounce = true;
        }
        spawn.object();

        track.draw();
        for (int j = 0; j < spawn.spawnedCoins.size(); j++) {
            SaxionApp.drawImage(spawn.spawnedCoins.get(j).coinImage, spawn.spawnedCoins.get(j).x, spawn.spawnedCoins.get(j).y, spawn.spawnedCoins.get(j).width, spawn.spawnedCoins.get(j).height);
            spawn.spawnedCoins.get(j).y += (track.speed - 3);
        } // update coins
        for (int k = 0; k < spawn.spawnedFuel.size(); k++) {
            SaxionApp.drawImage(spawn.spawnedFuel.get(k).fuelImage, spawn.spawnedFuel.get(k).x - 10, spawn.spawnedFuel.get(k).y, spawn.spawnedFuel.get(k).width, spawn.spawnedFuel.get(k).height);
            spawn.spawnedFuel.get(k).y += (track.speed - 4);

            if (spawn.spawnedFuel.getFirst().y > screenHeight) {
                spawn.spawnedFuel.removeFirst();
            }
        } // update fuel
        for (int i = 0; i < spawn.spawnedObjects.size(); i++) {
            SaxionApp.drawImage(spawn.spawnedObjects.get(i).carType, spawn.spawnedObjects.get(i).x, spawn.spawnedObjects.get(i).y, spawn.spawnedObjects.get(i).width, spawn.spawnedObjects.get(i).height);
            spawn.spawnedObjects.get(i).y = spawn.spawnedObjects.get(i).y - spawn.spawnedObjects.get(i).speed + track.speed;

            if (spawn.spawnedObjects.getFirst().y > BasicGame.screenHeight) {
                spawn.spawnedObjects.removeFirst();
                player.carsPassed++;
            }
        } // update objects
        for (int i = 0; i < spawn.spawnedPowerups.size(); i++) {
            SaxionApp.drawImage(spawn.spawnedPowerups.get(i).powerupType, spawn.spawnedPowerups.get(i).x - 10, spawn.spawnedPowerups.get(i).y, spawn.spawnedPowerups.get(i).width, spawn.spawnedPowerups.get(i).height);
            spawn.spawnedPowerups.get(i).y += (track.speed - 4);

            if (spawn.spawnedPowerups.getFirst().y > screenHeight) {
                spawn.spawnedPowerups.removeFirst();
                powerupDebounce = false;
            }
        } // update powerups
        player.draw();

        if (timer.getTime().equals(String.format("%02d:%02d", difficultyIncreaseTimer / 60, difficultyIncreaseTimer % 60))) {
            if (!timerDebounce && spawn.minDistance > 0) {
                spawn.minDistance -= 50;
                timerDebounce = true;
                difficultyIncreaseTimer += 30;
                track.newTrack = true;
            }
        } else {
            timerDebounce = false;
        } // Increase Difficulty

        checkForCollisions();

        if (currentScreen.equals("gamescreen")) {

            SaxionApp.drawImage("resource/punten 300-200.png", 310, -50);
            SaxionApp.drawText(String.valueOf(player.collectedCoins), 435, 30, 50); // coins collected
            SaxionApp.drawText(String.valueOf(player.carsPassed), 10, 718, 50); // cars passed


            String currentTime = timer.getTime();
            SaxionApp.drawImage("resource/afstand 300-200.png", 50, -50); //Heb de afstand foto gebruikt, maar moet nog vervangen worden
            SaxionApp.drawText(" " + currentTime, 165, 35, 40);


            SaxionApp.drawText(String.valueOf(track.speed), 300, 30, 50); // trackspeed debug

        }


        if (player.upPressed) {
            track.speed = (int) fastTrackSpeed;
            track.updateSpeed(true);
        } else {
            track.speed = normalTrackSpeed;
            track.updateSpeed(false);
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
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_A || keyboardEvent.getKeyCode() == 37) {
                player.leftPressed = true;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_D || keyboardEvent.getKeyCode() == 39) {
                player.rightPressed = true;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_3) {
                doubleCoins = !doubleCoins;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_2) {
                infiniteFuel = !infiniteFuel;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_1) {
                ghost = !ghost;
            }
        }

        if (!keyboardEvent.isKeyPressed()) {
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_W || keyboardEvent.getKeyCode() == 38) {
                player.upPressed = false;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_A || keyboardEvent.getKeyCode() == 37) {
                player.leftPressed = false;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_D || keyboardEvent.getKeyCode() == 39) {
                player.rightPressed = false;
            }
        }
//        if (keyboardEvent.isKeyPressed() && currentScreen.equals("deathscreen")) {
//            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_SPACE) {
//                currentScreen.equals("resultscreen");
//            }
//        }

    }


    @Override
    public void mouseEvent(MouseEvent mouseEvent) {
        if (currentScreen.equals("startscreen")) {
            if (mouseEvent.isLeftMouseButton()) {
                int mouseX = mouseEvent.getX();
                int mouseY = mouseEvent.getY();

                if (startButtonBounds.contains(mouseX, mouseY)) {
//                    System.out.println("Start button clicked!"); //debug
                    currentScreen = "gamescreen";
                }
            }
        }

    }

    public void updatePlayerBoundingBox() {
        player.boundingBox.x = player.x + 10;
        player.boundingBox.y = player.y + 20;
        player.boundingBox.width = player.width - 20;
        player.boundingBox.height = player.height - 50;

        // maak hier wijzigingen aan de hitbox van de speler auto
    }

    public void updateCoinBoundingBox() {
        for (int i = 0; i < spawn.spawnedCoins.size(); i++) {
            spawn.spawnedCoins.get(i).boundingBox.x = spawn.spawnedCoins.get(i).x;
            spawn.spawnedCoins.get(i).boundingBox.y = spawn.spawnedCoins.get(i).y;
            spawn.spawnedCoins.get(i).boundingBox.width = spawn.spawnedCoins.get(i).width;
            spawn.spawnedCoins.get(i).boundingBox.height = spawn.spawnedCoins.get(i).height;

            if (player.boundingBox.intersects(spawn.spawnedCoins.get(i).boundingBox)) {
                spawn.spawnedCoins.remove(i);
                if (doubleCoins) {
                    player.collectedCoins += 2;
                } else {
                    player.collectedCoins++;
                }
            }
        }
    }

    public void updateObjectBoundingBox() {
        for (int j = 0; j < spawn.spawnedObjects.size(); j++) {
            spawn.spawnedObjects.get(j).boundingBox.x = spawn.spawnedObjects.get(j).x;
            spawn.spawnedObjects.get(j).boundingBox.y = spawn.spawnedObjects.get(j).y;
            spawn.spawnedObjects.get(j).boundingBox.width = spawn.spawnedObjects.get(j).width - 5;
            spawn.spawnedObjects.get(j).boundingBox.height = spawn.spawnedObjects.get(j).height - 10;
            // maak hier wijzigingen aan de hitboxen van de auto

            if (player.boundingBox.intersects(spawn.spawnedObjects.get(j).boundingBox) && !ghost || player.fuel == 0) {
                deathScreenLoop();
                SaxionApp.stopLoop();


                // resetGame();
            }
        }
    }

    public void updateFuelBoundingBox() {
        for (int k = 0; k < spawn.spawnedFuel.size(); k++) {
            spawn.spawnedFuel.get(k).boundingBox.x = spawn.spawnedFuel.get(k).x;
            spawn.spawnedFuel.get(k).boundingBox.y = spawn.spawnedFuel.get(k).y;
            spawn.spawnedFuel.get(k).boundingBox.width = spawn.spawnedFuel.get(k).width;
            spawn.spawnedFuel.get(k).boundingBox.height = spawn.spawnedFuel.get(k).height;

            if (player.boundingBox.intersects(spawn.spawnedFuel.get(k).boundingBox)) {
                spawn.spawnedFuel.remove(spawn.spawnedFuel.get(k));
                player.fuel = player.maxFuel;
            }
        }
    }

    public void updatePowerupBoundingBox() {
        for (int i = 0; i < spawn.spawnedPowerups.size(); i++) {
            spawn.spawnedPowerups.get(i).boundingBox.x = spawn.spawnedPowerups.get(i).x;
            spawn.spawnedPowerups.get(i).boundingBox.y = spawn.spawnedPowerups.get(i).y;
            spawn.spawnedPowerups.get(i).boundingBox.width = spawn.spawnedPowerups.get(i).width;
            spawn.spawnedPowerups.get(i).boundingBox.height = spawn.spawnedPowerups.get(i).height;

            if (player.boundingBox.intersects(spawn.spawnedPowerups.get(i).boundingBox)) {
                if (spawn.spawnedPowerups.get(i).powerupType.equals(Powerup.powerupList[0])) {
                    doubleCoins = true;
                } else if (spawn.spawnedPowerups.get(i).powerupType.equals(Powerup.powerupList[1])) {
                    infiniteFuel = true;
                    player.fuel = player.maxFuel;
                } else if (spawn.spawnedPowerups.get(i).powerupType.equals(Powerup.powerupList[2])) {
                    ghost = true;
                }
                startPowerupTimer(spawn.spawnedPowerups.get(i));
                spawn.spawnedPowerups.remove(spawn.spawnedPowerups.get(i));
                powerupDebounce = false;
            }
        }
    }

    public void checkForCollisions() {
        updatePlayerBoundingBox();
        updateCoinBoundingBox();
        updateObjectBoundingBox();
        updateFuelBoundingBox();
        updatePowerupBoundingBox();
    }

    public void startPowerupTimer(Powerup p) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (p.powerupType.equals(Powerup.powerupList[0])) {
                    BasicGame.doubleCoins = false;
                } else if (p.powerupType.equals(Powerup.powerupList[1])) {
                    BasicGame.infiniteFuel = false;
                } else if (p.powerupType.equals(Powerup.powerupList[2])) {
                    BasicGame.ghost = false;
                }
            }
        }, 10000);
    }

    public void resetGame() {
        currentScreen = "startscreen";

        timer.resetTimer();
        player = new Player();
        track = new Track();
        spawn = new SpawnObjects();

        firstCar = new EnemyCar(10, -2000, 65, 140, 1, SaxionApp.getRandomValueBetween(1, 6));

        timerDebounce = false;
        powerupDebounce = false;

        doubleCoins = false;
        infiniteFuel = false;
        ghost = false;

        startButtonBounds = new Rectangle(224, 300, 225, 105);

        int randomTrack = SaxionApp.getRandomValueBetween(1, 6);
        int initY = -1000;
        for (int j = 0; j < 3; j++) {
            Coin firstCoin = new Coin(10, initY, 70, 70, randomTrack);
            initY -= 55;
            spawn.spawnedCoins.add(firstCoin);
            SaxionApp.drawImage("resource/coin 70-70.png", firstCoin.x, firstCoin.y, firstCoin.width, firstCoin.height);
        }

        spawn.spawnedObjects.add(firstCar);
        SaxionApp.drawImage("resource/auto.png", firstCar.x, firstCar.y, firstCar.width, firstCar.height);
    }
}