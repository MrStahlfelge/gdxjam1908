package de.golfgl.gdxjamgame.oneroom.scene2d;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.SnapshotArray;

public class AnimatedTable extends Table {
    private float delayTime;
    private float fadeInTime = 1f;

    public <T extends Actor> Cell<T> addAnimated(T a, float delayTime) {
        this.delayTime = delayTime + this.delayTime;

        a.getColor().a = 0;

        a.addAction(Actions.delay(this.delayTime,
                Actions.fadeIn(fadeInTime, Interpolation.fade)));

        return add(a);
    }

    public Cell<Label> addMultilineLabelAnimated(Label lbl, float delayTime) {
        lbl.setWrap(true);

        return addAnimated(lbl, delayTime).fillX();
    }

    public float getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(float delayTime) {
        this.delayTime = delayTime;
    }

    public float getFadeInTime() {
        return fadeInTime;
    }

    public void setFadeInTime(float fadeInTime) {
        this.fadeInTime = fadeInTime;
    }

    public void clearAnimated(float fadeOutTime, final Runnable doAfter) {
        setTransform(true);
        setOrigin(Align.center);
        final Touchable oldValue = getTouchable();
        setTouchable(Touchable.disabled);

        addAction(Actions.sequence(Actions.scaleTo(1f, 0f, fadeOutTime, Interpolation.fade),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        setTransform(false);
                        setTouchable(oldValue);
                        clear();
                        setScale(1f);
                        if (doAfter != null)
                            doAfter.run();
                    }
                })));
    }

    public void fadeOutAllChildrenAndClear(float fadeOutTime, final Runnable doAfter) {
        SnapshotArray<Actor> children = getChildren();
        for (Actor child: children)
            child.addAction(Actions.fadeOut(fadeOutTime, Interpolation.fade));

        addAction(Actions.delay(fadeOutTime, Actions.run(new Runnable() {
            @Override
            public void run() {
                clear();
                if (doAfter != null)
                    doAfter.run();
            }
        })));
    }

    @Override
    public void clear() {
        delayTime = 0;
        super.clear();
    }
}
