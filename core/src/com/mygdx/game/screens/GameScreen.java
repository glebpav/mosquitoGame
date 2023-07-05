package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.actors.Mosquito;
import com.mygdx.game.ui.Blackout;
import com.mygdx.game.ui.ImageView;
import com.mygdx.game.ui.TextButton;
import com.mygdx.game.ui.TextView;
import com.mygdx.game.ui.UiComponent;
import com.mygdx.game.utils.DifficultyLevel;
import com.mygdx.game.utils.GameSession;
import com.mygdx.game.utils.GameSettings;
import com.mygdx.game.utils.MemoryLoader;

import java.util.ArrayList;

public class GameScreen implements Screen {

    ArrayList<UiComponent> uiComponentsList;
    ArrayList<UiComponent> uiComponentsListEndOfGame;
    ArrayList<Texture> mosquitoTextureList;
    ArrayList<Mosquito> mosquitoList;
    MyGdxGame myGdxGame;

    int aliveMosquitoesCount;
    GameSession gameSession;

    TextButton returnButton;
    TextView textViewSessionTime;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        Gdx.app.debug("GameScreen", "constructor");

        gameSession = new GameSession();
        mosquitoTextureList = new ArrayList<>();
        uiComponentsList = new ArrayList<>();
        uiComponentsListEndOfGame = new ArrayList<>();
        mosquitoList = new ArrayList<>();
        uiComponentsList.add(new ImageView(0, 0, GameSettings.SCR_WIDTH,
                GameSettings.SCR_HEIGHT, "backgrounds/gameBG.jpg"));

        uiComponentsListEndOfGame.add(new Blackout());
        uiComponentsListEndOfGame.add(new TextView(myGdxGame.largeFont.bitmapFont,
                "Our congratulations", -1, 900));
        uiComponentsListEndOfGame.add(new TextView(myGdxGame.commonFont.bitmapFont,
                "Your time: ", 300, 700));
        textViewSessionTime = new TextView(myGdxGame.commonFont.bitmapFont, "", 700, 700);
        returnButton = new TextButton(myGdxGame.secondaryFont.bitmapFont, "Return home", 300, 500);
        uiComponentsListEndOfGame.add(textViewSessionTime);
        uiComponentsListEndOfGame.add(returnButton);
    }

    @Override
    public void show() {
        Gdx.app.debug("Show", "Show");
        loadMosquitoes(MemoryLoader.loadDifficultyLevel());
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.justTouched()) {
            myGdxGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.touch = myGdxGame.camera.unproject(myGdxGame.touch);
            for (UiComponent component : uiComponentsList) {
                if (component.isVisible) component.isHit(myGdxGame.touch.x, myGdxGame.touch.y);
            }
        }

        for (Mosquito mosquito : mosquitoList) {
            if (mosquito.isAlive) mosquito.update();
        }

        ScreenUtils.clear(0, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();

        for (UiComponent component : uiComponentsList) {
            component.draw(myGdxGame);
        }

        if (gameSession.gameState == GameSession.END_OF_GAME) {
            for (UiComponent component : uiComponentsListEndOfGame) {
                component.draw(myGdxGame);
            }
        }

        myGdxGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    void loadMosquitoes(DifficultyLevel difficultyLevel) {
        mosquitoList = new ArrayList<>();
        aliveMosquitoesCount = difficultyLevel.getCountOfEnemies();

        for (int i = 0; i < 9; i++)
            mosquitoTextureList.add(new Texture("tiles/mosq" + i + ".png"));
        Texture deadMosquitoTexture = new Texture("tiles/mosq10.png");

        for (int i = 0; i < aliveMosquitoesCount; i++) {
            Mosquito mosquito = new Mosquito(mosquitoTextureList, deadMosquitoTexture,
                    difficultyLevel.getEnemySpeed(), onKillMosquitoListener);
            mosquitoList.add(mosquito);
            uiComponentsList.add(mosquito.actorImgView);
        }
    }

    Mosquito.OnKillMosquitoListener onKillMosquitoListener = new Mosquito.OnKillMosquitoListener() {
        @Override
        public void onKill() {
            Gdx.app.debug("onKill", "killed");
            aliveMosquitoesCount -= 1;
            if (aliveMosquitoesCount == 0){
                gameSession.gameState = GameSession.END_OF_GAME;
                textViewSessionTime.text = gameSession.getSessionTime();
            }
        }
    };

}
