package de.golfgl.gdxjamgame.oneroom.scene2d;

import com.badlogic.gdx.utils.Align;

class ScaledAnimatedTable extends AnimatedTable {
    public static final float SCALE = .4f;

    public ScaledAnimatedTable() {
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
