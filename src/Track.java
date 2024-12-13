import nl.saxion.app.SaxionApp;

public class Track {
    String[] tracks = {
            "resource/baan stil.png",
            "resource/baan1 stil.png",
            "resource/baan2 stil.png"
    };

    int trackNumber = 0;
    String currentTrack = tracks[trackNumber];
    String fuelBar = "resource/tankbar.png";
    String speedometer = "resource/Speedometer.png";

    boolean newTrack = false;

    int speed = 10;
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
        if (newTrack && trackNumber != tracks.length -1) {
            trackNumber++;
            currentTrack = tracks[trackNumber];
            newTrack = false;
        }
    }

    public void drawSecond() {
        SaxionApp.drawImage(currentTrack, x, y - height, width, height);
        if (newTrack && trackNumber != tracks.length -1) {
            trackNumber++;
            currentTrack = tracks[trackNumber];
            newTrack = false;
        }
    }

    public void drawSpeedNumber(int x, int y, int digitWidth, int digitHeight) {

        String[] numberImages = new String[10];
        for (int i = 0; i <= 9; i++) {
            numberImages[i] = "resource/numbers/" + i + ".png";
        }

        // convert to string
        String speedStr = Integer.toString(speed);
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
        drawSpeedNumber(545, 400, 25, 25);
        y += speed;
        if (y > BasicGame.screenHeight) {
            y -= height;
        }
    }


}
