package sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Mole extends Sprite {
	private float posX;
	private float posY;
	private Texture region;
	private float height;
	private float width;	
	
	public Mole(Texture region, float height, float width) {
		super();
		this.region = region;
		super.setRegion(region);
		this.height = height;
		this.width = width;
		super.setSize(width, height);
	}

	public float getPosX() {
		return posX;
	}


	public void setPosX(float posX) {
		super.setPosition(posX, posY);
		this.posX = posX;
	}


	public float getPosY() {
		return posY;
	}


	public void setPosY(float posY) {
		super.setPosition(posX, posY);
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

	public void setPos(Vector2 pos) {
		this.posX = pos.x * (Gdx.graphics.getWidth()/3);
		this.posY = pos.y * (Gdx.graphics.getHeight()/3);
		super.setPosition(posX, posY);
	}
}
