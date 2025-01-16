import nl.saxion.app.SaxionApp;

import java.awt.*;

public class Powerup {
    static String[] powerupList = {
            "resource/Powerups/2x coins.png",
            "resource/Powerups/inf fuel.png",
            "resource/Powerups/ghost.png",
            "resource/Powerups/energy coin.png",
            "resource/Powerups/shrink.png",
            "resource/Powerups/grow.png",
            "resource/Powerups/magnet.png"
    };

    static String[] infoCards = {
            "resource/Info Cards/2x coins info.png",
            "resource/Info Cards/inf fuel info.png",
            "resource/Info Cards/ghost info.png",
            "resource/Info Cards/energy coin info.png",
            "resource/Info Cards/shrink info.png",
            "resource/Info Cards/grow info.png",
            "resource/Info Cards/magnet info.png"
    };

    int width, height = 80;
    int x, y;
    int carTrack;
    boolean hasSpawned;
    String powerupType = powerupList[SaxionApp.getRandomValueBetween(0, powerupList.length)];
    Rectangle boundingBox = new Rectangle();

    public Powerup(int x, int y, int width, int height, int carTrack) {
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
