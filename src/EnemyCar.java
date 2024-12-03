import nl.saxion.app.SaxionApp;

import java.util.ArrayList;

public class EnemyCar {
    String[] carTypes = {
            "resource/auto.png",
            "resource/auto 2.png",
            "resource/auto 3.png",
            "resource/auto 4.png"
    };

    int width, height;
    int x,y;
    int speed;
    int carTrack;
    boolean hasSpawned = false;
    String carType = carTypes[SaxionApp.getRandomValueBetween(0,4)];

    public EnemyCar(int x, int y, int width, int height, int speed, int carTrack) {
        this.y = y;
        this.width = width;
        this.height = height;
        this.carTrack = carTrack;
        this.hasSpawned = false;

        if (carTrack == 1) {
            this.x = 135;
            this.speed = 9;
        } else if (carTrack == 2) {
            this.x = 211;
            this.speed = 7;
        } else if (carTrack == 3) {
            this.x = 293;
            this.speed = 5;
        } else if (carTrack == 4) {
            this.x = 379;
            this.speed = 3;
        } else if (carTrack == 5) {
            this.x = 461;
            this.speed = 1;
        }
    }
}