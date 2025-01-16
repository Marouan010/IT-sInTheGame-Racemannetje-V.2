import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.KeyboardEvent;


public class Sfx {

    public static void Sounds() {

    }

    public static void backgroundsound() {
        SaxionApp.playSound("resource/Sfx/geluid1.wav", true);
    }

    public static void toeter(KeyboardEvent KeyboardEvent) {
        if (KeyboardEvent.isKeyPressed()) {
            //controle spatie
            if (KeyboardEvent.getKeyCode() == KeyboardEvent.VK_SPACE) {
                //toeter sound.
                SaxionApp.playSound("resource/Sfx/Goofy1.wav");
            }


        }
    }

    public static void powerupPickup() {
        SaxionApp.playSound("resource/Sfx/powerup pickup.wav");
    }
}









