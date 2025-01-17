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
    public static void remmen(KeyboardEvent keyboardEvent){
        if(keyboardEvent.isKeyPressed()){
            if(keyboardEvent.getKeyCode() == KeyboardEvent.VK_S || keyboardEvent.getKeyCode() == KeyboardEvent.VK_DOWN) {
                SaxionApp.playSound("resource/sfx/brakingsound.wav");
            }
        }
    }

    public static void tegenwind(KeyboardEvent keyboardEvent){
        if(keyboardEvent.isKeyPressed()){
            if(keyboardEvent.getKeyCode() == keyboardEvent.VK_W || keyboardEvent.getKeyCode() == keyboardEvent.VK_UP){
                SaxionApp.playSound("resource/sfx/wind.wav");
            }
        }
    }


    public static void powerupPickup() {
        SaxionApp.playSound("resource/Sfx/powerup pickup.wav");
    }

    public static void coinPickup() {
        SaxionApp.playSound("resource/Sfx/coin pickup.wav");
    }

    public static void fuelPickup() {
        SaxionApp.playSound("resource/Sfx/fuel pickup.wav");
    }
}









