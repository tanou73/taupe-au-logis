package com.koin.taupe;

import screens.Splash;
import utils.PosUtil;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class TaupeGame extends Game {
	
	public static final String TITLE = "taupe-au-logis, VERSION:0";

	private Music bgMusic;

	@Override
	public void create() {
		Texture.setEnforcePotImages(false);
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/home_screen_sound.wav"));
		bgMusic.setLooping(true);
		bgMusic.play();

		setScreen(new Splash());
	}
	

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
