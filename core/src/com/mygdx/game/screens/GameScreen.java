package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.actors.Butterfly;
import com.mygdx.game.actors.Mosquito;
import com.mygdx.game.ui.Blackout;
import com.mygdx.game.ui.ImageView;
import com.mygdx.game.ui.ProgressBar;
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
    ArrayList<UiComponent> uiComponentsListGameOver;
    ArrayList<Texture> mosquitoTextureList;
    ArrayList<Texture> butterflyTextureList;
    ArrayList<Mosquito> mosquitoList;
    ArrayList<Butterfly> butterflyList;
    MyGdxGame myGdxGame;

    int aliveMosquitoesCount;
    GameSession gameSession;

    TextButton returnButton;
    TextView textViewSessionTime;
    ProgressBar hitPointsBar;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        uiComponentsList = new ArrayList<>();
        uiComponentsListEndOfGame = new ArrayList<>();
        uiComponentsListGameOver = new ArrayList<>();

        butterflyTextureList = new ArrayList<>();
        mosquitoTextureList = new ArrayList<>();
        butterflyList = new ArrayList<>();
        mosquitoList = new ArrayList<>();

        uiComponentsList.add(new ImageView(0, 0, GameSettings.SCR_WIDTH,
                GameSettings.SCR_HEIGHT, "backgrounds/gameBG.jpg"));
        hitPointsBar = new ProgressBar(700, 30,
                MemoryLoader.loadDifficultyLevel().getUserHitPoints(), 100, 30, "Hit points");

        uiComponentsListGameOver.add(new Blackout());
        uiComponentsListEndOfGame.add(new Blackout());
        uiComponentsListEndOfGame.add(new TextView(myGdxGame.largeFont.bitmapFont,
                "Our congratulations", -1, 900));
        uiComponentsListGameOver.add(new TextView(myGdxGame.largeFont.bitmapFont,
                "Game over", -1, 900));
        uiComponentsListEndOfGame.add(new TextView(myGdxGame.commonFont.bitmapFont,
                "Your time: ", 300, 700));

        textViewSessionTime = new TextView(myGdxGame.commonFont.bitmapFont, "", 700, 700);
        returnButton = new TextButton(myGdxGame.secondaryFont.bitmapFont, "Return home", 300, 500);
        uiComponentsList.add(hitPointsBar);
        uiComponentsListEndOfGame.add(textViewSessionTime);
        uiComponentsListEndOfGame.add(returnButton);
        uiComponentsListGameOver.add(returnButton);
    }

    @Override
    public void show() {
        gameSession = new GameSession();
        hitPointsBar.setMaxValue(MemoryLoader.loadDifficultyLevel().getUserHitPoints());
        loadActors(MemoryLoader.loadDifficultyLevel());
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

        for(Butterfly butterfly: butterflyList)
            butterfly.update();

        hitPointsBar.setValue(gameSession.hitPointsLeft);

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

        if (gameSession.gameState == GameSession.GAME_OVER) {
            for (UiComponent component : uiComponentsListGameOver) {
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

    void loadActors(DifficultyLevel difficultyLevel) {
        mosquitoList.clear();
        butterflyList.clear();
        mosquitoTextureList.clear();
        butterflyTextureList.clear();
        aliveMosquitoesCount = difficultyLevel.getCountOfEnemies();

        for (int i = 0; i < 5; i++)
            butterflyTextureList.add(new Texture("tiles/butterFly" + i + ".png"));
        for (int i = 0; i < 9; i++)
            mosquitoTextureList.add(new Texture("tiles/mosq" + i + ".png"));
        Texture deadMosquitoTexture = new Texture("tiles/mosq10.png");


        for (int i = 0; i < aliveMosquitoesCount; i++) {
            Mosquito mosquito = new Mosquito(mosquitoTextureList, deadMosquitoTexture,
                    difficultyLevel.getEnemySpeed(), onKillMosquitoListener);
            mosquitoList.add(mosquito);

            if (i % 2 == 0) {
                Butterfly butterfly = new Butterfly(butterflyTextureList,
                        difficultyLevel.getEnemySpeed(), onHitButterflyListener);
                butterflyList.add(butterfly);
                uiComponentsList.add(butterfly.actorImgView);
            }

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

    Butterfly.OnHitButterflyListener onHitButterflyListener = new Butterfly.OnHitButterflyListener() {
        @Override
        public void onHit() {
            gameSession.getDamage();

        }
    };

}
