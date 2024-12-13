import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    Timer timer = new Timer();
    private int totalSeconds = 0;
    private boolean timerRunning;
    private boolean taskCompleted = false;

    public void timerStop(){
        timerRunning = false;
    }

    public void updateTimer(){
        if (!taskCompleted) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    totalSeconds++;
                    taskCompleted = false;
                }
            }, 1000);
            taskCompleted = true;
        }
    }

    public String getTime() {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
