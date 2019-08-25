package de.golfgl.gdxjamgame.oneroom;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class GdxJamGame extends Game {
    public static int nativeGameWidth = 1920;
    public static int nativeGameHeight = 1222;

    private AssetManager assetManager;
    public Texture backgroundFull;
    public Texture backgroundEmpty;
    public Music bgMusic;


    @Override
    public void create() {
        loadAssets();
        this.setScreen(new GameScreen(this));
    }

    private void loadAssets() {
        assetManager = new AssetManager();
        assetManager.load("serverempty.png", Texture.class);
        assetManager.load("serverfull.png", Texture.class);
        assetManager.load("bgmusic.mp3", Music.class);

        assetManager.finishLoading();

        backgroundEmpty = assetManager.get("serverempty.png", Texture.class);
        backgroundFull = assetManager.get("serverfull.png", Texture.class);
        bgMusic = assetManager.get("bgmusic.mp3", Music.class);
    }

    @Override
    public void dispose() {
        getScreen().dispose();
    }
}
