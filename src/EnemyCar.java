import nl.saxion.app.SaxionApp;

import java.awt.*;
import java.util.ArrayList;

public class EnemyCar {
    static String[][] carTypes = {
            {"resource/Car Types/auto.png", "resource/Car Types/auto 3.png", "resource/Car Types/auto 4.png"},
            {"resource/Car Types/auto woestijn.png", "resource/Car Types/auto woestijn 3.png", "resource/Car Types/auto woestijn 4.png"},
            {"resource/Car Types/auto zee.png", "resource/Car Types/auto zee 3.png", "resource/Car Types/auto zee 4.png"},
            {"resource/Car Types/auto ijs.png", "resource/Car Types/auto ijs 3.png", "resource/Car Types/auto ijs 4.png"},
            {"resource/Car Types/auto lava.png", "resource/Car Types/auto lava 3.png", "resource/Car Types/auto lava 4.png"},
            {"resource/Car Types/auto lucht.png", "resource/Car Types/auto lucht 3.png", "resource/Car Types/auto lucht 4.png"}
    };

    int width, height;
    int x,y;
    int speed;
    int carTrack;
    boolean hasSpawned = false;
    String carType = carTypes[0][SaxionApp.getRandomValueBetween(0,carTypes[0].length)];
    Rectangle boundingBox = new Rectangle();


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
            this.speed = SaxionApp.getRandomValueBetween(4, 6);;
        } else if (carTrack == 4) {
            this.x = 379;
            this.speed = SaxionApp.getRandomValueBetween(1, 3);
        } else if (carTrack == 5) {
            this.x = 461;
            this.speed = 1;
        }
    }
}