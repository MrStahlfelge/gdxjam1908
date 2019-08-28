package de.golfgl.gdxjamgame.oneroom.scene2d;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;

import de.golfgl.gdxjamgame.oneroom.GdxJamGame;
import de.golfgl.gdxjamgame.oneroom.model.Participant;

public class ParticipantsGroup extends ScaledAnimatedTable {
    public ParticipantsGroup(GdxJamGame app, float animationDelay) {
        setDelayTime(animationDelay);
        setFadeInTime(0);

        Array<Participant> correctLocations = app.gameLogic.getCorrectLocations();
        defaults().pad(20);
        for (Participant p : correctLocations) {
            ParticipantActor participantActor = new ParticipantActor(p, app, false);

            addAnimated(participantActor, .5f).uniform().top();
        }

        setTouchable(Touchable.disabled);
    }
}
