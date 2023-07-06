package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.ui.ImageView;
import com.mygdx.game.ui.TextButton;
import com.mygdx.game.ui.TextView;
import com.mygdx.game.ui.UiComponent;
import com.mygdx.game.utils.DifficultyLevel;
import com.mygdx.game.utils.GameSettings;
import com.mygdx.game.utils.MemoryLoader;
import com.mygdx.game.utils.SoundExecutor;
import com.sun.tools.sjavac.Log;

import java.util.ArrayList;

public class SettingsScreen implements Screen {

    ArrayList<UiComponent> uiComponentsList;
    MyGdxGame myGdxGame;
    TextView difficultyLabel;
    TextButton difficultyButton;
    TextButton soundsButton;
    TextButton resetButton;

    public SettingsScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        uiComponentsList = new ArrayList<>();

        ImageView background = new ImageView(0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT, "backgrounds/settingsBG.jpg");
        TextView title = new TextView(myGdxGame.largeFont.bitmapFont, "Settings", -1, 950);
        ImageView returnButton = new ImageView(200, 850, 100, 100, "icons/backIcon.png");
        returnButton.setOnClickListener(onReturnButtonClickListener);

        difficultyLabel = new TextView(myGdxGame.secondaryFont.bitmapFont, getDifficultyLabelText(MemoryLoader.loadDifficultyLevel()), 650, 700);
        difficultyButton = new TextButton(myGdxGame.commonFont.bitmapFont, "Change difficulty", 200, 700);
        difficultyButton.setOnClickListener(onChangeDifficultyClickListener);

        soundsButton = new TextButton(myGdxGame.commonFont.bitmapFont, getSoundButtonText(), 200, 600);
        soundsButton.setOnClickListener(onChangeMusicClickListener);

        resetButton = new TextButton(myGdxGame.commonFont.bitmapFont, "Clear all saves", 200, 500);

        uiComponentsList.add(background);
        uiComponentsList.add(title);
        uiComponentsList.add(difficultyLabel);
        uiComponentsList.add(returnButton);
        uiComponentsList.add(difficultyButton);
        uiComponentsList.add(soundsButton);
        uiComponentsList.add(resetButton);
    }

    @Override
    public void show() {

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

        ScreenUtils.clear(0, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();

        for (UiComponent component : uiComponentsList) {
            component.draw(myGdxGame);
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

    private String getSoundButtonText() {
        boolean musicState = MemoryLoader.loadMusicState();
        if (musicState) return "Turn off music";
        else return "Turn on music";
    }

    private String getDifficultyLabelText(DifficultyLevel difficultyLevel) {
        switch (difficultyLevel) {
            case EASY:
                return "(easy)";
            case HARD:
                return "(hard)";
            case MEDIUM:
                return "(medium)";
            default:
                return "(error)";
        }
    }

    UiComponent.OnClickListener onReturnButtonClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            Gdx.app.debug("onClicked", "onReturnButtonClicked");
            myGdxGame.setScreen(myGdxGame.menuScreen);
        }
    };

    UiComponent.OnClickListener onChangeDifficultyClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            Gdx.app.debug("onClicked", "onChangeDifficultyClicked");
            DifficultyLevel difficultyLevel = MemoryLoader.loadDifficultyLevel();
            switch (difficultyLevel) {
                case EASY:
                    difficultyLevel = DifficultyLevel.MEDIUM;
                    break;
                case MEDIUM:
                    difficultyLevel = DifficultyLevel.HARD;
                    break;
                case HARD:
                    difficultyLevel = DifficultyLevel.EASY;
                    break;
            }
            difficultyLabel.text = getDifficultyLabelText(difficultyLevel);
            MemoryLoader.saveDifficultyLevel(difficultyLevel);

        }
    };

    UiComponent.OnClickListener onChangeMusicClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            boolean isMusicOn = MemoryLoader.loadMusicState();
            MemoryLoader.saveMusicState(!isMusicOn);
            isMusicOn = !isMusicOn;
            soundsButton.text = getSoundButtonText();
            if (isMusicOn) SoundExecutor.playBackSound();
            else SoundExecutor.stopPlaying();
        }
    };
}
