
//package javasfmltest;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.Keyboard;
import static org.jsfml.window.VideoMode.getDesktopMode;
 
public class Javasfmltest {
    
    public static void main(String[] args) throws IOException {
        RenderWindow window= new RenderWindow(getDesktopMode(),"Archers 6.9");
        window.setFramerateLimit(60);

        Camera myCamera=new Camera();
        DataOfOptions doo=new DataOfOptions();
        Background bck=new Background(window);
        Menu ptr=new MainMenu(window);
        Menu ptrAlt=new Options(window);
        Event myEvent;
        //GameMode ptrGM=new SinglePlayer(window,doo,myCamera); TA JEBANA KURWA SZMATA PIZDA DZIWKA
        while(window.isOpen()){
            myEvent=window.pollEvent();
            while(myEvent!=null){
                ptr=ptr.Listening(myEvent,window,doo,myCamera);
                myEvent=null; //CO to kurwa robi xdddddddddddddddddddddd
            }
            //window.draw(mySprite);
            window.clear();
            bck.displayBackground(window);
            ptr.Display(window);
            //window.draw(mySprite);
            ///////window.draw(tab.get(0));
            window.display();
        }
    }
    
}