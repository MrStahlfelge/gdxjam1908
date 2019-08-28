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

    public boolean hasPosition() {
        return posX2 > posX1 && posY2 > posY1;
    }

    public boolean isNearPos(int x, int y) {
        int posX2 = this.posX2;
        int posX1 = this.posX1;
        int posY2 = this.posY2;
        int posY1 = this.posY1;

        // enlarge the target area for our smallest participants
        if (posX2 - posX1 < 250) {
            int addX = (250 - posX2 + posX1) / 2;
            posX1 = posX1 - addX;
            posX2 = posX2 + addX;
        }
        if (posY2 - posY1 < 250) {
            int addY = (250 - posY2 + posY1) / 2;
            posY1 = posY1 - addY;
            posY2 = posY2 + addY;
        }

        y = GdxJamGame.nativeGameHeight - y;
        return (posY1 <= y && posY2 >= y && posX1 <= x && posX2 >= x);
    }

    public int getPosX(int x) {
        if (x > posX1 && x < posX2)
            return x;
        else
            return getPosCenterX();
    }

    public int getPosCenterX() {
        return posX1 + (posX2 - posX1) / 2;
    }

    public int getPosY(int y) {
        int thisY = GdxJamGame.nativeGameHeight - y;

        if (thisY > posY1 && thisY < posY2)
            return y;
        else
            return getPosCenterY();
    }

    public int getPosCenterY() {
        return GdxJamGame.nativeGameHeight - (posY1 + (posY2 - posY1) / 2);
    }

}
