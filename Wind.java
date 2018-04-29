
import java.io.IOException;
import static java.lang.Math.*;
import java.nio.file.Paths;
import java.util.Random;
import org.jsfml.graphics.BasicTransformable;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
/**
 *
 * @author Andrzej
 */
public class Wind extends BasicTransformable implements Drawable{
        Random myRand;
        Vector2i v2iwind;
	Clock windClock;
	Time windTime1;
	Time oldTime;
	boolean myuseWind = true;
	int maxPlusX = 7;
	int maxNegativeX = 7;
	int maxPlusY = 4;
	int maxNegativeY = 4;
	int windX, windY;

	int moveX = 0;
	int moveY = 0;
	int moveXplus = 1;
	int moveYplus = 1;

	Texture windtexture;
	Sprite windsprite;
        @Override
	public void draw(RenderTarget target, RenderStates state){
            if (myuseWind) { target.draw(this.windsprite, state); }
        }
        
        ///
	public Wind( Vector2f position,DataOfOptions doo)throws IOException
        {
            myRand=new Random();
            myRand.setSeed(System.currentTimeMillis());
            v2iwind = new Vector2i(20, 0);
            myuseWind = doo.getuseWind();
            if (myuseWind) {
                    /////////////////////////////////////// Blok Rng
                    
                    windY = myRand.nextInt() % 2;
                    windX = myRand.nextInt() % 2;
                    if (windY == 1) {
                            windY = -(myRand.nextInt() % maxNegativeY);
                    }
                    else{ //windY ==0
                            windY = myRand.nextInt() % maxPlusY;
                    }
                    if (windX % 2 == 0) {
                            windX = myRand.nextInt() % maxPlusX;
                    }
                    else {//windX ==1
                            windX = -(myRand.nextInt() % maxNegativeX);
                    }
                    v2iwind=new Vector2i(windX,windY);
                    ///////////////////////////////////////
                    
                    windtexture.loadFromFile(Paths.get("Arrow.png"));
                    windtexture.setSmooth(true);
                    windsprite.setTexture(windtexture);
                    windsprite.setPosition(new Vector2f(position.x, position.y ));
                    if (v2iwind.y == 0) {
                            windsprite.setRotation(90);
                    }
                    else {
                            if (-atan(v2iwind.x / v2iwind.y) * 180 / PI < 0)
                                    windsprite.setRotation((float)(90 + (90 - atan(v2iwind.x / v2iwind.y) * 180 / PI)));
                            else
                                    windsprite.setRotation((float)(-atan(v2iwind.x / v2iwind.y) * 180 / PI));
                    }
                    if (v2iwind.x < 0)
                            windsprite.rotate(180);
                    windsprite.setOrigin(8, 68);
            }
        }
	public void update(int sequence,Vector2f position1,Vector2f position2)
        {
            if (myuseWind) {
                    if (sequence==0) {
                            position1=new Vector2f(position1.x+150,position1.y-150);
                            windsprite.setPosition(position1);
                    }
                    if (sequence == 1) {
                            position2=new Vector2f(position2.x+150,position2.y-150);
                            windsprite.setPosition(position2);
                    }
                    windTime1 = windClock.getElapsedTime();
                    if (windTime1.asSeconds() >= 2 + oldTime.asSeconds()) {	//co ile ma sie odswiezac wiatr
                            ///////////////////////////////
                            if (1==1/*rand() % 2*/) {// zmien wiatr lub niezmien
                                    if (moveX == 0) {
                                            moveX = 7; //rand() % 5;
                                            moveXplus = (moveXplus+1) % 2;
                                    }
                                    if (moveY == 0) {
                                            moveY = myRand.nextInt() % 4;
                                            moveYplus = (moveYplus+1) % 2;
                                    }
                                    else {
                                            if (moveX > 0 && moveXplus == 1) {
                                                    windX += 1;
                                                    moveX--;
                                            }
                                            if (moveX > 0 && moveXplus == 0) {
                                                    windX -= 1;
                                                    moveX--;
                                            }
                                            if (moveY > 0 && moveYplus == 1) {
                                                    windY += 1;
                                                    moveY--;
                                            }
                                            if (moveY > 0 && moveYplus == 0) {
                                                    windY -= 1;
                                                    moveY--;
                                            }
                                    }

                                    if (windX >= maxPlusX) {	////////// blok na max'ach
                                            windX = 0;// maxPlusX - 1;
                                    }
                                    if (windX <= -maxNegativeX) {
                                            windX = 0;// maxNegativeX - 1;
                                    }
                                    if (windY >= maxPlusY) {
                                            windY = 0;// maxPlusY - 1;
                                    }
                                    if (windY <= -maxNegativeY) {///////////
                                            windY = 0;// maxNegativeY - 1;
                                    }

                                    v2iwind=new Vector2i(windX,windY);
                            }
                            ///////////////////////////////
                            oldTime = windTime1;
                    }

                    if (v2iwind.y == 0) {
                            windsprite.setRotation(90);
                    }
                    else {
                            if (-atan(v2iwind.x / v2iwind.y) * 180 / PI < 0)
                                    windsprite.setRotation((float)(90 + (90 - atan(v2iwind.x / v2iwind.y) * 180 / PI)));
                            else
                                    windsprite.setRotation((float)(-atan(v2iwind.x / v2iwind.y) * 180 / PI));
                    }
                    if (v2iwind.x < 0)
                            windsprite.rotate(180);

                    windsprite.setTexture(windtexture);
            }
        }
        
        public void update()
        {
            if (myuseWind) {
                    windTime1 = windClock.getElapsedTime();
                    if (windTime1.asSeconds() >= 2 + oldTime.asSeconds()) {	//co ile ma sie odswiezac wiatr
                                                                                                                                    ///////////////////////////////

                            if (1==1/*rand() % 2*/) {// zmien wiatr lub niezmien
                                    if (moveX == 0) {
                                            moveX = 7; //rand() % 5;
                                            moveXplus = (moveXplus + 1) % 2;
                                    }
                                    if (moveY == 0) {
                                            moveY = myRand.nextInt() % 4;
                                            moveYplus = (moveYplus + 1) % 2;
                                    }
                                    else {
                                            if (moveX > 0 && moveXplus == 1) {
                                                    windX += 1;
                                                    moveX--;
                                            }
                                            if (moveX > 0 && moveXplus == 0) {
                                                    windX -= 1;
                                                    moveX--;
                                            }
                                            if (moveY > 0 && moveYplus == 1) {
                                                    windY += 1;
                                                    moveY--;
                                            }
                                            if (moveY > 0 && moveYplus == 0) {
                                                    windY -= 1;
                                                    moveY--;
                                            }
                                    }

                                    if (windX >= maxPlusX) {	////////// blok na max'ach
                                            windX = 0;// maxPlusX - 1;
                                    }
                                    if (windX <= -maxNegativeX) {
                                            windX = 0;// maxNegativeX - 1;
                                    }
                                    if (windY >= maxPlusY) {
                                            windY = 0;// maxPlusY - 1;
                                    }
                                    if (windY <= -maxNegativeY) {///////////
                                            windY = 0;// maxNegativeY - 1;
                                    }

                                    v2iwind=new Vector2i(windX,windY);
                            }

                            ///////////////////////////////
                            oldTime = windTime1;
                    }

                    if (v2iwind.y == 0) {
                            windsprite.setRotation(90);
                    }
                    else {
                            if (-atan(v2iwind.x / v2iwind.y) * 180 / PI < 0)
                                    windsprite.setRotation(90 + ((float)(90 - atan(v2iwind.x / v2iwind.y) * 180 / PI)));
                            else
                                    windsprite.setRotation((float)(-atan(v2iwind.x / v2iwind.y) * 180 / PI));
                    }
                    if (v2iwind.x < 0)
                            windsprite.rotate(180);

                    windsprite.setTexture(windtexture);
            }
        }
        
	public Sprite getwindsprite()
        {
	return windsprite;
        }

	public boolean getmyuseWind()
        {
	return myuseWind;
        }
	public void setmyuseWind(boolean myuseWind)
        {
	this.myuseWind = myuseWind;
        }

	public Vector2i getv2iwind(){
            	return v2iwind;
        }
	public void setv2iwind(Vector2i v2iwind)
        {
	this.v2iwind = v2iwind;
        }
}
