import nl.saxion.app.SaxionApp;

import java.awt.*;
import java.util.ArrayList;

public class EnemyCar {
    String[] carTypes = {
            "resource/auto.png",
            "resource/auto 3.png",
            "resource/auto 4.png"
    };

    int width, height;
    int x,y;
    int speed;
    int carTrack;
    boolean hasSpawned = false;
    String carType = carTypes[SaxionApp.getRandomValueBetween(0,3)];


    public EnemyCar(int x, int y, int width, int height, int speed, int carTrack) {
        this.y = y;
        this.width = width;
        this.height = height;
        this.carTrack = carTrack;
        this.hasSpawned = false;

        if (carTrack == 1) {
            this.x = 135;
            this.speed = SaxionApp.getRandomValueBetween(7, 9);
        } else if (carTrack == 2) {
            this.x = 211;
            this.speed = SaxionApp.getRandomValueBetween(5, 7);
        } else if (carTrack == 3) {
            this.x = 293;
            this.speed = SaxionApp.getRandomValueBetween(4, 6);
        } else if (carTrack == 4) {
            this.x = 379;
            this.speed = SaxionApp.getRandomValueBetween(1, 3);
        } else if (carTrack == 5) {
            this.x = 461;
            this.speed = 1;
        }
    }
}