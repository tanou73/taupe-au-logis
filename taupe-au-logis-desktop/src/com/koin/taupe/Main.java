package com.koin.taupe;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "taupe-au-logis";
		cfg.vSyncEnabled = true;
		cfg.useGL20 = true;
		cfg.width = 1200;
		cfg.height = 720;
		
		new LwjglApplication(new TaupeGame(), cfg);
	}
}