package screens;

import javax.swing.text.Position;

import sprites.Mole;
import tween.SpriteAccessor;
import utils.GameUtil;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import controller.GameCtrl;

public class GameScreen implements Screen {
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
	/** current mole which stand **/
	private int moleNumber;
	/** atlas texture and skin **/
	private Skin skin;
	/** font **/
	private BitmapFont yellow;
	/** question label **/
	private Label questionLabel;
	/** game **/
	private GameCtrl controller;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0.612f, 0.141f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tweenManager.update(delta);

		// question is changed
		if (controller.changeQuestion()) {
			changeQuestion();
		}

		timelapse += delta;
		if (timelapse > 3) {
			animateMoles();
		}
		
		checkEvent();

		batch.begin();
		drawMoles();
		batch.end();
	}
	
	public void checkEvent() {
		if (Gdx.input.isTouched()){			
			Rectangle moleRHitBox = new Rectangle(moleR.getX(), moleR.getY(), moleR.getWidth(), moleR.getHeight());
			Rectangle moleCHitBox = new Rectangle(moleC.getX(), moleC.getY(), moleC.getWidth(), moleC.getHeight());
			Rectangle moleLHitBox = new Rectangle(moleL.getX(), moleL.getY(), moleL.getWidth(), moleL.getHeight());
			
			float x = Gdx.input.getX();
			float y = Gdx.input.getY();
			System.out.println("x - y "+x+"-"+y);
			int isRight = -1;			
			if (moleRHitBox.contains(x, y)){
				System.out.println("hey ho");
				isRight = controller.checkAnswer(moleR.getAnswer());
			} else if (moleCHitBox.contains(x, y)){
				isRight = controller.checkAnswer(moleR.getAnswer());
			} else if (moleLHitBox.contains(x, y)){
				isRight = controller.checkAnswer(moleR.getAnswer());
			}
			
			switch (isRight) {
			case 1:
				System.out.println("gooood");
				break;
			case 2:
				System.out.println("baaaaaadd");
				timelapse = 20;
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

		controller = new GameCtrl();

		timelapse = 0;
		moleNumber = -1;

		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"),
				new TextureAtlas("ui/atlas.pack"));

		// Creating font
		yellow = new BitmapFont(Gdx.files.internal("font/yellow.fnt"), false);

		questionLabel = new Label("", skin, "big");
		questionLabel.setPosition(10, Gdx.graphics.getHeight() - 50);
		Label textToDisplay = new Label("", skin, "default");
		System.out.println(questionLabel.getText());

		// Init le sprite de la taupe
		moleR = new Mole(new Texture(Gdx.files.internal("img/mole.png")), 300,
				300, textToDisplay);
		moleR.setPosX(100);
		moleR.setPosY(0);

		moleC = new Mole(new Texture(Gdx.files.internal("img/mole.png")), 300,
				300, textToDisplay);
		moleC.setPosX(450);
		moleC.setPosY(0);

		moleL = new Mole(new Texture(Gdx.files.internal("img/mole.png")), 300,
				300, textToDisplay);
		moleL.setPosX(850);
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

}
