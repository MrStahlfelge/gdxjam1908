package de.golfgl.gdxjamgame.oneroom.scene2d;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;

import de.golfgl.gdxjamgame.oneroom.GdxJamGame;
import de.golfgl.gdxjamgame.oneroom.model.Item;
import de.golfgl.gdxjamgame.oneroom.model.Participant;

public class ItemQuizGroup extends WidgetGroup {

    public static final float DELAY_SUGGESTIONS = 2f;
    public static final float DELAY_BETWEEN_PARTICIPANTS = .2f;
    public static final float WAIT_BEFORE_SHOWN = 1f;
    private final GdxJamGame app;
    private final Item item;
    private final ParticipantActor[] participant;
    private final ItemActor itemActor;
    private int participantClicked = -1;
    private int correctParticipant;
    private float timePassed;


    public ItemQuizGroup(Item item, GdxJamGame app) {
        this.app = app;
        this.item = item;

        Array<Participant> participants = app.gameLogic.getItemParticipantSuggestions(item);
        itemActor = new ItemActor(item, app);
        addActor(itemActor);

        participant = new ParticipantActor[participants.size];

        for (int i = 0; i < participants.size; i++) {
            if (participants.get(i).getName().equals(item.getOwner()))
                correctParticipant = i;

            participant[i] = new ParticipantActor(participants.get(i), app);
            addActor(participant[i]);
            final int finalI = i;
            participant[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    clickedParticipant(finalI);
                }
            });
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Timeout, hehehe
        timePassed = timePassed + delta;

        if (participantClicked < 0 && timePassed > 5 + DELAY_SUGGESTIONS) {
            clickedParticipant(participant.length);
        }
    }

    private void clickedParticipant(int clickedIndex) {
        if (participantClicked >= 0)
            return;

        participantClicked = clickedIndex;

        for (int i = 0; i < participant.length; i++) {
            DelayAction delay = Actions.delay(i * DELAY_BETWEEN_PARTICIPANTS, Actions.fadeOut(.3f));
            if (i != clickedIndex) {
                participant[i].addAction(delay);
            } else
                participant[i].getLabel().addAction(delay);
        }

        if (clickedIndex >= participant.length) {
            addAction(Actions.delay(1f, Actions.run(new Runnable() {
                @Override
                public void run() {
                    onAnswered(false);
                }
            })));
            return;
        }

        float waitTime;
        if (clickedIndex == correctParticipant) {
            Image imageActor = participant[clickedIndex].getImageActor();
            imageActor.setOrigin(Align.center);
            imageActor.addAction(Actions.delay(WAIT_BEFORE_SHOWN, Actions.rotateBy(3600, 1.5f, Interpolation.fade)));
            waitTime = WAIT_BEFORE_SHOWN + 1f;
        } else {
            float duration = 0.033f;
            participant[clickedIndex].addAction(Actions.delay(WAIT_BEFORE_SHOWN, Actions.sequence(
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            app.wrongSound.play();
                        }
                    }),
                    Actions.moveBy(50, 0, duration, Interpolation.linear),
                    Actions.moveBy(-50, 0, duration, Interpolation.linear),
                    Actions.moveBy(-40, 0, duration, Interpolation.linear),
                    Actions.moveBy(40, 0, duration, Interpolation.linear),
                    Actions.moveBy(20, 0, duration, Interpolation.linear),
                    Actions.moveBy(-20, 0, duration, Interpolation.linear),
                    Actions.moveBy(-10, 0, duration, Interpolation.linear),
                    Actions.moveBy(10, 0, duration, Interpolation.linear))
            ));
            waitTime = WAIT_BEFORE_SHOWN + duration * 8 + .5f;
        }

        participant[clickedIndex].addAction(Actions.delay(waitTime, Actions.sequence(
                Actions.moveTo(participant[clickedIndex].getX(), -participant[clickedIndex].getHeight(), .5f, Interpolation.fade),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        onAnswered(correctParticipant == participantClicked);
                    }
                }))));
    }

    protected void onAnswered(boolean correct) {

    }

    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);

        if (stage != null) {
            // lay everything out
            itemActor.setPosition(-itemActor.getWidth(), stage.getHeight() * 3 / 4 - itemActor.getHeight());
            itemActor.addAction(Actions.moveTo(stage.getWidth() / 2 - itemActor.getWidth() / 2, itemActor.getY(), 1f, Interpolation.fade));
            itemActor.addAction(Actions.delay(2f, Actions.moveBy(stage.getWidth(), 0, 1f, Interpolation.fade)));

            for (int i = 0; i < participant.length; i++) {
                float posX = (stage.getWidth() - participant[i].getWidth()) * (i + 1) / (participant.length + 1);
                float posY = stage.getHeight() * 1 / 4 - participant[i].getHeight() / 2;
                participant[i].setPosition(posX, -participant[i].getHeight());
                participant[i].addAction(Actions.delay(DELAY_SUGGESTIONS + i * DELAY_BETWEEN_PARTICIPANTS,
                        Actions.moveTo(posX, posY, .5f, Interpolation.fade)));
            }
        }
    }

    static class ParticipantActor extends Table {

        private final Image imageActor;
        private final Label label;

        public ParticipantActor(Participant participant, GdxJamGame app) {
            imageActor = new Image(participant.getAvatar());
            Image background = new Image(app.circle);

            imageActor.setScaling(Scaling.none);

            Stack stack = new Stack();

            stack.add(background);
            stack.add(imageActor);
            add(stack).size(250);
            row();
            label = new Label(participant.getName(), app.labelStyle);
            add(label).pad(30);
            pack();
        }

        public Image getImageActor() {
            return imageActor;
        }

        public Label getLabel() {
            return label;
        }
    }

    static class ItemActor extends Table {

        public ItemActor(Item item, GdxJamGame app) {
            Image imageActor = new Image(item.getImage());
            Image background = new Image(app.circle);

            imageActor.setScaling(Scaling.none);

            Stack stack = new Stack();

            stack.add(background);
            stack.add(imageActor);
            add(stack).size(300);
            row();
            add(new Label(item.getDescription(), app.labelStyle)).pad(30);
            pack();
        }
    }
}
