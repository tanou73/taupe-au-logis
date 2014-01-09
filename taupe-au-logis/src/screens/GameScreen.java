package screens;

import java.util.Date;

import sprites.Mole;
import tween.SpriteAccessor;
import utils.GameUtil;
import utils.PosUtil;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import controller.GameCtrl;

public class GameScreen implements Screen, InputProcessor {
	private SpriteBatch batch;
	/** mole on the left **/
	private Mole moleL;
	/** mole on the middle **/
	private Mole moleC;
	/** mole on the right **/
	private Mole moleR;
	/** use for simulate a kind of setInterval **/
	private float timelapse;
	/** Tween manager **/
	private TweenManager tweenManager;
	/** current mole which stand : 0 = Left, 1 = Center, 2 = Right**/
	private int moleNumber;
	/** atlas texture and skin **/
	private Skin skin;
	/** font **/
	private BitmapFont yellow;
	/** question label **/
	private Label questionLabel;
	/** game **/
	private GameCtrl controller;
	/** Time of start **/
	private Date startDate;
	/** number of "life" the player has left **/
	private int life;
	
	private float x, y;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0.612f, 0.141f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tweenManager.update(delta);

		// question is changed
		if (controller.changeQuestion()) {
			changeQuestion();
		}
		try {
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		 shapeRenderer.begin(ShapeType.Line);
		 shapeRenderer.setColor(1, 1, 0, 1);
		 shapeRenderer.circle(this.x, this.y, 5);
		 shapeRenderer.end();
		} catch (Exception e) {}

		timelapse += delta;
		if (timelapse > 3) {
			animateMoles();
		}

		batch.begin();
		drawMoles();
		batch.end();
	}

	@SuppressWarnings("deprecation")
	public void checkEvent(float x, float y) {
		if (!controller.hasLost()) {	
			/*
			Rectangle moleRHitBox = new Rectangle(
					moleR.getX()-(moleR.getWidth()/2), 
					moleR.getY()-(moleR.getHeight()/2),
					moleR.getWidth(), 
					moleR.getHeight());
			
			Rectangle moleCHitBox = new Rectangle(
					moleC.getX()-(moleC.getWidth()/2), 
					moleC.getY()-(moleC.getHeight()/2),
					moleC.getWidth(), 
					moleC.getHeight());
			
			Rectangle moleLHitBox = new Rectangle(
					moleL.getX()-(moleL.getWidth()/2), 
					moleL.getY()-(moleL.getHeight()/2),
					moleL.getWidth(), 
					moleL.getHeight()); */
			
			System.out.println("x : " +x+ " y : "+ y);
			
			System.out.println("MoleL : "+moleL.getBoundingRectangle().x + " y: "+moleL.getBoundingRectangle().y);
			int isRight = -1;		
			if (moleR.getBoundingRectangle().contains(x, y) && (moleNumber == 2)) {
				isRight = controller.checkAnswer(moleR.getAnswer());
			} 
			else if (moleC.getBoundingRectangle().contains(x, y) && moleNumber == 1) {
				isRight = controller.checkAnswer(moleC.getAnswer());
			} 
			else if (moleL.getBoundingRectangle().contains(x, y) && moleNumber == 0) {
				isRight = controller.checkAnswer(moleL.getAnswer());
			}
	
			switch (isRight) {
			case 1:
				questionLabel.setColor(Color.GREEN);
				if (controller.isFinished()){
					System.out.println("finished");
					Date now = new Date();
					Date time = new Date(startDate.getTime() - now.getTime());				
					questionLabel.setText("Bravo ! Vous avez gagné en "+ (now.getTime() - startDate.getTime())/1000 +" secondes");
				} else {
					controller.setChangeQuestion(true);
				}
				break;
			case 0:
				questionLabel.setColor(Color.RED);
				life--;
				if (life > 0){
					timelapse = 20;
				} else {
					questionLabel.setText("Dommage ! Vous avez perdu ...");
					controller.lost();
				}
				break;
			default:
				break;
			}
		}
	}

	public void changeQuestion() {
		questionLabel.setText(controller.getNextQuestion().getQuestion());
		moleR.setAnswer(controller.getCurQuestion().getAnswer(0));
		moleC.setAnswer(controller.getCurQuestion().getAnswer(1));
		moleL.setAnswer(controller.getCurQuestion().getAnswer(2));
	}

	public void drawMoles() {
		questionLabel.draw(batch, 1);

		moleR.getWoodPane().draw(batch);
		moleR.draw(batch);
		moleR.getMasque().draw(batch);
		moleR.getWordToDisplay().draw(batch, 1);

		moleC.getWoodPane().draw(batch);
		moleC.draw(batch);
		moleC.getMasque().draw(batch);
		moleC.getWordToDisplay().draw(batch, 1);

		moleL.getWoodPane().draw(batch);
		moleL.draw(batch);
		moleL.getMasque().draw(batch);
		moleL.getWordToDisplay().draw(batch, 1);
	}

	public void animateMoles() {
		// animate out the current mole
		if (moleNumber == 0)
			moleL.animateOut(tweenManager);
		else if (moleNumber == 1)
			moleC.animateOut(tweenManager);
		else if (moleNumber == 2)
			moleR.animateOut(tweenManager);

		// Pick a random mole (excludinng the current one)
		moleNumber = GameUtil.pickRandomPosition(moleNumber);

		String word = "word";

		// animate the mole
		if (moleNumber == 0)
			moleL.animateIn(tweenManager);
		else if (moleNumber == 1)
			moleC.animateIn(tweenManager);
		else if (moleNumber == 2)
			moleR.animateIn(tweenManager);

		timelapse = 0;
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		batch = new SpriteBatch();

		Gdx.input.setInputProcessor(this);
		
		controller = new GameCtrl();

		timelapse = 0;
		moleNumber = -1;
		startDate = new Date();
		life = 3;

		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"),
				new TextureAtlas("ui/atlas.pack"));

		// Creating font
		yellow = new BitmapFont(Gdx.files.internal("font/yellow.fnt"), false);

		questionLabel = new Label("", skin, "big");
		questionLabel.setFontScale(PosUtil.xUnite(0.7f), PosUtil.yUnite(0.7f));
		questionLabel.setPosition(PosUtil.xUnite(10), Gdx.graphics.getHeight() - 200);
		Label textToDisplay = new Label("", skin, "default");

		// Init le sprite de la taupe
		moleR = new Mole(new Texture(Gdx.files.internal("img/mole.png")), 300,
				300, textToDisplay);
		moleR.setPosX(100);
		moleR.setPosY(0);

		moleC = new Mole(new Texture(Gdx.files.internal("img/mole.png")), 300,
				300, textToDisplay);
		moleC.setPosX(433);
		moleC.setPosY(0);

		moleL = new Mole(new Texture(Gdx.files.internal("img/mole.png")), 300,
				300, textToDisplay);
		moleL.setPosX(766);
		moleL.setPosY(0);

		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		this.x = screenX;
		this.y = Gdx.graphics.getHeight() - screenY;
		System.out.println("capture x --> "+screenX+" ---> y "+screenY);
		checkEvent( screenX, 
				Gdx.graphics.getHeight() - screenY); 
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
