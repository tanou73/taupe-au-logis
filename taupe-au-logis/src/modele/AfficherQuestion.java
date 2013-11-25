package modele;

import java.util.Random;

public class AfficherQuestion {
	
	public AfficherQuestion(){
		
	}
	
	public int nbAleatoireQuestion(){
		Random r = new Random();
		int valeur = r.nextInt(9);
		return valeur;
	}

}
