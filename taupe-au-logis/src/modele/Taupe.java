package modele;

public class Taupe {
	private int posX;
	private int posY;
	private boolean reponse;
	private String text;
	
	public Taupe(int posX, int posY, boolean reponse, String text){
		this.posX= posX;
		this.posY= posY;
		this.reponse=reponse;
		this.text=text;
	}
	
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public boolean isReponse() {
		return reponse;
	}
	public void setReponse(boolean reponse) {
		this.reponse = reponse;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

}
