
import java.io.IOException;
import java.nio.file.Paths;
import org.jsfml.audio.Music;
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andrzej
 */
public class SoundandMusic {
        boolean myuseSound;
	boolean bodyHit=false;
	boolean firstTime = true;
	Time myTime;
	Time oldTime;
	SoundBuffer sBuffer1;
	SoundBuffer sBuffer2;
	SoundBuffer sBufferP;
	Sound mySound1;
	Sound mySound2;
	Sound painSound;
	Music notmyMusic;
        
	SoundandMusic(Clock myCLock, DataOfOptions doo)throws IOException
        {
            myuseSound = doo.getuseMusic();
            if (myuseSound) {
                    //if (!musicBuffer.loadFromFile("title.wav"))
                    //	std::cout << "cos poszlo nie tak\n";
                    
                    notmyMusic.openFromFile(Paths.get("title.wav"));
                    sBuffer1.loadFromFile(Paths.get("sound4.wav"));
                    sBuffer2.loadFromFile(Paths.get("sound2.wav"));
                    sBufferP.loadFromFile(Paths.get("pain.wav"));

                    notmyMusic.setVolume(10);         // reduce the volume <0-100>
                    notmyMusic.setLoop(true);         // make it loop

                    mySound1.setBuffer(sBuffer1);
                    //mySound1.setPitch(1.0);			//playing speed

                    mySound2.setBuffer(sBuffer2);
                    //mySound2.setPitch(1.0);

                    painSound.setBuffer(sBufferP);
                    //painSound.setPitch(1.0);

                    oldTime = Time.getSeconds(0);
            }
        }
	void flyingUpdate(Clock myClock)
        {
            if (myuseSound) {
                    myTime = myClock.getElapsedTime();
                    if (myTime.asSeconds() >= 1.5 + oldTime.asSeconds() || firstTime) {
                            mySound1.play();
                            oldTime = myTime;
                            firstTime = false;
                    }
            }
        }
	void hitUpdate()
        {
            if (myuseSound) {
                    if (!bodyHit) {
                            mySound2.play();
                    }
                    else {
                            bodyHit = false;
                    }
            }
        }
	void painUpdate()
        {
            if (myuseSound) {
                    painSound.play();
                    bodyHit = true;
            }
        }
	void musicPlay()
        {
            if (myuseSound) {
                    notmyMusic.play();
            }
        }        
	void musicStop()
        {	
		notmyMusic.stop();
        }
	boolean getfirstTime()
        {
	return firstTime;
        }
	void setfirstTime(boolean firstTime)
        {
	this.firstTime = firstTime;
        }
}
