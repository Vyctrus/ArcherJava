
import org.jsfml.system.*;
import org.jsfml.graphics.*;
import java.nio.file.Paths;
import java.io.IOException;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andrzej
 */
public class DeadArrow extends BasicTransformable implements Drawable{
    
    Texture myxtexture;
    Sprite myxsprite;
     @Override
    public void draw(RenderTarget target, RenderStates state) {
        target.draw(myxsprite);
    }
    
    /**
     *
     * @param x
     * @throws IOException
     */
    DeadArrow(Arrow x)throws IOException
    {
	myxtexture.loadFromFile(Paths.get("Arrow.png"));
	myxtexture.setSmooth(true);
	myxsprite.setTexture(myxtexture);
	myxsprite.setPosition(new Vector2f(x.getmysprite().getPosition().x , x.getmysprite().getPosition().y));
	myxsprite.setRotation(x.getmysprite().getRotation());
	myxsprite.setOrigin(8, 68);
    }
    
}
