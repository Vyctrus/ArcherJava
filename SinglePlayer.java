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
import static org.jsfml.graphics.PrimitiveType.LINES;
import static org.jsfml.graphics.PrimitiveType.LINE_STRIP;
import org.jsfml.window.event.Event;
/**
 *
 * @author Andrzej
 */
public class SinglePlayer extends GameMode {
    	Bot myBot;
	Time botWaits;
        
        SinglePlayer(RenderWindow myWindow, DataOfOptions doo, Camera myCamera)throws IOException
        {
            endMessageTab[0]=new Text();
            endMessageTab[1]=new Text();
            endMessageTab[0].setString("Wygrales !!!");
            endMessageTab[1].setString("Przegrales");
           // liveArrow.setisDead(true);
            player1 = new Player(45, myWindow.getSize().y - 300);
            player2 = new Player(myWindow.getSize().x - 45, myWindow.getSize().y - 300);
            player2.playerFlip();
            windPosition=new Vector2f(player1.getPosition().x + 150,player1.getPosition().y - 150);
            players.add(player1);
            players.add(player2);
            myWind = new Wind(windPosition, doo);
            mySounds = new SoundandMusic(myClock, doo);

            myBot = new Bot((int)player1.getPosition().x, doo.getDificultyLevelPlus(), doo.getDificultyLevelMinus());

            view1 = new View(new FloatRect(new Vector2f(-100, 480), new Vector2f(1000, 1000)));
            view1.setCenter(players.get(sequence).getPosition());
            myWindow.setView(view1);

            myFont=new Font();
            myFont.loadFromFile(Paths.get("snap.ttf"));

            hpTexts[0]=new Text();
            hpTexts[1]=new Text();
            hpTexts[2]=new Text();

            hpTexts[0].setFont(myFont);
            hpTexts[0].setPosition(player1.getPosition().x, player1.getPosition().y - 200);///140
            hpTexts[0].setCharacterSize(30);
            hpTexts[0].setColor(Color.WHITE);
            hpTexts[0].setString(Integer.toString(player1.getplayerHP()));

            hpTexts[1].setFont(myFont);
            hpTexts[1].setPosition(player2.getPosition().x, player2.getPosition().y - 200);
            hpTexts[1].setCharacterSize(30);
            hpTexts[1].setColor(Color.WHITE);
            hpTexts[1].setString(Integer.toString(player2.getplayerHP()));

            hpTexts[2].setFont(myFont);
            hpTexts[2].setPosition(windPosition.x, windPosition.y);
            hpTexts[2].setCharacterSize(30);
            hpTexts[2].setColor(Color.WHITE);
            wordOfWind.append("X= ");
            wordOfWind.append(myWind.getv2iwind().x);
            wordOfWind.append(" Y= ");
            wordOfWind.append(myWind.getv2iwind().y);
            hpTexts[2].setString(wordOfWind.toString());
        }
	public void Run(RenderWindow myWindow, DataOfOptions doo, Camera myCamera)
        {   try{
            mySounds.musicPlay();
            Background myBackground=new Background(myWindow);
            while (myWindow.isOpen())
            {
                    //////////////////////////////////////////////////////////////////////Event handling
                    Event event;
                    event=myWindow.pollEvent();
                    while (event!=null)
                    {
                            switch (event.type)
                            {
                            case CLOSED:
                                    myWindow.close();
                                    break;
                            case KEY_PRESSED:
                                    if (event.asKeyEvent().key==Keyboard.Key.ESCAPE)
                                            return;
                            case MOUSE_BUTTON_RELEASED:
                                    if (event.asMouseButtonEvent().button==Mouse.Button.LEFT) {
                                            aimLineChecker = false;
                                            if (sequence == 0 && letShoot == true) {
                                                    liveArrow =new Arrow(aimLineBegin, aimLineEnd, player1.getPosition(), myWindow, myClock);
                                                    sequence = 1;
                                                    letShoot = false;
                                                    mySounds.setfirstTime(true);
                                            }
                                    }
                                    break;
                            case MOUSE_BUTTON_PRESSED:
                                    if (event.asMouseButtonEvent().button==Mouse.Button.LEFT) {
                                            Vector2i pixelPos = Mouse.getPosition(myWindow);
                                            Vector2f worldPos = myWindow.mapPixelToCoords(pixelPos);
                                            aimLineBegin = new Vector2i(worldPos);
                                            aimLineChecker = true;
                                    }
                                    break;
                            default:
                                    break;
                            }
                    }
                    ////////////////////////////////////////////////bot
                    if (sequence == 1 && letShoot == true && !myCamera.getcameraMoveY() && !myCamera.getcameraMoveX()) {
                            myTime = myClock.getElapsedTime();
                            if (myBot.getwaiting()) {
                                    //botWaits = myTime;
                                    //botWaits = botWaits + Time.getSeconds((float)2);
                                    botWaits=botWaits.add(myTime,Time.getSeconds((float)2));
                                    myBot.setwaiting(false);
                            }
                            if (myTime.asSeconds() >= botWaits.asSeconds()) {
                                    myBot.setwaiting(true);
                                    aimLineBegin = new Vector2i(0, 0);
                                    if (myBot.getcheetWindOff()) {	//mozna wylaczyc dzialanie waitru na strzaly bota
                                            myWind.setmyuseWind(false);
                                    }
                                    myBot.aim();
                                    aimLineEnd = myBot.getshoot();
                                    liveArrow = new Arrow(aimLineBegin, aimLineEnd, player2.getPosition(), myWindow, myClock);
                                    sequence = 0;
                                    letShoot = false;
                                    mySounds.setfirstTime(true);
                            }
                    }
                    //////////////////////////////////////////////////////////////////////Window update
                    if (!liveArrow.getisDead()) {
                            if (sequence == 0 && liveArrow.isInterecting(player1)) {
                                    player1.decreaseHP();
                                    if (player1.getplayerHP() < 1) {
                                            gameOver(myWindow, myBackground, false);
                                            return;
                                    }
                                    myBot.setgoodAim(true);
                                    mySounds.painUpdate();
                                    hpTexts[0].setColor(Color.RED);
                                    hpTexts[0].setString(Integer.toString(player1.getplayerHP()));
                            }
                            if (sequence == 1 && liveArrow.isInterecting(player2)) {
                                    player2.decreaseHP();
                                    if (player2.getplayerHP() < 1) {
                                            gameOver(myWindow, myBackground, true);
                                            return;
                                    }
                                    mySounds.painUpdate();
                                    hpTexts[1].setColor(Color.RED);
                                    hpTexts[1].setString(Integer.toString(player2.getplayerHP()));
                            }
                            liveArrow.update(myWindow, view1, myClock, myWind);

                            mySounds.flyingUpdate(myClock);

                            if (liveArrow.getisHit())
                            {
                                    if (!liveArrow.getisDead()) {
                                            deadarrows.add(new DeadArrow(liveArrow));
                                            liveArrow.setisDead(true);
                                            if (sequence == 0) {
                                                    myBot.setxLastArrow((int)liveArrow.getmysprite().getPosition().x);//tutaj bot traci dokladnosc z float do int~~~~niezamierzone "unaturalnienie" bota
                                                    if (!liveArrow.isInterecting(player1)) {	//to ma zapewnic ze bot sobie poradzi po trafieniu a potem nietrafieniu
                                                            myBot.setgoodAim(false);
                                                    }
                                            }
                                            letShoot = true;
                                            mySounds.hitUpdate();
                                    }
                                    if (myBot.getcheetWindOff()) {	//wlaczam wiatr kiedy tylko chce
                                            myWind.setmyuseWind(true);
                                    }
                                    myCamera.start();
                            }
                    }
                    //tworzenie i rysowanie lini
                    Vector2i pixelPos = Mouse.getPosition(myWindow);
                    Vector2f worldPos = myWindow.mapPixelToCoords(pixelPos);
                    aimLineEnd = new Vector2i(worldPos);
                    
                    VertexArray lines=new VertexArray(PrimitiveType.LINE_STRIP);
                    
                    myCamera.update(view1, players, sequence, myWindow);
                    //////////////////////////////////////////////////wind update
                    myWind.update();
                    wordOfWind.append("X= ");
                    wordOfWind.append(myWind.getv2iwind().x);
                    wordOfWind.append(" Y= ");
                    wordOfWind.append(myWind.getv2iwind().y);
                    hpTexts[2].setString(wordOfWind.toString());
                    ///////////////////////////////////////////////////////////////////////////////////Window Render
                    myWindow.clear();
                    myBackground.displayGraphics(myWindow, players, liveArrow, deadarrows);
                    if (aimLineChecker == true) {
                            lines.add(0,new Vertex(new Vector2f(aimLineBegin.x, aimLineBegin.y)));
                            lines.add(1,new Vertex(new Vector2f(aimLineEnd.x, aimLineEnd.y)));
                            myWindow.draw(lines);
                    }
                    myWindow.setView(view1);
                    myWindow.draw(myWind);
                    myWindow.draw(hpTexts[0]);
                    myWindow.draw(hpTexts[1]);
                    if (myWind.getmyuseWind()) {
                            myWindow.draw(hpTexts[2]);
                    }
                    myWindow.display();
            }
            
        }catch(IOException e){}
        }
}
