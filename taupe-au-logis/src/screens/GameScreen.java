package screens;

import java.util.ArrayList;
import java.util.Date;

import sprites.Life;
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
	/** current mole which stand : 0 = Left, 1 = Center, 2 = Right **/
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
	/** life **/
	private ArrayList<Life> lifes;

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

		batch.begin();
		drawMoles();
		for (int i = 0; i < lifes.size(); i++) {
			lifes.get(i).setPosition(Gdx.graphics.getWidth() - (i*PosUtil.xUnite(35) + PosUtil.xUnite(35)), PosUtil.yUnite(35));
			lifes.get(i).draw(batch);
		}
		batch.end();
	}

	@SuppressWarnings("deprecation")
	public void checkEvent(float x, float y) {
		if (!controller.hasLost()) {
			int isRight = -1;
			if (moleR.getBoundingRectangle().contains(x, y)
					&& (moleNumber == 2)) {
				isRight = controller.checkAnswer(moleR.getAnswer());
				if (isRight == 1)
					moleR.getWordToDisplay().setColor(Color.GREEN);
				else
					moleR.getWordToDisplay().setColor(Color.RED);
			} else if (moleC.getBoundingRectangle().contains(x, y)
					&& moleNumber == 1) {
				isRight = controller.checkAnswer(moleC.getAnswer());
				if (isRight == 1)
					moleC.getWordToDisplay().setColor(Color.GREEN);
				else
					moleC.getWordToDisplay().setColor(Color.RED);
			} else if (moleL.getBoundingRectangle().contains(x, y)
					&& moleNumber == 0) {
				isRight = controller.checkAnswer(moleL.getAnswer());
				if (isRight == 1)
					moleL.getWordToDisplay().setColor(Color.GREEN);
				else
					moleL.getWordToDisplay().setColor(Color.RED);
			}

			switch (isRight) {
			case 1:
				if (controller.isFinished()) {
					System.out.println("finished");
					Date now = new Date();
					Date time = new Date(startDate.getTime() - now.getTime());
					questionLabel.setText("Bravo ! Vous avez gagné en "
							+ (now.getTime() - startDate.getTime()) / 1000
							+ " secondes");
				} else {
					controller.setChangeQuestion(true);
				}
				break;
			case 0:
				life--;
				lifes.remove(lifes.size() -1);
				if (life > 0) {
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
		if (moleNumber == 0) {
			moleL.getWordToDisplay().setColor(Color.WHITE);
			moleL.animateIn(tweenManager);
		} else if (moleNumber == 1) {
			moleC.getWordToDisplay().setColor(Color.WHITE);
			moleC.animateIn(tweenManager);
		} else if (moleNumber == 2) {
			moleR.getWordToDisplay().setColor(Color.WHITE);
			moleR.animateIn(tweenManager);
		}

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
		questionLabel.setPosition(PosUtil.xUnite(10),
				Gdx.graphics.getHeight() - 200);
		
		// Init le sprite de la taupe
		moleR = new Mole(new Texture(Gdx.files.internal("img/mole.png")), 300,
				300, new Label("", skin, "default"));
		moleR.setPosX(100);
		moleR.setPosY(0);

		moleC = new Mole(new Texture(Gdx.files.internal("img/mole.png")), 300,
				300, new Label("", skin, "default"));
		moleC.setPosX(433);
		moleC.setPosY(0);

		moleL = new Mole(new Texture(Gdx.files.internal("img/mole.png")), 300,
				300, new Label("", skin, "default"));
		moleL.setPosX(766);
		moleL.setPosY(0);
		
		lifes = new ArrayList<Life>();
		for (int i = 0; i < 3; i++) {
			lifes.add(new Life());
		}

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
		checkEvent(screenX, Gdx.graphics.getHeight() - screenY);
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
