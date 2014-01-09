package screens;

import tween.ActorAccessor;
import utils.PosUtil;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class MainMenu implements Screen {

	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonPlay, buttonExit;
	private BitmapFont yellow, black;
	private Label heading;
	private SpriteBatch batch;
	private Sprite spriteBg;
	private TweenManager tweenManager;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Table.drawDebug(stage);
		batch.begin();
		spriteBg.draw(batch);
		batch.end();

		tweenManager.update(delta);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
		table.invalidateHierarchy();
		table.setSize(width, height);
	}

	@Override
	public void show() {
		stage = new Stage();
		batch = new SpriteBatch();

		Gdx.input.setInputProcessor(stage);

		atlas = new TextureAtlas("ui/button.pack");
		skin = new Skin(atlas);

		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// Creating font
		yellow = new BitmapFont(Gdx.files.internal("font/yellow.fnt"), false);
		black = new BitmapFont(Gdx.files.internal("font/font.fnt"), false);

		// Creating buttons
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button_up");
		textButtonStyle.down = skin.getDrawable("button_down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = black;

		buttonPlay = new TextButton("Jouer", textButtonStyle);
		buttonPlay.pad(PosUtil.xUnite(20));
		buttonPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new Levels());
			}
		});
		buttonExit = new TextButton("Quitter", textButtonStyle);
		buttonExit.pad(PosUtil.xUnite(20));
		buttonExit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});

		// Creating heading
		LabelStyle headingStyle = new LabelStyle(yellow, Color.WHITE);
		heading = new Label("taupe au logis", headingStyle);
		heading.setFontScale(1.5f);

		// Creating background sprite
		Texture textBg = new Texture(Gdx.files.internal("img/bg.png"));
		spriteBg = new Sprite(textBg);
		spriteBg.setBounds(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		// adding stuffs
		table.add(heading).spaceBottom(PosUtil.xUnite(100)).row();
		table.add(buttonPlay).spaceBottom(PosUtil.xUnite(50)).row();
		table.add(buttonExit);
		table.debug();
		stage.addActor(table);

		// creating animation
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());

		// heading and buttons fade-in
		Timeline.createSequence()
				.beginSequence()
				.push(Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(buttonExit, ActorAccessor.ALPHA).target(0))
				.push(Tween.from(heading, ActorAccessor.ALPHA, .25f).target(0))
				.push(Tween.to(buttonPlay, ActorAccessor.ALPHA, .25f).target(1))
				.push(Tween.to(buttonExit, ActorAccessor.ALPHA, .25f).target(1))
				.end().start(tweenManager);

		// table fade-in
		Tween.from(table, ActorAccessor.ALPHA, .75f).target(0)
				.start(tweenManager);

		tweenManager.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void hide() {
		dispose();
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
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		yellow.dispose();
		black.dispose();
		spriteBg.getTexture().dispose();
	}

}
