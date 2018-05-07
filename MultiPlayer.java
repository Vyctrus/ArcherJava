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
public class MultiPlayer extends GameMode {
    public MultiPlayer (RenderWindow myWindow,DataOfOptions doo,Camera myCamera)throws IOException
    {
		endMessageTab[0]=new Text();
		endMessageTab[1]=new Text();
	endMessageTab[0].setString("Wygral gracz pierwszy");
	endMessageTab[1].setString("Wygral gracz drugi");

	myTime = myClock.getElapsedTime();
//	liveArrow.setisDead(true);
	player1 = new Player(45, myWindow.getSize().y - 300);
	player2 = new Player(myWindow.getSize().x - 45, myWindow.getSize().y - 300);
	player2.playerFlip();
	windPosition=new Vector2f(player1.getPosition().x+150,player1.getPosition().y - 150);
	players=new ArrayList();//#uwaga
		 deadarrows=new ArrayList();//#uwaga
	players.add(player1);
	players.add(player2);
	myWind=new Wind(windPosition, doo);
	mySounds = new SoundandMusic(myClock,doo);
	view1 = new View(new FloatRect(new Vector2f(-100, 480), new Vector2f(1000, 1000)));
	view1.setCenter(players.get(sequence).getPosition());
	myWindow.setView(view1);

	myFont=new Font();
	myFont.loadFromFile(Paths.get("snap.ttf"));

	hpTexts[0]=new Text();
	hpTexts[1]=new Text();
	hpTexts[2]=new Text();

	hpTexts[0].setFont(myFont);
	hpTexts[0].setPosition(player1.getPosition().x,player1.getPosition().y-250);
	hpTexts[0].setCharacterSize(30);
	
	hpTexts[0].setColor(Color.WHITE);
	hpTexts[0].setString(Integer.toString(player1.getplayerHP()));

	hpTexts[1].setFont(myFont);
	hpTexts[1].setPosition(player2.getPosition().x, player2.getPosition().y - 250);
	hpTexts[1].setCharacterSize(30);
	hpTexts[1].setColor(Color.WHITE);
	hpTexts[1].setString(Integer.toString(player2.getplayerHP()));

	hpTexts[2].setFont(myFont);
	hpTexts[2].setPosition(windPosition.x,windPosition.y-250);
	hpTexts[2].setCharacterSize(30);
	hpTexts[2].setColor(Color.WHITE);
        wordOfWind=new StringBuilder();
        wordOfWind.append("X= ");
        wordOfWind.append(myWind.getv2iwind().x);
        wordOfWind.append(" Y= ");
        wordOfWind.append(myWind.getv2iwind().y);
        hpTexts[2].setString(wordOfWind.toString());

    }

    @Override
    public void Run(RenderWindow myWindow,DataOfOptions doo,Camera myCamera)
    {
        Event event;
	mySounds.musicPlay();
        try{
	Background myBackground=new Background(myWindow);
        
        //Background myBackground(myWindow);
	while (myWindow.isOpen())
	{
		//////////////////////////////////////////////////////////////////////Event handling
		//Event event;
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
						liveArrow = new Arrow(aimLineBegin, aimLineEnd, player1.getPosition(), myWindow, myClock);
						sequence = 1;
						letShoot = false;
						mySounds.setfirstTime(true);
					}
					else if (letShoot == true) {
						liveArrow = new Arrow(aimLineBegin, aimLineEnd, player2.getPosition(), myWindow, myClock);
						sequence = 0;
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
                        event=null;
		}
		//////////////////////////////////////////////////////////////////////Window update
		if(liveArrow!=null) {
			if (!liveArrow.getisDead()) {
				//std::cout << "update" << std::endl;
				if (sequence == 0 && liveArrow.isInterecting(player1)) {
					player1.decreaseHP();
					if (player1.getplayerHP() < 1) {
						gameOver(myWindow, myBackground, false);
						return;
					}
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

				if (liveArrow.getisHit()) {
					if (!liveArrow.getisDead()) {
						deadarrows.add(new DeadArrow(liveArrow));
						liveArrow.setisDead(true);
						letShoot = true;
						mySounds.hitUpdate();
					}
					myCamera.start();
				}
			}
		}
		Vector2i pixelPos = Mouse.getPosition(myWindow);
		Vector2f worldPos = myWindow.mapPixelToCoords(pixelPos);
		aimLineEnd = new Vector2i(worldPos);
                
		/*VertexArray lines=new VertexArray(LINES);//(LINES, 2);   //tworzenie i rysowanie lini //or LINES
		lines(0).position = new Vector2f(aimLineBegin.x, aimLineBegin.y);
		lines[1].position = sf::Vector2f(aimLineEnd.x, aimLineEnd.y);*/ //#poprawa
                VertexArray lines=new VertexArray(PrimitiveType.LINE_STRIP);
                
		myCamera.update(view1, players, sequence, myWindow);

		if (myWind.getmyuseWind()) {
			myWind.update(sequence, player1.getPosition(), player2.getPosition());
                        wordOfWind=new StringBuilder();
                        wordOfWind.append("X= ");
                        wordOfWind.append(myWind.getv2iwind().x);
                        wordOfWind.append(" Y= ");
                        wordOfWind.append(myWind.getv2iwind().y);
                        hpTexts[2].setString(wordOfWind.toString());
			hpTexts[2].setPosition(myWind.getwindsprite().getPosition());
		}

		///////////////////////////////////////////////////////////////////////////////////Window Render
		myWindow.clear();
		myBackground.displayGraphics(myWindow, players, liveArrow, deadarrows);
		if (aimLineChecker) {
			//myWindow.draw(lines);//#poprawa
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
        
        finally {
                mySounds.musicStop();
            }
        
    }
}
