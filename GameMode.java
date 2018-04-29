/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.jsfml.window.*;
import org.jsfml.system.*;
import org.jsfml.graphics.*;
import java.util.*;
import java.io.IOException;
import java.nio.file.Paths;
import org.jsfml.window.event.Event;
/**
 *
 * @author Andrzej
 */
public abstract class GameMode {
        Text endMessageTab[]=new Text[2];
	Font myFont;
	Text hpTexts[]=new Text[3];//HP 1 i 2 gracza + wartosci waitru w formie tekstu
	StringBuilder wordOfWind;//tekst ktory przekazujemy tablicy do wyswietlenia
	Wind myWind;
	SoundandMusic mySounds;
	View view1;
	Player player1;
	Player player2;
	Clock myClock;
	Time myTime;
	ArrayList<Player> players;
	Arrow liveArrow;
	ArrayList<DeadArrow> deadarrows;
	int sequence = 0;
	boolean aimLineChecker = false;
	Vector2f windPosition;
	Vector2i aimLineBegin, aimLineEnd;
	boolean letShoot = true;
        Text endMessage;
        Text endButton;
        //Event event()=new Event();
        
        
       /*@Override
        public void finalize() throws Throwable{
                System.out.println("Destruktor muzyki//gamemode\n");
                mySounds.musicStop();
                //super.finalize();
        }*/
        
	//GameMode(sf::RenderWindow& myWindow, DataOfOptions & doo, Camera & myCamera);
	public abstract void Run(RenderWindow myWindow, DataOfOptions doo, Camera myCamera);
        
	public void gameOver(RenderWindow myWindow, Background myBackground, boolean playerOneWins) throws IOException
        {
            //Font myFont;
            myFont.loadFromFile(Paths.get("snap.ttf"));
            myWindow.setView(new View(new Vector2f(960,540), new Vector2f(1920, 1080)));
            //Text endMessage;
            //Text endButton;
            endMessage.setFont(myFont);
            endButton.setFont(myFont);
            Event event;
            endMessage.setColor(Color.WHITE);
            //endMessage.setFillColor(Color::White);
            endMessage.setCharacterSize(80); 
            endMessage.setPosition(new Vector2f(myWindow.getSize().x*(float)0.3, myWindow.getSize().y*(float)0.1));

            endButton.setColor(Color.RED);
            endButton.setCharacterSize(40);
            endButton.setPosition(new Vector2f(myWindow.getSize().x*(float)0.4, myWindow.getSize().y*(float)0.8));
            endButton.setString("Powrot");

            if (playerOneWins) {
                    endMessage.setString(endMessageTab[0].getString());
            }
            else {
                    endMessage.setString(endMessageTab[1].getString());
            }

            while (true) {
                    //////////////////Event handling
                    event = myWindow.pollEvent();
                    while (event != null) {
                            if(event.type==Event.Type.KEY_PRESSED){
                                if(event.asKeyEvent().key==Keyboard.Key.RETURN){
                                    return;
                                }
                            }
                    }
                    if(event.type==Event.Type.CLOSED){};
                    //////////Window Render
                    myWindow.clear();
                    myBackground.displayBackground(myWindow);
                    myWindow.draw(endMessage);
                    myWindow.draw(endButton);
                    myWindow.display();
            }
        }
}
