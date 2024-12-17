import java.util.Timer;
import java.util.TimerTask;

public class PowerupTimer {
    public void startPowerUpTimer(Powerup p) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (p.powerupType.equals(Powerup.powerupList[0])) {
                    BasicGame.doubleCoins = false;
                } else if (p.powerupType.equals(Powerup.powerupList[1])) {
                    BasicGame.infiniteFuel = false;
                } else if (p.powerupType.equals(Powerup.powerupList[2])) {
                    BasicGame.ghost = false;
                }
            }
        }, 10000);
    }
}
