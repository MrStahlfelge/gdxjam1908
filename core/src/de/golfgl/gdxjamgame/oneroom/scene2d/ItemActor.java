package de.golfgl.gdxjamgame.oneroom.scene2d;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;

import de.golfgl.gdxjamgame.oneroom.GdxJamGame;
import de.golfgl.gdxjamgame.oneroom.model.Item;

public class ItemActor extends Table {

    public ItemActor(Item item, GdxJamGame app) {
        this(item, app, true);
    }

    public ItemActor(Item item, GdxJamGame app, boolean withDescription) {
        Image imageActor = new Image(item.getImage());
        Image background = new Image(app.circle);

        imageActor.setScaling(Scaling.none);

        Stack stack = new Stack();

        stack.add(background);
        stack.add(imageActor);
        add(stack).size(300);
        String description = item.getDescription();
        if (withDescription && description != null) {
            row();
            add(new Label(description, app.labelStyle)).pad(30);
        }
        pack();
    }
}
