/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.*;
import org.jsfml.audio.Music;
import org.jsfml.window.VideoMode;
import static org.jsfml.window.VideoMode.getDesktopMode;
import org.jsfml.window.event.Event;
import java.io.IOException;
import java.util.*;
/**
 *
 * @author Andrzej
 */
public class ArcherMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        RenderWindow window= new RenderWindow(getDesktopMode(),"Archers 1.0");
	window.setFramerateLimit(60);
	GameMode ptrGameMode;//referencja? do zmieniania trybu gry
	//Background background(window);
        Background background=new Background(window);
        MainMenu myMenu=new MainMenu(window);
        Options myOptions=new Options(window);
	Menu ptrPresent;//dwa wskazniki ktore sluza do przenoszenia sie miedzy menu gry a opcjami zaczynamy od menu gry wiec ptrPresent = myMenu
        ptrPresent = myMenu;
	Menu ptrAlternative = myOptions;
	DataOfOptions doo;
	Camera myCamera;

	while (window.isOpen())
	{
		/////////////////////////////////////////////////////////Event Handling
		Event event;
                event = window.pollEvent();
		while (event!=null)
		{//zmiany w Menu.h MainMenu cpp i h Options cpp i h
			ptrPresent.Listening(event, window, ptrPresent, ptrAlternative,ptrGameMode, doo, myCamera);//funkcja juz nic nie zwraca bo przyjmuje referencje do wskaznikow, jezli chce sie przejsc do innego menu to wskanziki zamieniaja sie wartosciami
		}
		//////////////////////////////////////////////////////////////Update


		/////////////////////////////////////////////////////////////Window Render
		window.clear();

		background.displayBackground(window);
		ptrPresent.Display(window);

		window.display();
	}
	
    }
    
}
