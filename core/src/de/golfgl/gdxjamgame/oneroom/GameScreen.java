package de.golfgl.gdxjamgame.oneroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameScreen extends AbstractScreen {

    private final Image imFull;
    private final AnimatedTable mainTable;

    public GameScreen(GdxJamGame app) {
        super(app);

        Stack stack = new Stack();

        stack.setSize(stage.getWidth(), stage.getHeight());

        mainTable = new AnimatedTable();
        mainTable.pad(50);
        mainTable.setFillParent(true);
        Image imEmpty = new Image(new TextureRegionDrawable(app.backgroundEmpty));
        imFull = new Image(new TextureRegionDrawable(app.backgroundFull));

        mainTable.setTouchable(Touchable.enabled);
        stack.add(imEmpty);
        stack.add(imFull);
        stack.add(mainTable);

        fillTableWithTitle(app);

        stage.addActor(stack);


    }

    private void fillTableWithTitle(GdxJamGame app) {
        Image gameTitle = new Image(new TextureRegionDrawable(app.title));

        gameTitle.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainTable.clearAnimated(.3f, null);
            }
        });

        mainTable.addAnimated(gameTitle, 0).expandY();
        mainTable.row();
        mainTable.addAnimated(new Label("Submission by MrStahlfelge. Tap the logo to continue.", app.labelStyle), .5f);
        mainTable.row().padTop(20);
        Label communityLabel = new Label("Or tap here to join the best libGDX community.", app.labelStyle);
        communityLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://discord.gg/9X4MMfH");
            }
        });
        mainTable.addAnimated(communityLabel, 2f);
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
