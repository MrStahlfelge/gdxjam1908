package de.golfgl.gdxjamgame.oneroom;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.golfgl.gdxjamgame.oneroom.model.GameLogic;

public class GdxJamGame extends Game {
    public static int nativeGameWidth = 1920;
    public static int nativeGameHeight = 1222;
    public Texture backgroundFull;
    public Texture backgroundEmpty;
    public Texture title;
    public Texture crosshair;
    public Music bgMusic;
    public Sound wrongSound;
    public Sound doneSound;
    public Label.LabelStyle labelStyle;
    public Texture bgWhite;
    public Texture noAvatar;
    public Texture circle;
    public GameLogic gameLogic;
    private AssetManager assetManager;
    private BitmapFont font;
    private Preferences prefs;

    @Override
    public void create() {
        loadAssets();
        loadData();
        prefs = Gdx.app.getPreferences("jam1908");
        this.setScreen(new GameScreen(this));
    }

    private void loadAssets() {
        assetManager = new AssetManager();
        assetManager.load("serverempty.png", Texture.class);
        assetManager.load("serverfull.png", Texture.class);
        assetManager.load("bgmusic.mp3", Music.class);
        assetManager.load("title.png", Texture.class);
        assetManager.load("font.fnt", BitmapFont.class);
        assetManager.load("noavatar.png", Texture.class);
        assetManager.load("circle.png", Texture.class);
        assetManager.load("wrong.mp3", Sound.class);
        assetManager.load("donescreen.mp3", Sound.class);
        assetManager.load("crosshair.png", Texture.class);

        assetManager.finishLoading();

        backgroundEmpty = assetManager.get("serverempty.png", Texture.class);
        backgroundFull = assetManager.get("serverfull.png", Texture.class);
        bgMusic = assetManager.get("bgmusic.mp3", Music.class);
        title = assetManager.get("title.png", Texture.class);
        font = assetManager.get("font.fnt", BitmapFont.class);
        noAvatar = assetManager.get("noavatar.png", Texture.class);
        circle = assetManager.get("circle.png", Texture.class);
        wrongSound = assetManager.get("wrong.mp3", Sound.class);
        doneSound = assetManager.get("donescreen.mp3", Sound.class);
        crosshair = assetManager.get("crosshair.png", Texture.class);

        bgMusic.setVolume(.8f);

        title.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        circle.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        noAvatar.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        crosshair.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        labelStyle = new Label.LabelStyle(font, Color.WHITE);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, .7f);
        pixmap.drawPixel(0, 0);
        bgWhite = new Texture(pixmap); //remember to dispose of later
        pixmap.dispose();
    }

    private void loadData() {
        gameLogic = new GameLogic(this);
    }

    public boolean canSkipIntro() {
        return prefs.getBoolean("introseen", false);
    }

    public void setIntroSeen() {
        prefs.putBoolean("introseen", true);
        prefs.flush();
    }

    @Override
    public void dispose() {
        getScreen().dispose();
    }
}
