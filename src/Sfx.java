import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.KeyboardEvent;


public class Sfx {

    public static void Sounds() {

    }
    public static void backgroundsound(){
        SaxionApp.playSound("resource/geluid1.wav");
    }
    public static void toeter(KeyboardEvent KeyboardEvent){
        if(KeyboardEvent.isKeyPressed()){
            //controle spatie
            if(KeyboardEvent.getKeyCode() == KeyboardEvent.VK_SPACE){
                //toeter sound.
                SaxionApp.playSound("resource/Goofy1.wav");
            }


        }



        }
    public static void remmen(KeyboardEvent KeyboardEvent){
        if(KeyboardEvent.isKeyPressed()){
            if(KeyboardEvent.getKeyCode() == KeyboardEvent.VK_S){
                SaxionApp.playSound("resource/brakingsound.wav");
            }
            }


        }





    }









