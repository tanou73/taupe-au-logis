package model;

import java.util.Random;

public class AfficherTaupe {
	
	public AfficherTaupe(Taupe taupe){
		
		
	}
	
	public int nbAleatoireReponse(){
		Random r = new Random();
		int valeur = r.nextInt(2);
		return valeur;
	}

}
