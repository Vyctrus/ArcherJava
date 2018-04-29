/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.jsfml.system.*;
import java.util.Random;
/**
 *
 * @author Andrzej
 */
public class Bot {
        public Bot(int playerPosition, float dificultyLevelPlus, float dificultyLevelMinus)
        {
	//shoot.x = 1000;
	//shoot.y = 100;
	multPlus = dificultyLevelPlus;
	multMinus = dificultyLevelMinus;
	xPosition = playerPosition;
        myRand=new Random();
        myRand.setSeed(System.currentTimeMillis());
        }
        //Date mydate=new Date();
	public void aim()
        {
            if (isItFirst) {
                    //prev.x = myRand.nextInt() % 652;		// tutaj tak sam jak ponizej	UWAGA chwilowo w Arrow cpp ograniczenie jest do winsize*0.3 wynosi 652.8
                    prev=new Vector2i(myRand.nextInt()% 652,myRand.nextInt()%367);
                    //prev.y = rand() % 367;		//wartosc startowa bedzie ustalana od rng UWAGA w y jest to 367.2 *z tymi parametrami komputer strzela z maksymaln¹ sila luku
                    //shoot.x = prev.x;
                    //shoot.y = prev.y;
                    shoot=new Vector2i(prev.x,prev.y);
                    isItFirst = false;
                    isItSecond = true;
            }
            else {
                    if (goodAim) {
                            //shoot.x=shoot.x;
                            //shoot.y=shoot.y;
                    }
                    else {
                            if (isItSecond) {
                                    xPrev = xLastArrow;
                                    //xPrev= getArrow x to przy niszczeniu strzly
                                    if (xPrev < xPosition) {	//strzala jest za graczem
                                            //next.x = prev.x - step;
                                            //next.y = prev.y - step;
                                            next= new Vector2i(prev.x-step,prev.y-step);
                                    }
                                    else {	//strzala jest przed graczem
                                            next= new Vector2i(prev.x+step,prev.y+step);
                                    }
                                    shoot=new Vector2i(next.x,next.y);
                                    isItSecond = false;
                            }
                            else {	//tutaj powinnismy miec juz dwa oddane strzaly prev i next
                                    xNext = xLastArrow;
                                    if (xPrev < xPosition && xNext < xPosition) {	//oba sa po lewej od gracza
                                            step *= multPlus;//tu bylo 1.5
                                            prev=new Vector2i(next.x,next.y);
                                            xPrev = xNext;
                                            next=new Vector2i(next.x-step,next.y-step);
                                            shoot=next;
                                    }
                                    else {
                                            if (xPrev<xPosition && xNext > xPosition) {	// sytuacja   prev Player next
                                                    step *= multMinus;//tu bylo 0.5
                                                    prev=new Vector2i(next.x,next.y);
                                                    xPrev = xNext;
                                                    next=new Vector2i(next.x+step,next.y+step);
                                                    shoot=next;
                                            }
                                            else {
                                                    if (xPrev > xPosition && xNext > xPosition) {	// sytuacja Player prev next
                                                            step *= multPlus;
                                                            prev=new Vector2i(next.x,next.y);
                                                            xPrev = xNext;
                                                            next=new Vector2i(next.x+step,next.y+step);
                                                            shoot=next;
                                                    }
                                                    else {
                                                            if (xPrev > xPosition && xNext < xPosition) {	// sytuacja next Player prev
                                                                    step *= multMinus;
                                                                    prev= new Vector2i(next.x,next.y);
                                                                    xPrev = xNext;
                                                                    next=new Vector2i(next.x-step,next.y-step);
                                                                    shoot=next;
                                                            }
                                                            else {
                                                                    ///w sumie nawet nie wiem jak mogloby sie to zdarzyc ale nalezaloby walnac exception czy cos
                                                                    System.out.println("Error\n");
                                                            }
                                                    }
                                            }

                                    }

                            }

                    }

            }

        }

	public boolean getwaiting()
        {
	return waiting;
        }
	public void setwaiting(boolean waiting)
        {
	this.waiting = waiting;
        }
	public boolean getgoodAim()
        {
	return goodAim;
        }
	public void setgoodAim(boolean goodAim)
        {
	this.goodAim = goodAim;
        }
	public boolean getcheetWindOff()
        {
	return cheetWindOff;
        }
	public void setcheetWindOff(boolean cheetWindOff)
        {
	this.cheetWindOff = cheetWindOff;
        }

	public Vector2i getshoot()
        {
	return shoot;
        }
	public void setshoot(Vector2i shoot)
        {
	this.shoot = shoot;
        }

	public int getxLastArrow()
        {
	return xLastArrow;
        }
	public void setxLastArrow(int xLastArrow)
        {
	this.xLastArrow = xLastArrow;
        }
        Random myRand;
        Vector2i shoot;
	boolean waiting = true;
	boolean isItFirst = true;
	boolean isItSecond = false;
	boolean cheetWindOff = false; //czy wiatr dziala na strzaly bota
	boolean goodAim = false;   // informuje bota czy trafil
	int step = 100;		// domyslna wartosc zmieniania sily strzalu bota po nietrafionym strzale
	//mnozniki dla poziomow trudnosci
	float multPlus;     //1.1 dla latwego, 1.5 dla normalnego, 2.0 dla trudnego
	float multMinus;    // 0.9 dla latwego, 0.5 dla normalnego, 0.4 dla trudnego

	int xPosition;	    //przekazywana w konstruktorze pozycja gracza
	int xLastArrow;     // pozycja ostatniej strzaly

	Vector2i prev;  //poprzedni strzal-sila
	int xPrev;          //lokalizacja poprzedniego strzalu
	Vector2i next;  //kolejny strzal-sila
	int xNext;			//lokalizacja aktualnego strzalu
}
