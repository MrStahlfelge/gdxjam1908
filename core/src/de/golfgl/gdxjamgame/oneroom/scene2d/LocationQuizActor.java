package de.golfgl.gdxjamgame.oneroom.scene2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import de.golfgl.gdxjamgame.oneroom.GdxJamGame;
import de.golfgl.gdxjamgame.oneroom.model.Participant;

public class LocationQuizActor extends WidgetGroup {

    public static final float CROSSHAIR_FADEIN = 1f;
    private final Participant participant;
    private final Image crosshair;
    private int clickedX = -1;
    private int clickedY = -1;

    public LocationQuizActor(Participant participant, GdxJamGame app) {
        this.participant = participant;

        crosshair = new Image(app.crosshair);
        crosshair.setRotation(MathUtils.random(360));
        crosshair.setSize(200, 200);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickedPos(x, y);
            }
        });
    }

    private void clickedPos(float x, float y) {
        if (clickedX > 0 && clickedY > 0)
            return;

        clickedX = (int) x;
        clickedY = (int) y;

        boolean correct = participant.isNearPos(clickedX, clickedY);

        addActor(crosshair);
        crosshair.setPosition(x, y, Align.center);
        crosshair.setColor(Color.BLACK);
        crosshair.setOrigin(Align.center);
        crosshair.setScale(2f);
        crosshair.getColor().a = 0;

        crosshair.addAction(Actions.fadeIn(CROSSHAIR_FADEIN, Interpolation.fade));
        crosshair.addAction(Actions.scaleTo(1, 1, CROSSHAIR_FADEIN, Interpolation.fade));
        crosshair.addAction(Actions.delay(1f, Actions.sequence(
                Actions.alpha(.5f, .5f),
                Actions.alpha(1, .5f),
                Actions.alpha(.5f, .5f),
                Actions.alpha(1f, .5f),
                Actions.parallel(Actions.color(correct ? Color.GREEN : Color.RED, .2f, Interpolation.fade),
                        Actions.moveTo(participant.getPosX() - crosshair.getWidth() / 2, participant.getPosY() - crosshair.getHeight() / 2,
                                correct ? .2f : .5f, Interpolation.fade)),
                Actions.delay(.5f),
                Actions.fadeOut(.3f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        remove();
                        onDone();
                    }
                })
        )));
    }

    protected void onDone() {
        // overriding purpose
    }

    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);
        if (stage != null)
            setSize(stage.getWidth(), stage.getHeight());
    }
}
