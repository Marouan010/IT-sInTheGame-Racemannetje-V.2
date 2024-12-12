import java.awt.*;

public class Fuel {
    int width, height = 60;
    int x, y;
    int carTrack;
    boolean hasSpawned = false;
    String fuelImage = "resource/fuel tank 60-60.png";
    Rectangle boundingBox = new Rectangle();

    public Fuel(int x, int y, int width, int height, int carTrack) {
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

