

import org.jsfml.graphics.*;
//import org.jsfml.system.Vector2f;
//import org.jsfml.system.Vector2i;
import org.jsfml.system.*;
import java.nio.file.Paths;
import java.io.IOException;
import static java.lang.Math.*;
class Arrow extends BasicTransformable implements Drawable
{
//public:
    private boolean isHit;
    private boolean isDead;
    private Time timeOfRun;
    private Vector2f velocityZero;
    private Time momentZero;
    private Texture mytexture = new Texture();
    private Sprite mysprite= new Sprite();
    private Vector2f arrowVelocity;
	//Arrow() = default;
	Arrow(Vector2i mouseP, Vector2i mouse, Vector2f poczatek, RenderWindow myWindow, Clock myClock)throws IOException{
            mytexture.loadFromFile(Paths.get("Arrow.png"));
            System.out.println("Constructor strzaly");
            momentZero = myClock.getElapsedTime();
            mytexture.setSmooth(true);
            mysprite.setTexture(mytexture);
            //mysprite.setPosition(Vector2f(poczatek.x, poczatek.y - 120));
            mysprite.setPosition(new Vector2f(poczatek.x, poczatek.y - 120));
            //mysprite.setScale(0.67, 0.67);
            mysprite.setOrigin(8, 68);

            isHit = false;
            isDead = false;
            float X = (float)((mouseP.x - mouse.x) / (0.34*myWindow.getSize().x));
            float Y = (float)((mouseP.y - mouse.y) / (0.34*myWindow.getSize().y));
            if (X>1)
                    X = 1;
            if (X<-1)
                    X = -1;
            if (Y>1)
                    Y = 1;
            if (Y<-1)
                    Y = -1;
            arrowVelocity = new Vector2f(20 * X, 20 * Y);
            velocityZero = arrowVelocity;
        }
        
	//~Arrow() = default;
	void update(RenderWindow myWindow, View view1, Clock myClock,Wind myWind)
           {
               myClock.getElapsedTime();
            //timeOfRun = (myClock.getElapsedTime() - momentZero);
            timeOfRun=Time.sub(myClock.getElapsedTime(),momentZero);
            if (!(mysprite.getPosition().y >= myWindow.getSize().y - mytexture.getSize().y) && !isHit) {
                    if (-atan(arrowVelocity.x / arrowVelocity.y) * 180 / PI < 0)
                            mysprite.setRotation((float)(90 + (90 - atan(arrowVelocity.x / arrowVelocity.y) * 180 / PI)));
                    else
                            mysprite.setRotation((float)(-atan(arrowVelocity.x / arrowVelocity.y) * 180 / PI));
                    if (arrowVelocity.x < 0)
                            mysprite.rotate(180);

                    mysprite.move(arrowVelocity);
                    view1.setCenter(mysprite.getPosition());
                    double gravity = 9.81;

                    if (myWind.getmyuseWind()) {
                            //arrowVelocity.y = velocityZero.y + myWind.getv2iwind().y + gravity * (timeOfRun.asSeconds()); // V= wiatr + v0 - g*t
                            arrowVelocity=new Vector2f(myWind.getv2iwind().x + velocityZero.x,velocityZero.y+myWind.getv2iwind().y + (float)gravity * (timeOfRun.asSeconds()));
                            //arrowVelocity.x = myWind.getv2iwind().x + velocityZero.x; // Vx+ wiatr
                            
                    }										 //double gravity = 0.15;
                    else {
                            //arrowVelocity.y = velocityZero.y + gravity * (timeOfRun.asSeconds());
                            arrowVelocity=new Vector2f(arrowVelocity.x,velocityZero.y+ (float)gravity * (timeOfRun.asSeconds()));
                    }
            }
            else {
                    isHit = true;

            }
        }
                
	boolean isInterecting(Player player)//funkcja sprawdzajaca zywej strzaly z graczem podanym jako argument
        {
            if (mysprite.getGlobalBounds().intersection(player.getmyPlayerSprite().getGlobalBounds())!=null)
            {
                    isHit = true;
                    return true;
            }
            return false;
        }
	boolean getisHit()
        {
            return isHit;
        }
	void setisHit(boolean isHit)
        {
            this.isHit = isHit;
        }

	boolean getisDead()
        {
            return isDead;
        }
	void setisDead(boolean isDead)
        {
            this.isDead = isDead;
        }

	Vector2f getarrowVelocity()
        {
            return arrowVelocity;
        }
	void setarroVelocity(Vector2f arrowVelocity)
	{
            this.arrowVelocity = arrowVelocity;
        }
	Sprite getmysprite()
        {
	return mysprite;
        }

    @Override
    public void draw(RenderTarget target, RenderStates state) {
        target.draw(mysprite);
    }
};