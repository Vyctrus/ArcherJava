/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.jsfml.graphics.*;
import java.nio.file.Paths;
import java.io.IOException;
import org.jsfml.system.*;
import org.jsfml.window.event.Event;
/**
 *
 * @author Andrzej
 */
public abstract class Menu {
	public abstract void MoveUp();
	public abstract void MoveDown();
	public abstract void Display(RenderWindow myWindow);
	public abstract Menu Listening(Event event, RenderWindow myWindow, DataOfOptions doo,Camera myCamera) throws IOException;//dzieki dodaniu referencji mozna zmieniac ich wartosci oraz nie trzeba wysylac wskaznika do wskaznika tylko sam wskaznik
	public int GetPressedItem() { return selectedItemIndex; }

	int selectedItemIndex;
	Font myFont;
	Text[] endMessage=new Text[2];
}
