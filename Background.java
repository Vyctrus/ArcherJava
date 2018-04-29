/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andrzej
 */
import org.jsfml.graphics.*;
import java.util.*;
import java.nio.file.Paths;
import java.io.IOException;
public class Background {
    int spriteTabIterator;
    Texture forestTexture;
    Texture skyTexture;
    Texture grassTexture;
    Sprite mySprite;
    ArrayList<Sprite> tabSprite;
    //Vector<Sprite> smiec;
    
    //public
    //Background() = default;
    Background(RenderWindow myWindow) throws IOException
    {
	spriteTabIterator = 0;
	forestTexture.loadFromFile(Paths.get("forest.jpg"));
	skyTexture.loadFromFile(Paths.get("sky.jpg"));
	grassTexture.loadFromFile(Paths.get("grass.jpg"));

	for (int i = 0; i < 13; i++, spriteTabIterator++)
	{
		tabSprite.add(mySprite);
		tabSprite.get(spriteTabIterator).setTexture(forestTexture);
                tabSprite.get(spriteTabIterator).setPosition(-6*myWindow.getSize().x+i*myWindow.getSize().x,0);
	}
	for (int i = 0; i < 13; i++, spriteTabIterator++)
	{
		tabSprite.add(mySprite);
		tabSprite.get(spriteTabIterator).setTexture(skyTexture);
		tabSprite.get(spriteTabIterator).setPosition(-6 * (myWindow.getSize().x) + i *(myWindow.getSize().x), -(myWindow.getSize().y));
	}
	for (int i = 0; i < 13; i++, spriteTabIterator++)
	{
		tabSprite.add(mySprite);
		tabSprite.get(spriteTabIterator).setTexture(skyTexture);
		tabSprite.get(spriteTabIterator).setPosition(-6 *(myWindow.getSize().x) + i *(myWindow.getSize().x), -2 *(myWindow.getSize().y));
	}
	for (int i = 0; i < 13; i++, spriteTabIterator++)
	{
		tabSprite.add(mySprite);
		tabSprite.get(spriteTabIterator).setTexture(grassTexture);
		tabSprite.get(spriteTabIterator).setPosition(-6 * (myWindow.getSize().x) + i * (myWindow.getSize().x),(myWindow.getSize().y));
	}
    }
    //~Background() = default;
    void displayGraphics(RenderWindow myWindow, ArrayList<Player> players, Arrow liveArrow, ArrayList<DeadArrow> deadarrows)
    {
	for (int i = 0; i < 52; i++) {
		myWindow.draw(tabSprite.get(i));
	}
	for (int i = 0; i<players.size(); i++)
	{
		myWindow.draw(players.get(i));
	}
	if (!liveArrow.getisDead()) {
		myWindow.draw(liveArrow);
	}
	for (int i = 0; i < deadarrows.size(); i++) {
		myWindow.draw(deadarrows.get(i));
	}
    }
    void displayBackground(RenderWindow myWindow)
    {
	for (int i = 0; i < 52; i++)
	{
		myWindow.draw(tabSprite.get(i));
	}
    }
    
}
