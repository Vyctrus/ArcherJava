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

public class Camera {
    	boolean cameraStart;
	boolean cameraMoveX;
	boolean cameraMoveY;
	boolean cameraXDirection;
	boolean cameraYDirection;
        
        public Camera()
        {
	cameraStart = false;
	cameraMoveX = false;
	cameraXDirection = false;
	cameraYDirection = false;
	cameraMoveY = false;
        }
	public void update(View view1, ArrayList<Player> players, int sequence, RenderWindow myWindow)
         {
            if (cameraStart) {
                    cameraStart = false;
                    cameraMoveX = true;
                    cameraMoveY = true;
                    if (view1.getCenter().x > players.get(sequence).getPosition().x) {
                            cameraXDirection = true;
                    }
                    if (view1.getCenter().x < players.get(sequence).getPosition().x) {
                            cameraXDirection = false;
                    }
                    if (view1.getCenter().y > players.get(sequence).getPosition().y) {
                            cameraYDirection = true;
                    }
                    if (view1.getCenter().y < players.get(sequence).getPosition().y) {
                            cameraYDirection = false;
                    }
            }
            if (cameraMoveX) {// X
                    if (view1.getCenter().x > players.get(sequence).getPosition().x && cameraXDirection) {
                            view1.move(-12, 0);
                    }
                    else {
                            if (view1.getCenter().x < players.get(sequence).getPosition().x && !cameraXDirection) {
                                    view1.move(12, 0);
                            }
                            else {
                                    cameraMoveX = false;
                                    System.out.println("Wylaczenie kamery\n");
                            }
                    }
            }

            if (cameraMoveY) {// Y
                    if (view1.getCenter().y > players.get(sequence).getPosition().y && cameraYDirection) {
                            view1.move(0, -3);
                    }
                    else {
                            if (view1.getCenter().y < players.get(sequence).getPosition().y && !cameraYDirection) {
                                    view1.move(0, 3);
                            }
                            else {
                                    cameraMoveY = false;
                            }
                    }
            }
            myWindow.setView(view1);
        }
	public void start()
        {
	cameraStart = true;
        }
	public boolean getcameraMoveX()
        {
	return cameraMoveX;
        }
	public boolean getcameraMoveY()
        {
	return cameraMoveY;
        }
}
