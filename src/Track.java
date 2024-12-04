import nl.saxion.app.SaxionApp;

public class Track {
    String imageFile = "resource/baan stil.png";
    String fuelBar = "resource/tankbar.png";

    int speed = 10;
    int x, y = 0;
    int width = BasicGame.screenWidth;
    int height = BasicGame.screenHeight;

    public void drawFuelBar(){SaxionApp.drawImage(fuelBar, 0, -50, 670, 780);}
    public void drawFirst() {
        SaxionApp.drawImage(imageFile, x, y, width, height);
    }

    public void drawSecond() {
        SaxionApp.drawImage(imageFile, x, y - height, width, height);
    }

    public void draw() {
        drawFirst();
        drawSecond();
        drawFuelBar();
        y += speed;
        if (y > BasicGame.screenHeight) {
            y -= height;
        }
    }

}
