package screens;

import sprites.Mole;
import utils.GameUtil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
	private SpriteBatch batch;
	private Mole mole;
	private float timelapse;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		timelapse += delta;
		if (timelapse > 2) {
			mole.setPos(GameUtil.pickRandomPosition());
			timelapse = 0;
		}
		
		batch.begin();
		mole.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		
		timelapse = 0;
		
		// Init le sprite de la taupe
		mole = new Mole(new Texture(Gdx.files.internal("img/mole.png")), 300, 300);
		mole.setPosX(200);
		mole.setPosY(200);		
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
