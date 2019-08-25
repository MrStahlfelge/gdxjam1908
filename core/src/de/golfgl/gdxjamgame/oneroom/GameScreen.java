package de.golfgl.gdxjamgame.oneroom;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen extends AbstractScreen {

    public GameScreen(GdxJamGame app) {
        super(app);
        FitViewport viewport = new FitViewport(GdxJamGame.nativeGameWidth, GdxJamGame.nativeGameHeight);
        stage = new Stage(viewport);
    }

    @Override
    public void show() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);
    }

}
