package de.golfgl.gdxjamgame.oneroom.scene2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import de.golfgl.gdxjamgame.oneroom.GdxJamGame;
import de.golfgl.gdxjamgame.oneroom.model.GameLogic;
import de.golfgl.gdxjamgame.oneroom.model.Participant;

public class LocationQuizActor extends WidgetGroup {

    public static final float CROSSHAIR_FADEIN = 1f;
    public static final int ANSWER_TIME = GameLogic.MAX_ANSWER_TIME * 2;
    private final Participant participant;
    private final Image crosshair;
    private final GdxJamGame app;
    private int clickedX = -1;
    private int clickedY = -1;
    private float timeGone = 0;
    private final Label label;
    private final ParticipantActor participantActor;

    public LocationQuizActor(Participant participant, GdxJamGame app) {
        this.participant = participant;

        label = new Label("BONUS! Do you know where this gentleman was located while the party was going on?", app.labelStyle);

        addActor(label);
        label.getColor().a = 0;
        label.addAction(Actions.fadeIn(.5f, Interpolation.fade));
        label.setPosition(GdxJamGame.nativeGameWidth / 2, GdxJamGame.nativeGameHeight, Align.top);

        participantActor = new ParticipantActor(participant, app);

        participantActor.setPosition(-participantActor.getWidth(), 50);
        participantActor.addAction(Actions.moveTo(GdxJamGame.nativeGameWidth, 50, ANSWER_TIME));
        addActor(participantActor);

        crosshair = new Image(app.crosshair);
        this.app = app;
        crosshair.setRotation(MathUtils.random(360));
        crosshair.setSize(200, 200);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickedPos(x, y);
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        timeGone = timeGone + delta;

        if (timeGone > ANSWER_TIME)
            clickedPos(getStage().getWidth() / 2, getStage().getHeight() + 1000);
    }

    private void clickedPos(float x, float y) {
        if (clickedX > 0 && clickedY > 0)
            return;

        clickedX = (int) x;
        clickedY = (int) y;

        final boolean correct = participant.isNearPos(clickedX, clickedY);

        label.addAction(Actions.fadeOut(.5f, Interpolation.fade));
        participantActor.addAction(Actions.fadeOut(.5f, Interpolation.fade));

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
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        if (!correct)
                            app.wrongSound.play();
                    }
                }),
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
