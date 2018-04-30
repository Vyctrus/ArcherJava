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
import java.nio.file.Paths;
import java.io.IOException;
import org.jsfml.system.*;
import org.jsfml.window.event.Event;
public class MainMenu extends Menu {
        final int NUMBER_OF_MENU_ITEMS = 4;
        int selectedItemIndex;
	Font myFont;
	Text[] menuTexts= new Text[NUMBER_OF_MENU_ITEMS];
	public MainMenu(RenderWindow myWindow) throws IOException
            {
                myFont=new Font();
                selectedItemIndex = 0;
                myFont.loadFromFile(Paths.get("snap.ttf"));
                
                for (int i = 0; i < NUMBER_OF_MENU_ITEMS; i++)
                {
                        menuTexts[i]=new Text();
                        menuTexts[i].setFont(myFont);
                        menuTexts[i].setPosition(new Vector2f(myWindow.getSize().x*(float)0.4, myWindow.getSize().y / (NUMBER_OF_MENU_ITEMS + 1)*(i + (float)0.5)));
                        menuTexts[i].setCharacterSize(50);
                        menuTexts[i].setColor(Color.BLACK); 
                }
                menuTexts[0].setColor(Color.RED);
                menuTexts[0].setString(" Jeden gracz");

                menuTexts[1].setColor(Color.WHITE);
                menuTexts[1].setString("Dwóch graczy");

                menuTexts[2].setColor(Color.WHITE);
                menuTexts[2].setString("Ustawienia");

                menuTexts[3].setColor(Color.WHITE);
                menuTexts[3].setString("  Wyjscie");
            }
	public void Listening(Event event, RenderWindow myWindow, Menu ptrPresent, Menu ptrAlternative, GameMode ptrGameMode, DataOfOptions doo, Camera myCamera)
        {   try{
            Menu wskTemporary;//wskaznik pomocniczy do zamiany wartosci Present i Alter ze soba
            switch (event.type)
            {
            case CLOSED:
                    myWindow.close();
                    break;
            case KEY_PRESSED:
                    switch (event.asKeyEvent().key)
                    {
                    case UP:
                            this.MoveUp();
                            break;
                    case DOWN:
                            this.MoveDown();
                            break;
                    case RETURN:
                            switch (GetPressedItem())
                            {
                            case 0:
                            {
                                    System.out.println("Jeden gracz\n");
                                    ptrGameMode = new SinglePlayer(myWindow, doo, myCamera);
                                    ptrGameMode.Run(myWindow, doo, myCamera);
                                    //delete ptrGameMode;
                                    ptrGameMode=null;
                                    myWindow.setView(new View(new Vector2f(960,540),new Vector2f(1920, 1080)));
                                    break;
                            }
                            case 1:
                            {
                                    System.out.println("Dwoch graczy\n");                                   
                                    ptrGameMode = new MultiPlayer(myWindow,doo,myCamera);
                                    ptrGameMode.Run(myWindow,doo,myCamera);
                                    //delete ptrGameMode;
                                    ptrGameMode=null;
                                    myWindow.setView(new View(new Vector2f(960, 540),new Vector2f(1920, 1080)));
                                    break;
                            }
                            case 2:
                            {
                                    System.out.println("Ustawienia\n");
                                    wskTemporary = ptrPresent;//zamiana wartosci wskaznikow, return juz nie jest potrzebny
                                    ptrPresent = ptrAlternative;
                                    ptrAlternative = wskTemporary;
                                    break;
                            }
                            case 3:
                                    myWindow.close();
                                    break;
                            }
                            break;
                    }
                    break;
            default:
                    break;
            }   }catch(IOException e){}
        }
	public void MoveUp()
        {
            if (selectedItemIndex - 1 >= 0)
            {
                    menuTexts[selectedItemIndex].setColor(Color.WHITE);
                    selectedItemIndex--;
                    menuTexts[selectedItemIndex].setColor(Color.RED);
            }
        }
	public void MoveDown()
        {
            if (selectedItemIndex + 1 < NUMBER_OF_MENU_ITEMS)
            {
                    menuTexts[selectedItemIndex].setColor(Color.WHITE);
                    selectedItemIndex++;
                    menuTexts[selectedItemIndex].setColor(Color.RED);
            }
        }
	public void Display(RenderWindow myWindow)
        {
            for (int i = 0; i < NUMBER_OF_MENU_ITEMS; i++)
            {
                    myWindow.draw(menuTexts[i]);
            }
        }
	public int GetPressedItem() { return selectedItemIndex; }

	
}
