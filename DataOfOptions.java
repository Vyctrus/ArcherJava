/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andrzej
 */
public class DataOfOptions {
	boolean useWind;
	boolean useMusic;
	float dificultyLevelPlus;
	float dificultyLevelMinus;
        
        public boolean getuseWind()
        {
	return useWind;
        }
	public void setuseWind(boolean useWind)
        {
	this.useWind = useWind;
        }
	public boolean getuseMusic()
        {
	return useMusic;
        }
	public void setuseMusic(boolean useMusic)
        {
	this.useMusic = useMusic;
        }
	public DataOfOptions()
        {
	useWind = true;
	useMusic = true;
	dificultyLevelPlus = (float)1.5;
	dificultyLevelMinus = (float)0.5;
        }
	public void changeWind()
        {
	if(useWind) {
		useWind = false;
	}
	else {
		useWind = true;
	}
        System.out.println("Wiatr zostal zmieniony \n");
        }
	public void changeMusic()
        {
	if (useMusic) {
		useMusic = false;
	}
	else {
		useMusic = true;
	}
        System.out.println("Muzyka zostala zmieniona\n");
        }
	public void changeDificultyLevel(float levelPlus, float levelMinus)
        {
	dificultyLevelPlus = levelPlus;
	dificultyLevelMinus = levelMinus;
        }
	public float getDificultyLevelPlus()
        {
	return dificultyLevelPlus;
        }
	public float getDificultyLevelMinus()
        {
	return dificultyLevelMinus;
        }
}
