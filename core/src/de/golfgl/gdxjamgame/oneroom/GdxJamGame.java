package de.golfgl.gdxjamgame.oneroom;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class GdxJamGame extends Game {
    public static int nativeGameWidth = 1920;
    public static int nativeGameHeight = 1222;
    public Texture backgroundFull;
    public Texture backgroundEmpty;
    public Texture title;
    public Music bgMusic;
    public Label.LabelStyle labelStyle;
    public Texture bgWhite;
    private AssetManager assetManager;
    private BitmapFont font;

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
        assetManager.load("title.png", Texture.class);
        assetManager.load("font.fnt", BitmapFont.class);

        assetManager.finishLoading();

        backgroundEmpty = assetManager.get("serverempty.png", Texture.class);
        backgroundFull = assetManager.get("serverfull.png", Texture.class);
        bgMusic = assetManager.get("bgmusic.mp3", Music.class);
        title = assetManager.get("title.png", Texture.class);
        font = assetManager.get("font.fnt", BitmapFont.class);

        title.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        labelStyle = new Label.LabelStyle(font, Color.WHITE);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, .5f);
        pixmap.drawPixel(0, 0);
        bgWhite = new Texture(pixmap); //remember to dispose of later
        pixmap.dispose();
    }

    @Override
    public void dispose() {
        getScreen().dispose();
    }
}
