public class GameTimer {
    private int beginFrames;
    private int fps;
    private boolean timerRunning;

    public GameTimer(int fps){
        this.fps = fps;
        this.beginFrames = 0;
        this.timerRunning = true;
    }

    public void timerStop(){
        timerRunning = false;
    }

    public void updateTimer(){
        beginFrames++;
    }

    public String getTime() {
        int totalSeconds = beginFrames / fps;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
