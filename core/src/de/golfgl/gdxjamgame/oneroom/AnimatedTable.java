package de.golfgl.gdxjamgame.oneroom;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class AnimatedTable extends Table {
    private float delayTime;
    private float fadeInTime = 1f;

    public Cell<Actor> addAnimated(Actor a, float delayTime) {
        this.delayTime = delayTime + this.delayTime;

        a.getColor().a = 0;

        a.addAction(Actions.delay(this.delayTime,
                Actions.fadeIn(fadeInTime, Interpolation.fade)));

        return add(a);
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

    @Override
    public void clear() {
        delayTime = 0;
        super.clear();
    }
}
