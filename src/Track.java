import nl.saxion.app.SaxionApp;

public class Track {
    String[] tracks = {
            "resource/baan stil.png",
            "resource/baan1 stil.png",
            "resource/baan2 stil.png",
    };

    int trackNumber = 0;
    String currentTrack = tracks[trackNumber];
    String fuelBar = "resource/tankbar.png";
    String speedometer = "resource/Speedometer.png";

    boolean newTrack = false;

    int speed = 10; // Echte snelheid
    int displaySpeed = 50; // Getoonde snelheid
    int x, y = 0;
    int width = BasicGame.screenWidth;
    int height = BasicGame.screenHeight;

    public void drawFuelBar() {
        SaxionApp.drawImage(fuelBar, 0, -50, 670, 780);
    }

    public void drawSpeedometer(int x, int y) {
        SaxionApp.drawImage(speedometer, x, y);
    }

    public void drawFirst() {
        SaxionApp.drawImage(currentTrack, x, y, width, height);
        if (newTrack && trackNumber != tracks.length - 1) {
            trackNumber++;
            currentTrack = tracks[trackNumber];
            newTrack = false;
        }
    }

    public void drawSecond() {
        SaxionApp.drawImage(currentTrack, x, y - height, width, height);
        if (newTrack && trackNumber != tracks.length - 1) {
            trackNumber++;
            currentTrack = tracks[trackNumber];
            newTrack = false;
        }
    }

    public void updateSpeed(boolean isAccelerating) {
        if (isAccelerating) {
            displaySpeed = 100; // Versnel naar 100
        } else {
            displaySpeed = 50; // Vertraag naar 50
        }
    }

    public void drawSpeedNumber(int x, int y, int digitWidth, int digitHeight) {
        String[] numberImages = new String[10];
        for (int i = 0; i <= 9; i++) {
            numberImages[i] = "resource/numbers/" + i + ".png";
        }

        // Gebruik displaySpeed in plaats van speed
        String speedStr = Integer.toString(displaySpeed);
        int currentX = x;

        for (int i = 0; i < speedStr.length(); i++) {
            int digit = Character.getNumericValue(speedStr.charAt(i));

            SaxionApp.drawImage(numberImages[digit], currentX, y, digitWidth, digitHeight);
            currentX += digitWidth;
        }
    }

    public void draw() {
        drawFirst();
        drawSecond();
        drawFuelBar();
        drawSpeedometer(530, 370);
        if (speed > 10){
            drawSpeedNumber(540,386, 20, 48);
        } else {
            drawSpeedNumber(550,386, 20, 48);
        }

        y += speed;
        if (y > BasicGame.screenHeight) {
            y -= height;
        }
    }
}
