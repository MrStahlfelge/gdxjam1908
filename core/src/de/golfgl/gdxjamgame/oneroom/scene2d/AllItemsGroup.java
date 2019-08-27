package de.golfgl.gdxjamgame.oneroom.scene2d;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.IntSet;

import de.golfgl.gdxjamgame.oneroom.GdxJamGame;
import de.golfgl.gdxjamgame.oneroom.model.GameLogic;
import de.golfgl.gdxjamgame.oneroom.model.Item;

public class AllItemsGroup extends AnimatedTable {

    public static final float SCALE = .5f;

    public AllItemsGroup(GdxJamGame app, float animationDelay) {
        setDelayTime(animationDelay);
        setFadeInTime(0);

        IntSet assignedItems = app.gameLogic.getAssignedItems();
        defaults().pad(20);
        for (int i = 0; i < GameLogic.NUM_ITEMS_SHOWN; i++) {
            Table itemAndOwner = new Table();
            Item item = app.gameLogic.getItem(i);
            itemAndOwner.add(new ItemActor(item, app, false));
            if (assignedItems.contains(i)) {
                itemAndOwner.row().padTop(50);
                ParticipantActor participantActor = new ParticipantActor(app.gameLogic.getParticipant(item.getOwner()),
                        app, false);
                itemAndOwner.add(participantActor);
            }

            addAnimated(itemAndOwner, .5f).uniform().top();
        }

        setScale(SCALE);
        setTransform(true);
    }

    @Override
    public void layout() {
        super.layout();
        setOrigin(Align.center);
    }

    @Override
    public float getPrefWidth() {
        return super.getPrefWidth() * SCALE;
    }

    @Override
    public float getPrefHeight() {
        return super.getPrefHeight() * SCALE;
    }

    @Override
    public float getMinWidth() {
        return super.getMinWidth() * SCALE;
    }

    @Override
    public float getMinHeight() {
        return super.getMinHeight() * SCALE;
    }
}
