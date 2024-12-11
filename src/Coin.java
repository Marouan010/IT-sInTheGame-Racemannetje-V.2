import nl.saxion.app.SaxionApp;

import java.awt.*;

public class Coin {
    int width, height = 70;
    int x, y;
    int carTrack;
    boolean hasSpawned = false;
    String coinImage = "resource/coin 70-70.png";
    Rectangle boundingBox = new Rectangle();

    public Coin(int x, int y, int width, int height, int carTrack) {
        this.y = y;
        this.width = width;
        this.height = height;
        this.carTrack = carTrack;
        this.hasSpawned = false;

        if (carTrack == 1) {
            this.x = 135;
        } else if (carTrack == 2) {
            this.x = 211;
        } else if (carTrack == 3) {
            this.x = 293;
        } else if (carTrack == 4) {
            this.x = 379;
        } else if (carTrack == 5) {
            this.x = 461;
        }
    }
}
