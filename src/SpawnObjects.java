import nl.saxion.app.SaxionApp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SpawnObjects {
    ArrayList<EnemyCar> spawnedObjects = new ArrayList<>();
    ArrayList<Coin> spawnedCoins = new ArrayList<>();
    ArrayList<Fuel> spawnedFuel = new ArrayList<>();
    int lastObjectTrack = 1;
    int initY = -300;
    int coins = 3;
    int minDistance = 200;

    public void object() {

        for (int j = 0; j < spawnedObjects.size(); j++) {
            if (spawnedObjects.get(j).y >= minDistance && spawnedObjects.get(j).y <= 780 && !spawnedObjects.get(j).hasSpawned) {

                int randomTrack = SaxionApp.getRandomValueBetween(1, 6);

                while (lastObjectTrack == randomTrack) {
                    randomTrack = SaxionApp.getRandomValueBetween(1, 6);
                }

                int randomObject = SaxionApp.getRandomValueBetween(1,6);

                if (randomObject == 1) {
                    EnemyCar cone = new EnemyCar(10, -300, 42, 42, 0, randomTrack);
                    cone.carType = "cone";
                    cone.speed = 3;
                    cone.carType = "resource/cone 42-42.png";
                    cone.x+= 12;
                    spawnedObjects.add(cone);
                } else {
                    EnemyCar car = new EnemyCar(10, -300, 65, 140, 5, randomTrack);
                    spawnedObjects.add(car);
                }

                lastObjectTrack = randomTrack;


                spawnedObjects.get(j).hasSpawned = true;
            }
        }
    }

    public void coin() {
        for (int i = 0; i < spawnedCoins.size(); i++) {
            if (spawnedCoins.size()<3) {
                coins = 0;
                int randomTrack = SaxionApp.getRandomValueBetween(1,6);

                for (int j = 0; j < 3; j++) {
                    Coin coin = new Coin(10, initY, 70, 70, randomTrack);
                    coin.x -= 3;
                    spawnedCoins.add(coin);
                    initY-= 55;
                    coins++;
                }
                initY = -300;

                spawnedCoins.get(i).hasSpawned = true;
            }

            if (spawnedCoins.get(0).y > BasicGame.screenHeight) {
                spawnedCoins.remove(0);
            }
        }
    }

    public void fuel() {
        for (int k = -1; k < spawnedFuel.size(); k++) {
            if (spawnedFuel.size() == 0) {
                int randomTrack = SaxionApp.getRandomValueBetween(1, 6);

                Fuel newFuel = new Fuel(10, -200, 80, 80, randomTrack);
                spawnedFuel.add(newFuel);
            } else {
                return;
            }
        }
    }
}
