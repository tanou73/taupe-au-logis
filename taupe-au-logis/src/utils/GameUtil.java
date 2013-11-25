package utils;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class GameUtil {
	public static final int NB_ANWSER = 3;
	public static final int NB_ROW = 2;
	public static final int NB_COL = 3;
	
	public static int pickRandomAnswer(){
		Random r = new Random();
		int valeur = r.nextInt(NB_ANWSER);
		return valeur;
	}
	
	public static Vector2 pickRandomPosition(){
		Vector2 res = new Vector2();
		Random r = new Random();
		res.x = r.nextInt(NB_COL);
		res.y = r.nextInt(NB_ROW);
		return res;
	}
}
