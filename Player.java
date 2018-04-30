
import org.jsfml.graphics.BasicTransformable;
import org.jsfml.graphics.Drawable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.jsfml.system.*;
import org.jsfml.graphics.*;
import java.nio.file.Paths;
import java.io.IOException;
/**
 *
 * @author Andrzej
 */
public class Player extends BasicTransformable implements Drawable {
        //FloatRect(Vector2f position, Vector2f size)
        //public FloatRect auraOfCollision;//#poprawa na private
        int playerHP;
	Texture myPlayerTexture;
	Sprite myPlayerSprite;
        @Override
	public void draw(RenderTarget target, RenderStates state)
        {
            target.draw(this.myPlayerSprite, state);///////
        }
        
        
	public Player(float xp, float yp)throws IOException
        {
            myPlayerTexture=new Texture();
            myPlayerTexture.loadFromFile(Paths.get("Player.png"));
            myPlayerTexture.setSmooth(true);
            myPlayerSprite=new Sprite();
            myPlayerSprite.setTexture(myPlayerTexture);
            myPlayerSprite.setOrigin(myPlayerTexture.getSize().x / 2, myPlayerTexture.getSize().y / 2);
            myPlayerSprite.setScale(1.0f, 1.0f);
            myPlayerSprite.setPosition(new Vector2f(xp, yp));
            
            //auraOfCollision=new FloatRect(new Vector2f(xp, yp),new Vector2f(myPlayerTexture.getSize()));
            
            playerHP = 10;
        }
	public RectangleShape rect;
        @Override
	public Vector2f getPosition()
        {
            return myPlayerSprite.getPosition();
        }
	public int getplayerHP()
        {
            return playerHP;
        }
	public void setplayerHP(int playerHP)
        {
	this.playerHP = playerHP;
        }
	public void decreaseHP()
        {
            playerHP--;
        }
	public void playerFlip()
        {
            myPlayerSprite.setScale(-1.0f, 1.0f);
        }
	public Sprite getmyPlayerSprite()
        {
            return myPlayerSprite;
        }
        
}
