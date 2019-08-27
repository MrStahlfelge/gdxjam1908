package de.golfgl.gdxjamgame.oneroom.scene2d;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;

import de.golfgl.gdxjamgame.oneroom.GdxJamGame;
import de.golfgl.gdxjamgame.oneroom.model.Participant;

public class ParticipantActor extends Table {

    private final Image imageActor;
    private final Label label;

    public ParticipantActor(Participant participant, GdxJamGame app) {
        this(participant, app, true);
    }

    public ParticipantActor(Participant participant, GdxJamGame app, boolean withName) {
        imageActor = new Image(participant.getAvatar());
        Image background = new Image(app.circle);

        imageActor.setScaling(Scaling.none);

        Stack stack = new Stack();

        stack.add(background);
        stack.add(imageActor);
        add(stack).size(250);
        if (withName) {
            row();
            label = new Label(participant.getName(), app.labelStyle);
            add(label).pad(30);
            pack();
        } else
            label = null;
    }

    public Image getImageActor() {
        return imageActor;
    }

    public Label getLabel() {
        return label;
    }
}
