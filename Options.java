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
public class Options extends Menu {
    int selectedItemIndex;
	int dificultyLevelIndex;//okresla jaki jest wybrany poziom trudnosci
	Font myFont;  
	final int NUMBER_OF_OPTIONS_ITEMS = 9;
	Text[] optionsTexts=new Text[NUMBER_OF_OPTIONS_ITEMS];
    
    
	public Options(RenderWindow myWindow,DataOfOptions doo)throws IOException
        {
            selectedItemIndex = 0;
            dificultyLevelIndex = 1;
            myFont=new Font();
            myFont.loadFromFile(Paths.get("snap.ttf"));
            
            for (int i = 0; i < NUMBER_OF_OPTIONS_ITEMS; i++)
            {
                    optionsTexts[i]=new Text();
                    optionsTexts[i].setFont(myFont);
                    optionsTexts[i].setPosition(new Vector2f(myWindow.getSize().x*(float)0.2, myWindow.getSize().y / (NUMBER_OF_OPTIONS_ITEMS + 1)*(i + 1)));
                    optionsTexts[i].setCharacterSize(50);
            }

            optionsTexts[0].setColor(Color.RED);
            optionsTexts[0].setString("Muzyka");

            optionsTexts[1].setColor(Color.WHITE);
            optionsTexts[1].setString("Wiatr");

            optionsTexts[2].setColor(Color.WHITE);
            optionsTexts[2].setString("Poziom trudnosci");

            optionsTexts[3].setColor(Color.WHITE);
            optionsTexts[3].setString("Powrot");

            optionsTexts[4].setColor(Color.WHITE);
            if(doo.getuseMusic())
                optionsTexts[4].setString("ON");
            else
                optionsTexts[4].setString("OFF");
            optionsTexts[4].setPosition(new Vector2f(myWindow.getSize().x*(float)0.6, myWindow.getSize().y / (NUMBER_OF_OPTIONS_ITEMS + 1)*(0 + 1)));

            optionsTexts[5].setColor(Color.WHITE);
            if(doo.getuseWind())
                optionsTexts[5].setString("ON");
            else
                optionsTexts[5].setString("OFF");
            optionsTexts[5].setPosition(new Vector2f(myWindow.getSize().x*(float)0.6, myWindow.getSize().y / (NUMBER_OF_OPTIONS_ITEMS + 1)*(1 + 1)));
            //nowe znaczniki 3 stopniowego poziomu trudnosci
            optionsTexts[6].setColor(Color.WHITE);
            optionsTexts[6].setString("1");
            optionsTexts[6].setCharacterSize(50);
            optionsTexts[6].setPosition(new Vector2f(myWindow.getSize().x*(float)0.6, myWindow.getSize().y / (NUMBER_OF_OPTIONS_ITEMS + 1)*(2 + 1)));

            optionsTexts[7].setColor(Color.RED);
            optionsTexts[7].setString("2");
            optionsTexts[7].setCharacterSize(65);
            optionsTexts[7].setPosition(new Vector2f(myWindow.getSize().x*(float)0.64, myWindow.getSize().y / (NUMBER_OF_OPTIONS_ITEMS + 1)*(2 + 1)));

            optionsTexts[8].setColor(Color.WHITE);
            optionsTexts[8].setString("3");
            optionsTexts[8].setCharacterSize(80);
            optionsTexts[8].setPosition(new Vector2f(myWindow.getSize().x*(float)0.69, myWindow.getSize().y / (NUMBER_OF_OPTIONS_ITEMS + 1)*(2 + 1)));
        }
        @Override
	public Menu Listening(Event event, RenderWindow myWindow, DataOfOptions doo, Camera myCamera) throws IOException {
            Menu wskTemporary; //wskaznik pomocniczy do zamiany wartosci Present i Alter ze soba
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
                                    System.out.println("Muzyka\n");
                                    if ("ON".equals(optionsTexts[4].getString())) {
                                            optionsTexts[4].setString("OFF");
                                            doo.changeMusic();
                                    }
                                    else {
                                            optionsTexts[4].setString("ON");
                                            doo.changeMusic();
                                    }
                                    break;
                            case 1:
                            {
                                    System.out.println("Wiatr\n");
                                    if ("ON".equals(optionsTexts[5].getString())) {
                                            optionsTexts[5].setString("OFF");
                                            doo.changeWind();
                                    }
                                    else {
                                            optionsTexts[5].setString("ON");
                                            doo.changeWind();
                                    }
                                    break;
                            }
                            case 2:
                            {
                                    System.out.println("Poziom trudnosci\n");
                                    if (dificultyLevelIndex < 2) {
                                            optionsTexts[6 + dificultyLevelIndex].setColor(Color.WHITE);
                                            dificultyLevelIndex++;
                                            optionsTexts[6 + dificultyLevelIndex].setColor(Color.RED);
                                    }
                                    else {
                                            optionsTexts[6 + dificultyLevelIndex].setColor(Color.WHITE);
                                            dificultyLevelIndex = 0;
                                            optionsTexts[6 + dificultyLevelIndex].setColor(Color.RED);
                                    }
                                    if (dificultyLevelIndex == 0)
                                            doo.changeDificultyLevel((float)1.1, (float)0.9);
                                    if (dificultyLevelIndex == 1)
                                            doo.changeDificultyLevel((float)1.5, (float)0.5);
                                    if (dificultyLevelIndex == 2)
                                            doo.changeDificultyLevel((float)2.0, (float)0.4);
                                    break;
                            }//dodane graficzne wyswietlenie poziomu trudnosci

                            case 3:
                            {
                                    System.out.println("Powrot\n");
                                    return new MainMenu(myWindow);
                                   // wskTemporary = ptrPresent;//zamiana wartosci wskaznikow, return juz nie jest potrzebny
                                   // ptrPresent = ptrAlternative;
                                   // ptrAlternative = wskTemporary;
                                   // break;
                            }
                            }
                            break;
                    }
                    break;
            default:
                    break;
            }
            return this;
        }
        @Override
	public void MoveUp()
        {
            if (selectedItemIndex - 1 >= 0)
            {
                    optionsTexts[selectedItemIndex].setColor(Color.WHITE);
                    selectedItemIndex--;
                    optionsTexts[selectedItemIndex].setColor(Color.RED);
            }
        }
        @Override
	public void MoveDown()
        {
            if (selectedItemIndex + 1 < NUMBER_OF_OPTIONS_ITEMS - 5)
            {
                    optionsTexts[selectedItemIndex].setColor(Color.WHITE);
                    selectedItemIndex++;
                    optionsTexts[selectedItemIndex].setColor(Color.RED);
            }
        }
        
        @Override
	public void Display(RenderWindow myWindow)
        {
            for (int i = 0; i < NUMBER_OF_OPTIONS_ITEMS; i++)
            {
                    myWindow.draw(optionsTexts[i]);
            }
        }
        @Override
	public int GetPressedItem() { return selectedItemIndex; }
        
}
