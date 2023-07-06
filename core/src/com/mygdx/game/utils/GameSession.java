package com.mygdx.game.utils;

import com.badlogic.gdx.utils.TimeUtils;

public class GameSession {

    public static final int PLAY_GAME = 0;
    public static final int END_OF_GAME = 1;
    public static final int GAME_OVER = 2;

    public int hitPointsLeft;
    public int gameState;
    long startTime;

    public GameSession() {
        gameState = PLAY_GAME;
        startTime = TimeUtils.millis();
        hitPointsLeft = MemoryLoader.loadDifficultyLevel().getUserHitPoints();
    }

    public void getDamage() {
        hitPointsLeft = Math.max(hitPointsLeft - GameSettings.BUTTERFLY_DAMAGE, 0);
        if (hitPointsLeft == 0) {
            gameState = GAME_OVER;
        }
    }

    public String getSessionTime() {
        long sessionTime = TimeUtils.millis() - startTime;
        String min = "" + sessionTime / 1000 / 60 / 10 + sessionTime / 1000 / 60 % 10;
        String sec = "" + sessionTime / 1000 % 60 / 10 + sessionTime / 1000 % 60 % 10;
        return min + ":" + sec;
    }

}
