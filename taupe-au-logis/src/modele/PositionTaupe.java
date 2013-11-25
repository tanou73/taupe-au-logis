package modele;

import java.awt.event.ActionListener;
import java.util.Random;

public abstract class PositionTaupe implements ActionListener{
	
	public PositionTaupe (){
		
	}
	public int nbAleatoirePosition(){
		Random r = new Random();
		int valeur = r.nextInt(5);
		return valeur;
	}
}
