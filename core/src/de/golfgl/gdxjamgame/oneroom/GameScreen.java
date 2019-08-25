package de.golfgl.gdxjamgame.oneroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameScreen extends AbstractScreen {

    public GameScreen(GdxJamGame app) {
        super(app);

        Stack stack = new Stack();

        stack.setSize(stage.getWidth(), stage.getHeight());

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        Image imEmpty = new Image(new TextureRegionDrawable(app.backgroundEmpty));
        Image imFull = new Image(new TextureRegionDrawable(app.backgroundFull));

        mainTable.setTouchable(Touchable.enabled);
        stack.add(imEmpty);
        stack.add(imFull);
        stack.add(mainTable);

        imFull.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(2f), Actions.fadeIn(2f))));

        stage.addActor(stack);


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }
}
