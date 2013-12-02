package sprites;

import screens.MainMenu;
import tween.SpriteAccessor;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Mole extends Sprite {
	private float posX;
	private float posY;
	private Texture region;
	private float height;
	private float width;	
	private Sprite greenSquare;
	private Sprite woodPane;
	private Label wordToDisplay;
	private String answer;
	
	public Mole(Texture region, float height, float width, Label wordToDisplay) {
		super();
		
		this.region = region;
		super.setRegion(region);
		this.height = height;
		this.width = width;

		greenSquare = new Sprite(new Texture(Gdx.files.internal("img/terrier.png")));
		woodPane = new Sprite(new Texture(Gdx.files.internal("img/woodPane.png")));
		woodPane.setOrigin(woodPane.getWidth()/2, 0);
		
		this.wordToDisplay = wordToDisplay;
		
		super.setSize(width, height);
	}
	
	public void animateIn(final TweenManager tweenManager) {
		Tween.set(this, SpriteAccessor.POSY).target(0).start(tweenManager);
		Tween.set(woodPane, SpriteAccessor.DEGREE).target(10).start(tweenManager);
		Tween.to(this, SpriteAccessor.POSY, 1).target(150).setCallback(new TweenCallback() {
			
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				Tween.set(woodPane, SpriteAccessor.POSY).target(1000).start(tweenManager);
				Tween.to(woodPane, SpriteAccessor.POSY, 0.5f).target(150).setCallback(new TweenCallback() {
					
					@Override
					public void onEvent(int type, BaseTween<?> source) {
						wordToDisplay.setText(answer);
						wordToDisplay.setRotation(25);
						wordToDisplay.setPosition(woodPane.getX() + 50, woodPane.getY()-50);
						Tween.to(wordToDisplay, SpriteAccessor.ALPHA, 1f).target(1).start(tweenManager);
					}
				}).start(tweenManager);

			}
		}).start(tweenManager);
	}
	
	public void animateOut(final TweenManager tweenManager) {
		final float posx = this.posX;
		Tween.set(this, SpriteAccessor.POSY).target(150).start(tweenManager);
		Tween.to(this, SpriteAccessor.POSY, 1).target(0).start(tweenManager);
		Tween.to(woodPane, SpriteAccessor.DEGREE, 0.7f).target(90).start(tweenManager);
		Tween.to(woodPane, SpriteAccessor.POSY, 0.5f).target(-300).delay(0.1f).start(tweenManager);
		Tween.to(wordToDisplay, SpriteAccessor.ALPHA, 1f).target(0).start(tweenManager);
	}

	public float getPosX() {
		return posX;
	}


	public void setPosX(float posX) {
		super.setPosition(posX, posY);
		this.posX = posX;
		updateMasquePos();
		updateWoodPanePos();
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
		updateMasquePos();
		updateWoodPanePos();
		super.setPosition(posX, posY);
	}
	
	public void updateMasquePos() {
		greenSquare.setPosition(this.posX, this.posY);
	}
	
	public void updateWoodPanePos() {
		woodPane.setPosition(this.posX - 100, 1000);
	}
	
	public Sprite getMasque() {
		return greenSquare;
	}
	
	public Sprite getWoodPane() {
		return woodPane;
	}
	
	public Label getWordToDisplay() {
		return wordToDisplay;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
