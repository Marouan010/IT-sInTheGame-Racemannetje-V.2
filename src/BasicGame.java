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
    GameTimer gameTimer = new GameTimer();
    Timer timer = new Timer();

    public static void main(String[] args) {
        SaxionApp.startGameLoop(new BasicGame(), screenWidth, screenHeight, 1000 / FPS);
    }

    String currentScreen = "startscreen";
    Rectangle startButtonBounds;
    //Rectangle leaderboardButtonBounds;
    Rectangle scoreButtonBounds;
    Rectangle backButtonBounds;

    Rectangle playerCar1ButtonBounds = new Rectangle(147, 600, 65, 140);
    Rectangle playerCar2ButtonBounds = new Rectangle(247, 600, 65, 140);
    Rectangle playerCar3ButtonBounds = new Rectangle(347, 600, 65, 140);
    Rectangle playerCar4ButtonBounds = new Rectangle(447, 600, 65, 140);
    boolean playerCar2Unlocked = false;
    boolean playerCar3Unlocked = false;
    boolean playerCar4Unlocked = false;
    int selectedPlayerCarBorderX = 142;
    boolean drawCarUnlockInfo = false;
    int whichCarUnlockInfo;
    boolean carUnlockInfoTimerActive = false;

    String selectedCard = "";
    String card1 = "";
    String card2 = "";
    String card3 = "";
    Rectangle card1ButtonBounds;
    Rectangle card2ButtonBounds;
    Rectangle card3ButtonBounds;

    static Player player = new Player();
    static Track track = new Track();
    SpawnObjects spawn = new SpawnObjects();

    EnemyCar firstCar = new EnemyCar(10, -2000, 65, 140, 1, SaxionApp.getRandomValueBetween(1, 6));

    double fastTrackSpeed = track.speed * 1.5;
    int normalTrackSpeed = track.speed;

    boolean isCardButtonClickable = false;
    boolean cardClicked = false;
    boolean cardScreen = false;
    boolean drawCardScreenBackdrop = true;

    boolean timerDebounce = false;
    boolean powerupDebounce = false;
    int powerupSpawnThreshold = 50; // 50 coins per powerup spawn
    int powerupTime = 10000; // 10 seconds
    int difficultyIncreaseTimer = 30;

    String activatedPowerup = "";
    boolean powerupInfoShown = false;

    static boolean doubleCoins = false;
    static boolean infiniteFuel = false;
    static boolean ghost = false;
    static boolean energyCoin = false;
    static boolean shrink = false;
    static boolean grow = false;
    static boolean magnet = false;

    boolean fuelSpawnTimerStarted = false;

    String playerName = "";
    boolean playerNameEntered = false;

    @Override
    public void init() {
        Sfx.backgroundsound();

        startButtonBounds = new Rectangle(224, 322, 220, 75);
        scoreButtonBounds = new Rectangle(217, 450, 220, 75);
        // backButtonBounds = new Rectangle(0,50,100,120);

        int randomTrack = SaxionApp.getRandomValueBetween(1, 6);
        int initY = -1000;
        for (int j = 0; j < 3; j++) {
            Coin firstCoin = new Coin(10, initY, 70, 70, randomTrack);
            initY -= 55;
            spawn.spawnedCoins.add(firstCoin);
            SaxionApp.drawImage("resource/coin 70-70.png", firstCoin.x, firstCoin.y, firstCoin.width, firstCoin.height);
        }

        spawn.spawnedObjects.add(firstCar);
        SaxionApp.drawImage("resource/Car Types/auto.png", firstCar.x, firstCar.y, firstCar.width, firstCar.height);

    }

    @Override
    public void loop() {
        switch (currentScreen) {
            case "startscreen" -> startScreenLoop();
            case "cardscreen" -> cardScreenLoop();
            case "gamescreen" -> gameScreenLoop();
            case "deathscreen" -> deathScreenLoop();
            case "leaderboard" -> leaderboardScreenLoop();
        }

    }

    public void startScreenLoop() {
        SaxionApp.clear();

        if (Player.collectedPowerupsList.size() == 6 && !playerCar2Unlocked) {
            playerCar2Unlocked = true;
        }
        if (Player.reachedSkyMap && !playerCar3Unlocked) {
            playerCar3Unlocked = true;
        }
        if (Player.totalCollectedCoins >= 1000 && !playerCar4Unlocked) {
            playerCar4Unlocked = true;
        }

        SaxionApp.setBorderColor(Color.red);
        SaxionApp.drawImage("resource/totaal plaatje startscherm.png", 0, 0, 670, 780);

        //startknop toevoegen
        SaxionApp.setBorderColor(Color.red);
        SaxionApp.drawRectangle(startButtonBounds.x, startButtonBounds.y, startButtonBounds.width, startButtonBounds.height);
        SaxionApp.drawImage("resource/start knop groot.png", 180, 300);

        //score knop toevoegen
        SaxionApp.setBorderColor(Color.red);
        SaxionApp.drawRectangle(scoreButtonBounds.x, scoreButtonBounds.y, scoreButtonBounds.width, scoreButtonBounds.height);
        SaxionApp.drawImage("resource/score knop groot.png", 180, 423);

        SaxionApp.drawImage("resource/SelectedCarBorder.png", selectedPlayerCarBorderX, 595, 75, 150);
        SaxionApp.drawImage("resource/CarSelectionBorder.png", 108, 567, 445, 200);
        SaxionApp.drawImage("resource/Player Cars/auto me 1.png", 147, 600, 65, 140);
        SaxionApp.drawImage("resource/Player Cars/auto me 2.png", 247, 600, 65, 140);
        SaxionApp.drawImage("resource/Player Cars/auto me 3.png", 347, 600, 65, 140);
        SaxionApp.drawImage("resource/Player Cars/auto me 4.png", 447, 600, 65, 140);

        if (drawCarUnlockInfo) {
            if (whichCarUnlockInfo == 2) {
                SaxionApp.drawImage("resource/auto me 2 unlock info.png", 176, 147, 310, 150);
                SaxionApp.setTextDrawingColor(Color.yellow);
                SaxionApp.drawText("(" + Player.collectedPowerupsList.size() + "/6)", 360, 244, 27);
            } else if (whichCarUnlockInfo == 3) {
                SaxionApp.drawImage("resource/auto me 3 unlock info.png", 176, 147, 310, 150);
            } else if (whichCarUnlockInfo == 4) {
                SaxionApp.drawImage("resource/auto me 4 unlock info.png", 176, 147, 310, 150);
                SaxionApp.setTextDrawingColor(Color.yellow);
                SaxionApp.drawText("(" + Player.totalCollectedCoins + "/1000)", 317, 242, 27);
            }

            if (!carUnlockInfoTimerActive) {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        drawCarUnlockInfo = false;
                        carUnlockInfoTimerActive = false;
                    }
                }, 5000);
            }
            carUnlockInfoTimerActive = true;
        }

        if (!playerCar2Unlocked) {
            SaxionApp.drawImage("resource/Player Cars/locked icon.png", 244, 625, 70, 70);
        }
        if (!playerCar3Unlocked) {
            SaxionApp.drawImage("resource/Player Cars/locked icon.png", 344, 625, 70, 70);
        }
        if (!playerCar4Unlocked) {
            SaxionApp.drawImage("resource/Player Cars/locked icon.png", 444, 625, 70, 70);
        }
    }

    public void leaderboardScreenLoop() {
        SaxionApp.clear();
        SaxionApp.drawImage("resource/leaderboard plaatje.png", 0, 0, 670, 780);

        //titell
        //SaxionApp.drawText("leaderboard", 250,100,30);
        //test scores ( voor nu geen csv reader puur om te testen
        //SaxionApp.drawText("1. Ali - 1250 coins", 250,200,20);
        //SaxionApp.drawText("1. Kjeld - 950 coins", 250,250,20);
        //SaxionApp.drawText("1. test - 100 coins", 250,300,20);


        //terug knop toevoegen
//        SaxionApp.setBorderColor(Color.white);
//        SaxionApp.drawRectangle(backButtonBounds.x, backButtonBounds.y, backButtonBounds.width, backButtonBounds.height);
//        SaxionApp.drawImage("resource/exit button.png", backButtonBounds.x, backButtonBounds.y, backButtonBounds.width, backButtonBounds.height);

    }

    public void deathScreenLoop() {

        SaxionApp.drawImage("resource/Deathscreen_final.png", 0, 0, screenWidth, screenHeight);
        currentScreen = "deathscreen";


        drawNumberAsImages(gameTimer.getTime(), 475, 302, 50, 65);
        drawNumberAsImages(String.valueOf(player.collectedCoins), 525, 402, 50, 65);
        drawNumberAsImages(String.valueOf(player.carsPassed), 418, 500, 50, 65);

        SaxionApp.drawText(playerName, 120, 675, 40);

        if (playerNameEntered) {
            //CSV functionality
        }


    }


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

        if (Track.currentTrack.equals("resource/Tracks/baan5 stil.png") && !Player.reachedSkyMap) {
            Player.reachedSkyMap = true;
        }

        if (!infiniteFuel) {
            player.decreaseFuelAcceleration();
        }

        gameTimer.updateTimer();

        spawn.coin();

        if (!fuelSpawnTimerStarted && !infiniteFuel) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    spawn.fuel();
                    fuelSpawnTimerStarted = false;
                }
            }, SaxionApp.getRandomValueBetween(5000, 10000));
            fuelSpawnTimerStarted = true;
        }
        if (player.collectedCoins % powerupSpawnThreshold == 0 && !powerupDebounce && player.collectedCoins != 0) {
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
        if (player.collectedCoins % powerupSpawnThreshold != 0) {
            powerupDebounce = false;
        }
        SaxionApp.setTextDrawingColor(Color.red);
        player.draw();

        if (gameTimer.getTotalSeconds() == difficultyIncreaseTimer) {
            if (!timerDebounce) {
                timerDebounce = true;
                difficultyIncreaseTimer += 30;
                track.newTrack = true;
                cardScreen = true;
            }
        } else {
            timerDebounce = false;
        } // Increase Difficulty

        checkForCollisions();

        if (currentScreen.equals("gamescreen")) {

            SaxionApp.drawImage("resource/punten 300-200.png", 310, -50);

            SaxionApp.setTextDrawingColor(Color.white);
            SaxionApp.drawText(String.valueOf(player.collectedCoins), 435, 30, 50); // coins collected

            String currentTime = gameTimer.getTime();
            SaxionApp.setTextDrawingColor(Color.pink);
            SaxionApp.drawImage("resource/afstand 300-200.png", 50, -50); //Heb de afstand foto gebruikt, maar moet nog vervangen worden
            SaxionApp.drawText(" " + currentTime, 165, 35, 40);
        }


        if (player.upPressed) {
            track.speed = (int) fastTrackSpeed;
            track.updateSpeed(true);
        } else {
            track.speed = normalTrackSpeed;
            track.updateSpeed(false);
        }

        if (cardScreen) {
            currentScreen = "cardscreen";
            cardScreen = false;
        }

        if (!activatedPowerup.isEmpty() && !currentScreen.equals("deathscreen")) {
            SaxionApp.drawImage(activatedPowerup, screenWidth - 235, 140, 240, 89);
            if (!powerupInfoShown) {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        activatedPowerup = "";
                        powerupInfoShown = false;
                    }
                }, 4500);
            }
            powerupInfoShown = true;
        }

    }

    public void cardScreenLoop() {
        if (drawCardScreenBackdrop) {
            SaxionApp.drawImage("resource/blackbackdrop.png", 0, 0, screenWidth, screenHeight);
            isCardButtonClickable = true;
            drawCardScreenBackdrop = false;
            card1 = DebuffCards.cardList[SaxionApp.getRandomValueBetween(0, DebuffCards.cardList.length)];
            card2 = DebuffCards.cardList[SaxionApp.getRandomValueBetween(0, DebuffCards.cardList.length)];
            card3 = DebuffCards.cardList[SaxionApp.getRandomValueBetween(0, DebuffCards.cardList.length)];
            SaxionApp.drawImage(card1, 50, 260, 160, 275);
            SaxionApp.drawImage(card2, 250, 260, 160, 275);
            SaxionApp.drawImage(card3, 450, 260, 160, 275);
        }

        card1ButtonBounds = new Rectangle();
        card1ButtonBounds.x = 50;
        card1ButtonBounds.y = 260;
        card1ButtonBounds.width = 160;
        card1ButtonBounds.height = 275;

        card2ButtonBounds = new Rectangle();
        card2ButtonBounds.x = 250;
        card2ButtonBounds.y = 260;
        card2ButtonBounds.width = 160;
        card2ButtonBounds.height = 275;

        card3ButtonBounds = new Rectangle();
        card3ButtonBounds.x = 450;
        card3ButtonBounds.y = 260;
        card3ButtonBounds.width = 160;
        card3ButtonBounds.height = 275;

        if (cardClicked) {
            if (selectedCard.equals(DebuffCards.cardList[0])) {
                player.normalWidth += 7;
            } else if (selectedCard.equals(DebuffCards.cardList[1])) {
                player.drainSpeed *= 2;
            } else if (selectedCard.equals(DebuffCards.cardList[2])) {
                powerupSpawnThreshold += 10;
            } else if (selectedCard.equals(DebuffCards.cardList[3])) {
                spawn.minDistance -= 50;
            } else if (selectedCard.equals(DebuffCards.cardList[4])) {
                if (player.speed > 2) {
                    player.speed -= 2;
                }
            } else if (selectedCard.equals(DebuffCards.cardList[5])) {
                powerupTime -= 1000;
            }

            currentScreen = "gamescreen";
            drawCardScreenBackdrop = true;
            cardClicked = false;
        }
    }

    @Override
    public void keyboardEvent(KeyboardEvent keyboardEvent) {
        //sound effects toevoegen.
        Sfx.toeter(keyboardEvent);
        Sfx.remmen(keyboardEvent);
        Sfx.tegenwind(keyboardEvent);


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

        if (currentScreen.equals("leaderboard")) {

            if (keyboardEvent.isKeyPressed()) {
                if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ESCAPE) {
                    currentScreen = "startscreen";
                }
            }

        }


        if (currentScreen.equals("deathscreen")) {
            SaxionApp.setTextDrawingColor(Color.white);


            if (keyboardEvent.isKeyPressed()) {
                char key = (char) keyboardEvent.getKeyCode();

                if (Character.isLetterOrDigit(key) && playerName.length() < 14) {
                    playerName += key;
                }
                if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_BACK_SPACE && !playerName.isEmpty()) {
                    playerName = playerName.substring(0, playerName.length() - 1);
                }
                if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ENTER && !playerName.isEmpty()) {
                    playerNameEntered = true;
                    resetGame();
                }
            }
        }


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

                //controleren of er op de score knop is gedrukt.
                if (scoreButtonBounds.contains(mouseX, mouseY)) {
//                    System.out.println("leaderboard knop is ingedrukt.");
                    currentScreen = "leaderboard";
                }

                if (playerCar1ButtonBounds.contains(mouseX, mouseY)) {
                    selectedPlayerCarBorderX = 142;
                    Player.playerCarType = 1;
                }
                if (playerCar2ButtonBounds.contains(mouseX, mouseY)) {
                    if (playerCar2Unlocked) {
                        selectedPlayerCarBorderX = 242;
                        Player.playerCarType = 2;
                    } else {
                        whichCarUnlockInfo = 2;
                        drawCarUnlockInfo = true;
                    }
                }
                if (playerCar3ButtonBounds.contains(mouseX, mouseY)) {
                    if (playerCar3Unlocked) {
                        selectedPlayerCarBorderX = 342;
                        Player.playerCarType = 3;
                    } else {
                        whichCarUnlockInfo = 3;
                        drawCarUnlockInfo = true;
                    }
                }
                if (playerCar4ButtonBounds.contains(mouseX, mouseY)) {
                    if (playerCar4Unlocked) {
                        selectedPlayerCarBorderX = 442;
                        Player.playerCarType = 4;
                    } else {
                        whichCarUnlockInfo = 4;
                        drawCarUnlockInfo = true;
                    }
                }

            } else if (currentScreen.equals("leaderboard")) {
                if (mouseEvent.isLeftMouseButton()) {
                    int mouseX = mouseEvent.getX();
                    int mouseY = mouseEvent.getY();

                    if (backButtonBounds.contains(mouseX, mouseY)) {
                        System.out.println("terug knop ingedrukt.");
                        currentScreen = "startscreen";

                    }


                }


            }


        } else if (currentScreen.equals("cardscreen")) {
            if (mouseEvent.isLeftMouseButton()) {
                int mouseX = mouseEvent.getX();
                int mouseY = mouseEvent.getY();

                if (card1ButtonBounds.contains(mouseX, mouseY) && isCardButtonClickable) {
                    selectedCard = card1;
                    cardClicked = true;
                    isCardButtonClickable = false;
                } else if (card2ButtonBounds.contains(mouseX, mouseY) && isCardButtonClickable) {
                    selectedCard = card2;
                    cardClicked = true;
                    isCardButtonClickable = false;
                } else if (card3ButtonBounds.contains(mouseX, mouseY) && isCardButtonClickable) {
                    selectedCard = card3;
                    cardClicked = true;
                    isCardButtonClickable = false;
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

            if (player.boundingBox.intersects(spawn.spawnedCoins.get(i).boundingBox) || magnet && spawn.spawnedCoins.get(i).boundingBox.y > 575) {
                spawn.spawnedCoins.remove(i);
                if (doubleCoins) {
                    player.collectedCoins += 2;
                } else {
                    player.collectedCoins++;
                }

                if (energyCoin) {
                    if (player.fuel + 125 > player.maxFuel) {
                        player.fuel = player.maxFuel;
                    } else {
                        player.fuel += 125;
                    }
                }

                Sfx.coinPickup();
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

            if (player.boundingBox.intersects(spawn.spawnedObjects.get(j).boundingBox) || player.fuel == 0) {
                if (!ghost && !grow) {
                    currentScreen = "deathscreen";
                    track.speed = 0;
                    player.speed = 0;
                    gameTimer.timerStop();
                } else if (grow) {
                    spawn.spawnedObjects.remove(j);
                }
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
                if (player.fuel + 1500 > player.maxFuel) {
                    player.fuel = player.maxFuel;
                } else {
                    player.fuel += 1500;
                }

                Sfx.fuelPickup();
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
                    activatedPowerup = Powerup.infoCards[0];
                    if (!Player.collectedPowerupsList.contains("doublecoins")) {
                        Player.collectedPowerupsList.add("doublecoins");
                    }
                } else if (spawn.spawnedPowerups.get(i).powerupType.equals(Powerup.powerupList[1])) {
                    infiniteFuel = true;
                    player.fuel = player.maxFuel;
                    activatedPowerup = Powerup.infoCards[1];
                    if (!Player.collectedPowerupsList.contains("infinitefuel")) {
                        Player.collectedPowerupsList.add("infinitefuel");
                    }
                } else if (spawn.spawnedPowerups.get(i).powerupType.equals(Powerup.powerupList[2])) {
                    ghost = true;
                    activatedPowerup = Powerup.infoCards[2];
                    if (!Player.collectedPowerupsList.contains("ghost")) {
                        Player.collectedPowerupsList.add("ghost");
                    }
                } else if (spawn.spawnedPowerups.get(i).powerupType == Powerup.powerupList[3]) {
                    energyCoin = true;
                    activatedPowerup = Powerup.infoCards[3];
                    if (!Player.collectedPowerupsList.contains("energycoin")) {
                        Player.collectedPowerupsList.add("energycoin");
                    }
                } else if (spawn.spawnedPowerups.get(i).powerupType == Powerup.powerupList[4]) {
                    shrink = true;
                    activatedPowerup = Powerup.infoCards[4];
                    if (!Player.collectedPowerupsList.contains("shrink")) {
                        Player.collectedPowerupsList.add("shrink");
                    }
                } else if (spawn.spawnedPowerups.get(i).powerupType == Powerup.powerupList[5]) {
                    grow = true;
                    activatedPowerup = Powerup.infoCards[5];
                    if (!Player.collectedPowerupsList.contains("grow")) {
                        Player.collectedPowerupsList.add("grow");
                    }
                } else if (spawn.spawnedPowerups.get(i).powerupType == Powerup.powerupList[6]) {
                    magnet = true;
                    activatedPowerup = Powerup.infoCards[6];
                    if (!Player.collectedPowerupsList.contains("magnet")) {
                        Player.collectedPowerupsList.add("magnet");
                    }
                }
                startPowerupTimer(spawn.spawnedPowerups.get(i));
                spawn.spawnedPowerups.remove(spawn.spawnedPowerups.get(i));
                Sfx.powerupPickup();
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
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (p.powerupType.equals(Powerup.powerupList[0])) {
                    BasicGame.doubleCoins = false;
                } else if (p.powerupType.equals(Powerup.powerupList[1])) {
                    BasicGame.infiniteFuel = false;
                } else if (p.powerupType.equals(Powerup.powerupList[2])) {
                    BasicGame.ghost = false;
                } else if (p.powerupType.equals(Powerup.powerupList[3])) {
                    energyCoin = false;
                } else if (p.powerupType.equals(Powerup.powerupList[4])) {
                    shrink = false;
                } else if (p.powerupType.equals(Powerup.powerupList[5])) {
                    grow = false;
                } else if (p.powerupType.equals(Powerup.powerupList[6])) {
                    magnet = false;
                }
            }
        }, powerupTime);
    }

    public void resetGame() {
        timer.cancel();
        timer = new Timer();

        selectedCard = "";
        card1 = "";
        card2 = "";
        card3 = "";

        Track.trackNumber = 0;
        Track.currentTrack = Track.tracks[Track.trackNumber];

        Track.trackNumber = 0;

        gameTimer.resetTimer();
        difficultyIncreaseTimer = 30;

        currentScreen = "startscreen";

        Player.totalCollectedCoins += player.collectedCoins;

        player = new Player();
        track = new Track();
        spawn = new SpawnObjects();

        firstCar = new EnemyCar(10, -2000, 65, 140, 1, SaxionApp.getRandomValueBetween(1, 6));

        timerDebounce = false;
        powerupDebounce = false;
        powerupTime = 10000;
        powerupSpawnThreshold = 50;

        activatedPowerup = "";
        powerupInfoShown = false;

        playerName = "";

        doubleCoins = false;
        infiniteFuel = false;
        ghost = false;
        energyCoin = false;
        shrink = false;
        grow = false;
        magnet = false;

        fuelSpawnTimerStarted = false;

        startButtonBounds = new Rectangle(224, 322, 220, 75);
        backButtonBounds = new Rectangle(0, 50, 100, 120);

        int randomTrack = SaxionApp.getRandomValueBetween(1, 6);
        int initY = -1000;
        for (int j = 0; j < 3; j++) {
            Coin firstCoin = new Coin(10, initY, 70, 70, randomTrack);
            initY -= 55;
            spawn.spawnedCoins.add(firstCoin);
            SaxionApp.drawImage("resource/coin 70-70.png", firstCoin.x, firstCoin.y, firstCoin.width, firstCoin.height);
        }

        spawn.spawnedObjects.add(firstCar);
        SaxionApp.drawImage("resource/Car Types/auto.png", firstCar.x, firstCar.y, firstCar.width, firstCar.height);
    }
}