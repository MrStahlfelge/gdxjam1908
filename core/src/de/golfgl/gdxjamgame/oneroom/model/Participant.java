package de.golfgl.gdxjamgame.oneroom.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;

import de.golfgl.gdxjamgame.oneroom.GdxJamGame;

public class Participant {
    private String name;
    private Texture avatar;
    private int posX1;
    private int posX2;
    private int posY1;
    private int posY2;

    public Participant(JsonValue value, GdxJamGame app) {
        name = value.getString("name");

        String avatarFile = value.getString("avatar");
        if (avatarFile != null) {
            avatar = new Texture(Gdx.files.internal("participants/" + avatarFile));
            avatar.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else
            avatar = app.noAvatar;

        JsonValue position = value.get("pos");
        if (position != null) {
            posX1 = position.getInt(0);
            posY1 = position.getInt(1);
            posX2 = position.getInt(2);
            posY2 = position.getInt(3);
        }
    }

    public String getName() {
        return name;
    }

    public Texture getAvatar() {
        return avatar;
    }

    public int getPosX1() {
        return posX1;
    }

    public int getPosX2() {
        return posX2;
    }

    public int getPosY1() {
        return posY1;
    }

    public int getPosY2() {
        return posY2;
    }

    public boolean hasPosition() {
        return posX2 > posX1 && posY2 > posY1;
    }
}
