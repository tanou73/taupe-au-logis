package sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Mole extends Sprite {
	private int posX;
	private int posY;
	private Texture region;
	private float height;
	private float width;	
	
	public Mole(Texture region, float height, float width) {
		super();
		this.region = region;
		this.height = height;
		this.width = width;
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


	public Texture getRegion() {
		return region;
	}


	public void setRegion(Texture region) {
		this.region = region;
	}


	public float getHeight() {
		return height;
	}


	public void setHeight(float height) {
		this.height = height;
	}


	public float getWidth() {
		return width;
	}


	public void setWidth(float width) {
		this.width = width;
	}
}
