package de.golfgl.gdxjamgame.oneroom;

import com.badlogic.gdx.Game;

public class GdxJamGame extends Game {
    public static int nativeGameWidth = 1920;
    public static int nativeGameHeight = 1222;

    @Override
    public void create() {
        loadAssets();
        this.setScreen(new GameScreen(this));
    }

    private void loadAssets() {

    }

    @Override
    public void dispose() {
        getScreen().dispose();
    }
}
