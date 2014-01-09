package utils;

import com.badlogic.gdx.Gdx;

public class PosUtil {
	private static float gameResolutionX = 1200f;
	private static float gameResolutionY = 720f;
	
	public static float xUnite(float x)
	{
	       return (x*Gdx.graphics.getWidth())/gameResolutionX;
	}
	
	public static float yUnite(float y)
	{
	       return (y*Gdx.graphics.getHeight())/gameResolutionY;
	}
}
