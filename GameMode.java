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
	StringBuilder wordOfWind=new StringBuilder();//tekst ktory przekazujemy tablicy do wyswietlenia
	Wind myWind;
	SoundandMusic mySounds;
	View view1;
	Player player1;
	Player player2;
	Clock myClock=new Clock();
	Time myTime;
	ArrayList<Player> players;
	Arrow liveArrow=null;
	ArrayList<DeadArrow> deadarrows;
	int sequence = 0;
	boolean aimLineChecker = false;
	Vector2f windPosition;
	Vector2i aimLineBegin, aimLineEnd;
	boolean letShoot = true;
	Text endMessage=new Text();
	Text endButton=new Text();

	GameMode(RenderWindow myWindow, DataOfOptions doo, Camera myCamera) throws IOException {
        endMessageTab[0]=new Text();
        endMessageTab[1]=new Text();

        player1 = new Player(45, myWindow.getSize().y - 300);
        player2 = new Player(myWindow.getSize().x - 45, myWindow.getSize().y - 300);
        player2.playerFlip();

        windPosition=new Vector2f(player1.getPosition().x+150,player1.getPosition().y - 150);
        players=new ArrayList();//#uwaga
        deadarrows=new ArrayList();//#uwaga

        players.add(player1);
        players.add(player2);
        myWind = new Wind(windPosition, doo);
        mySounds = new SoundandMusic(myClock, doo);

        view1 = new View(new FloatRect(new Vector2f(-100, 480), new Vector2f(1000, 1000)));
        view1.setCenter(players.get(sequence).getPosition());
        myWindow.setView(view1);

        myFont=new Font();
        myFont.loadFromFile(Paths.get("snap.ttf"));

        hpTexts[0]=new Text();
        hpTexts[1]=new Text();
        hpTexts[2]=new Text();

        hpTexts[0].setFont(myFont);
        hpTexts[0].setPosition(player1.getPosition().x, player1.getPosition().y - 250);///140
        hpTexts[0].setCharacterSize(30);
        hpTexts[0].setColor(Color.WHITE);
        hpTexts[0].setString(Integer.toString(player1.getplayerHP()));

        hpTexts[1].setFont(myFont);
        hpTexts[1].setPosition(player2.getPosition().x, player2.getPosition().y - 250);
        hpTexts[1].setCharacterSize(30);
        hpTexts[1].setColor(Color.WHITE);
        hpTexts[1].setString(Integer.toString(player2.getplayerHP()));

        hpTexts[2].setFont(myFont);
        hpTexts[2].setPosition(windPosition.x, windPosition.y-250);
        hpTexts[2].setCharacterSize(30);
        hpTexts[2].setColor(Color.WHITE);
        wordOfWind=new StringBuilder();
        wordOfWind.append("X= ");
        wordOfWind.append(myWind.getv2iwind().x);
        wordOfWind.append(" Y= ");
        wordOfWind.append(myWind.getv2iwind().y);
        hpTexts[2].setString(wordOfWind.toString());
    }

	public abstract void Run(RenderWindow myWindow, DataOfOptions doo, Camera myCamera);
        
	public void gameOver(RenderWindow myWindow, Background myBackground, boolean playerOneWins) throws IOException
        {
            myFont=new Font();
            myFont.loadFromFile(Paths.get("snap.ttf"));
            myWindow.setView(new View(new Vector2f(960,540), new Vector2f(1920, 1080)));
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
                            event=null;
                    }
                    //if(event.type==Event.Type.CLOSED){};
                    //////////Window Render
                    myWindow.clear();
                    myBackground.displayBackground(myWindow);
                    myWindow.draw(endMessage);
                    myWindow.draw(endButton);
                    myWindow.display();
            }
        }
}
