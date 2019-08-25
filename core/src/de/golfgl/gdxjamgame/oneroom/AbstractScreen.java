package de.golfgl.gdxjamgame.oneroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

abstract class AbstractScreen implements Screen {
    GdxJamGame app;
    Stage stage;

    AbstractScreen(final GdxJamGame app) {
        this.app = app;
        FitViewport viewport = new FitViewport(GdxJamGame.nativeGameWidth, GdxJamGame.nativeGameHeight);
        stage = new Stage(viewport) {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                // enable Music
                if (!app.bgMusic.isPlaying()) {
                    app.bgMusic.play();
                    app.bgMusic.setLooping(true);
                }

                return super.touchDown(screenX, screenY, pointer, button);
            }
        };
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        if (app.bgMusic.isPlaying())
            app.bgMusic.pause();
    }

    @Override
    public void resume() {
        if (app.bgMusic.isPlaying())
            app.bgMusic.play();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
