package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGdxGame;

public class ProgressBar extends UiComponent{

    Texture backFullBarTexture;
    Texture frontBarTexture;

    private final int maxWidth;
    private double maxValue;

    private final String barTitle;

    public ProgressBar(int maxWidth, int height, double maxValue, int positionX, int positionY, String barTitle) {
        super(positionX, positionY, maxWidth, height);
        this.height = height;
        this.maxWidth = maxWidth;
        this.maxValue = maxValue;
        this.width = maxWidth;
        this.barTitle = barTitle;

        initBars();
    }

    public void setMaxValue(double value) {
        maxValue = value;
    }

    public void setValue(double value) {
        if (value < 0) width = 0;
        else if (value > maxValue) width = maxWidth;
        else width = (int) (maxWidth * (value / maxValue));
    }

    @Override
    public void draw(MyGdxGame myGdxGame) {
        myGdxGame.batch.draw(backFullBarTexture, x, y, maxWidth, height);
        myGdxGame.batch.draw(frontBarTexture, x, y, width, height);
        myGdxGame.commonFont.bitmapFont.draw(myGdxGame.batch, barTitle, x + maxWidth + 15, y + 40);
    }

    void initBars() {
        Pixmap pixmap = createProceduralPixmap(1, 1, 0, 1, 0);
        Pixmap pixmap2 = createProceduralPixmap(1, 1, 1, 0, 0);

        backFullBarTexture = new Texture(pixmap2);
        frontBarTexture = new Texture(pixmap);
    }

    private Pixmap createProceduralPixmap(int width, int height, int r, int g, int b) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(r, g, b, 1);
        pixmap.fill();
        return pixmap;
    }

}