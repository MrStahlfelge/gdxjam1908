package de.golfgl.gdxjamgame.oneroom.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;

import de.golfgl.gdxjamgame.oneroom.GdxJamGame;

class Item {
    private String description;
    private String owner;
    private Texture image;

    public Item(JsonValue value, GdxJamGame app) {
        description = value.getString("description");

        String imageFile = value.getString("image");
        if (imageFile != null)
            image = new Texture(Gdx.files.internal(imageFile));
        else
            image = app.noAvatar;

        owner = value.getString("owner");
    }

    public String getDescription() {
        return description;
    }

    public String getOwner() {
        return owner;
    }

    public Texture getImage() {
        return image;
    }
}
