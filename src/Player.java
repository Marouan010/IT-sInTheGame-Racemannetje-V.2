import nl.saxion.app.SaxionApp;

import java.awt.*;
import java.util.ArrayList;

public class Player {
    static int playerCarType = 1;

    static int totalCollectedCoins = 0;
    static ArrayList<String> collectedPowerupsList = new ArrayList<>();
    static boolean reachedSkyMap = false;

    Track track = new Track();

    String imageFile = "resource/auto me " + playerCarType + ".png";
    String fuelTankIcon = "resource/fuel tank 60-60.png";

    int normalWidth = 65;
    int width = 65;
    int height = 140;
    int x = BasicGame.screenWidth / 2 - 42;
    int y = BasicGame.screenHeight - height - 15;
    int speed = 7; // snelheid van links en rechts bewegen
    Rectangle boundingBox = new Rectangle();

    // Stats
    int collectedCoins = 0;
    int carsPassed = 0;

    // Fuel gauge
    int maxFuel = 2500;
    volatile int fuel = maxFuel;
    int drainSpeed = 1;
    int fuelGaugeWidth = 15;
    int fuelGaugeHeight = 332;
    int fuelStartX = 103;
    int fuelStartY = 280;
    int colorR = 255;
    int colorG = 0;
    int colorB = 0;

    // Speedometer
    int xS = 50;
    int yS = 50;

    // Digital speedometer
    int digitWidth = 50;
    int digitHeight = 100;
    int digitX = xS;
    int digitY = yS + 50;


    boolean leftPressed = false;
    boolean rightPressed = false;
    boolean upPressed = false;

    public void InputCheck() {
        if (leftPressed && x > 135) {
            x -= speed;
            boundingBox.x -= speed;
        }
        if (!BasicGame.shrink && !BasicGame.grow && rightPressed && x < 460 || BasicGame.shrink && rightPressed && x < 493 || BasicGame.grow && rightPressed && x < 400) {
            x += speed;
            boundingBox.y += speed;
        }
        if (upPressed) {
            track.speed++;
            decreaseFuelAcceleration();
        }
    }

    // Decreases your fuel faster
    public void decreaseFuelAcceleration() {
        if (fuel > 0) {
            fuel -= drainSpeed;
        }
    }


    // Fuel gauge itself
    public void drawFuelGauge() {
        int currentHeight = (int) (fuelGaugeHeight * (fuel / (double) maxFuel));
        int currentY = fuelStartY + (fuelGaugeHeight - currentHeight);

        // Amount of fuel in %
        SaxionApp.drawRectangle(fuelStartX, currentY, fuelGaugeWidth, currentHeight);
        SaxionApp.setBorderColor(new Color(colorR, colorG, colorB));
        SaxionApp.setFill(new Color(colorR, colorG, colorB));
    }

    public void drawFuelIcon() {
        int currentHeight = (int) (fuelGaugeHeight * (fuel / (double) maxFuel));
        int currentY = fuelStartY + (fuelGaugeHeight - currentHeight);
        int x = 80;
        if (y > 100) {
            int y = currentY - 20;
            SaxionApp.drawImage(fuelTankIcon, x, y);
        }

    }




    public void draw() {
        InputCheck();

        if (BasicGame.shrink) {
            width = 33;
            height = 70;
            y = BasicGame.screenHeight - height - 5;
        } else if (BasicGame.grow) {
            width = 130;
            height = 280;
            y = BasicGame.screenHeight - height;
        } else {
            width = normalWidth;
            height = 140;
            y = BasicGame.screenHeight - height - 15;
        }

        if (BasicGame.ghost) {
            imageFile = "resource/Player Cars/auto me " + playerCarType + " ghost.png";
        } else {
            imageFile = "resource/Player Cars/auto me " + playerCarType + ".png";
        }

        SaxionApp.drawImage(imageFile, x, y, width, height);
        drawFuelGauge();
        drawFuelIcon();
    }

}
