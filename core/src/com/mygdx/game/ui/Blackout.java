package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.GameSettings;

public class Blackout extends UiComponent{

    Texture blackoutTexture;

    public Blackout() {
        super(0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);
        Pixmap pixmap = new Pixmap((int) width, (int) height, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.5f);
        pixmap.fill();
        blackoutTexture = new Texture(pixmap);
    }

    @Override
    public void draw(MyGdxGame myGdxGame) {
        myGdxGame.batch.draw(blackoutTexture, x, y, width, height);
    }
}
