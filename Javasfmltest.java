
package javasfmltest;

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
        int x=0;
        Event myevent;
        Sprite mySprite=new Sprite();
        ArrayList<Sprite> tab;
        tab=new ArrayList();
        tab.add(mySprite);
        Texture forestTexture=new Texture();
	forestTexture.loadFromFile(Paths.get("forest.jpg"));
        //mySprite=new Sprite(forestTexture);
        mySprite.setTexture(forestTexture);
        //RenderWindow window(new getDesktopMode(), "Archers 1.0");
        RenderWindow window= new RenderWindow(getDesktopMode(),"Archers 6.9");
        window.setFramerateLimit(60);
        System.out.println(x);
        while(window.isOpen()){
            window.clear();
            //Event myevent;
            myevent=window.pollEvent();
            while(myevent!=null){
                switch(myevent.type){
                    case CLOSED:
                       window.close();
                       break;
                    case KEY_PRESSED:
                        if(myevent.asKeyEvent().key==Keyboard.Key.SPACE){
                           x++;
                           window.draw(tab.get(0));
                           
                        }
                        System.out.println(x);
                        break;
                    default:
                        break;

                }
                myevent=null; //CO to kurwa robi xdddddddddddddddddddddd
            }
            //window.draw(mySprite);
            //window.clear();
            //window.draw(mySprite);
            ///////window.draw(tab.get(0));
            window.display();
        }
        System.out.println(x);
    }
    
}
