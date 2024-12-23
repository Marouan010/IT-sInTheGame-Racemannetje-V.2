import nl.saxion.app.SaxionApp;

import java.awt.*;

public class Player {
    Track track = new Track();

    String imageFile = "resource/auto me.png";
    String fuelTankIcon = "resource/fuel tank 60-60.png";
    String nummers = "resource/nummers.png";

    int width = 65;
    int height = 140;
    int x = BasicGame.screenWidth / 2 - 42;
    int y = BasicGame.screenHeight - height - 15;
    int speed = 10; // snelheid van links en rechts bewegen
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
        if (rightPressed && x < 460) {
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
//        if (BasicGame) {
//            imageFile = "resource/auto me ghost.png";
//        } else {
//            imageFile = "resource/auto me.png";
//        }
        SaxionApp.drawImage(imageFile, x, y, width, height);
        drawFuelGauge();
        drawFuelIcon();
    }

}
