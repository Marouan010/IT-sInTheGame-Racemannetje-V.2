import nl.saxion.app.SaxionApp;

import java.nio.file.LinkPermission;

public class Player {
    Track track = new Track();

    String imageFile = "resource/auto me.png";

    int width = 65;
    int height = 140;
    int x = BasicGame.screenWidth/2 - 42;
    int y = BasicGame.screenHeight - height - 15;
    int speed = 10; // snelheid van links en rechts bewegen

    boolean leftPressed = false;
    boolean rightPressed = false;
    boolean upPressed = false;
    boolean downPressed = false;

    public void InputCheck() {
        if (leftPressed && x > 135) {
            x -= speed;
        }
        if (rightPressed && x < 460) {
            x+= speed;
        }
        if (upPressed) {
            track.speed++;
        }
        if (downPressed) {
            track.speed--;
        }
    }

    public void draw() {
        InputCheck();
        SaxionApp.drawImage(imageFile, x, y, width, height);
    }
}
