import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;

public class BasicGame implements GameLoop {

    public static final int screenWidth = 670;
    public static final int screenHeight = 780;
    public static final int FPS = 60;

    public static void main(String[] args) {
        SaxionApp.startGameLoop(new BasicGame(), screenWidth, screenHeight, 1000/FPS);
    }

    Track track = new Track();
    Player player = new Player();

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        SaxionApp.clear();

        track.draw();
        player.draw();
    }

    @Override
    public void keyboardEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.isKeyPressed()) {
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_W || keyboardEvent.getKeyCode() == 38) {
                player.upPressed = true;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_S || keyboardEvent.getKeyCode() == 40) {
                player.downPressed = true;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_A || keyboardEvent.getKeyCode() == 37) {
                player.leftPressed = true;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_D || keyboardEvent.getKeyCode() == 39) {
                player.rightPressed = true;
            }
        }

        if (!keyboardEvent.isKeyPressed()) {
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_W || keyboardEvent.getKeyCode() == 38) {
                player.upPressed = false;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_S || keyboardEvent.getKeyCode() == 40) {
                player.downPressed = false;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_A || keyboardEvent.getKeyCode() == 37) {
                player.leftPressed = false;
            }
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_D || keyboardEvent.getKeyCode() == 39) {
                player.rightPressed = false;
            }
        }
    }

    @Override
    public void mouseEvent(MouseEvent mouseEvent) {

    }
}