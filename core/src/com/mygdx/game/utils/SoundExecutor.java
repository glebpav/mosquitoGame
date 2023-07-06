package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class SoundExecutor {

    static Sound backSound = Gdx.audio.newSound(Gdx.files.internal("sounds/background.mp3"));
    static Sound[] mosquitoesSounds = {
            Gdx.audio.newSound(Gdx.files.internal("sounds/mosq0.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("sounds/mosq1.mp3"))
    };

    public static void playBackSound() {
        long soundId = backSound.play();
        backSound.setLooping(soundId, true);
    }

    public static void stopPlaying() {
        backSound.stop();
    }

    public static void playMosquitoSound() {
        mosquitoesSounds[MathUtils.random(0, mosquitoesSounds.length - 1)].play();
    }


}
