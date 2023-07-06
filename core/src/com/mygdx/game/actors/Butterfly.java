package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.ui.UiComponent;
import com.mygdx.game.utils.GameSettings;

import java.util.ArrayList;

public class Butterfly extends Character {

    Butterfly.OnHitButterflyListener onHitButterflyListener;

    public Butterfly(ArrayList<Texture> texturesArray, float velocity,
                     Butterfly.OnHitButterflyListener onHitButterflyListener) {
        super(texturesArray);
        Gdx.app.debug("Mosquito", String.valueOf(velocity));
        x = GameSettings.SCR_WIDTH / 2 - width / 2;
        y = GameSettings.SCR_HEIGHT / 2 - height / 2;
        velocityX = MathUtils.random(-velocity, velocity);
        velocityY = (float) ((MathUtils.random(0, 5) % 2 - 0.5) * 2 * Math.sqrt(velocity * velocity - velocityX * velocityX));
        this.onHitButterflyListener = onHitButterflyListener;
        actorImgView.setOnClickListener(butterflyOnClickListener);
    }

    UiComponent.OnClickListener butterflyOnClickListener = new UiComponent.OnClickListener() {
        @Override
        public void onClicked() {
            onHitButterflyListener.onHit();
        }
    };

    public interface OnHitButterflyListener {
        void onHit();
    }

}
