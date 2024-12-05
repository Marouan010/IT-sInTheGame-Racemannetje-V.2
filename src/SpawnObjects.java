import nl.saxion.app.SaxionApp;

import java.util.ArrayList;

public class SpawnObjects {

    ArrayList<EnemyCar> spawnedCars = new ArrayList<>();
    int lastTrack = 1;
    String lastCarType = "";

    public void car() {
        //for (int i = 0; i < spawnedCars.size(); i++) {
          //  SaxionApp.drawImage(spawnedCars.get(i).carType, spawnedCars.get(i).x, spawnedCars.get(i).y, spawnedCars.get(i).width, spawnedCars.get(i).height);
            //spawnedCars.get(i).y = spawnedCars.get(i).y - spawnedCars.get(i).speed + track.speed;
        //}

        for (int j = 0; j < spawnedCars.size(); j++) {
            if (spawnedCars.get(j).y >= 200 && spawnedCars.get(j).y <= 780 && !spawnedCars.get(j).hasSpawned) {

                int randomTrack = SaxionApp.getRandomValueBetween(1, 6);

                while (lastTrack == randomTrack) {
                    randomTrack = SaxionApp.getRandomValueBetween(1, 6);
                }

                EnemyCar car = new EnemyCar(10, -300, 65, 140, 5, randomTrack);

                lastTrack = randomTrack;

                spawnedCars.add(car);
                spawnedCars.get(j).hasSpawned = true;
            }

            if (spawnedCars.get(0).y > BasicGame.screenHeight) {
                spawnedCars.remove(0);
            }
        }
    }

    public void coin() {

    }

    public void obstacle() {

    }
}
