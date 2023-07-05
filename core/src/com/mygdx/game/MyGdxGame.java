package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.screens.AboutScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.SettingsScreen;
import com.mygdx.game.utils.GameSettings;

public class MyGdxGame extends Game {

	public OrthographicCamera camera;
	public SpriteBatch batch;
	public Vector3 touch;

	public MyCustomFont commonFont;
	public MyCustomFont largeFont;
	public MyCustomFont secondaryFont;

	public AboutScreen aboutScreen;
	public GameScreen gameScreen;
	public MenuScreen menuScreen;
	public SettingsScreen settingsScreen;

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		touch = new Vector3();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);

		largeFont = new MyCustomFont(100, "fonts/arnamu.ttf", new Color(1, 1, 1, 1));
		commonFont = new MyCustomFont(50, "fonts/arnamu.ttf", new Color(1, 1, 1, 1));
		secondaryFont = new MyCustomFont(50, "fonts/arnamu.ttf", new Color(0.5f, 0.5f, 1, 1));

		aboutScreen = new AboutScreen();
		gameScreen = new GameScreen(this);
		menuScreen = new MenuScreen(this);
		settingsScreen = new SettingsScreen(this);

		setScreen(menuScreen);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
