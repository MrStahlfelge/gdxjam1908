package de.golfgl.gdxjamgame.oneroom.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.HashMap;

import de.golfgl.gdxjamgame.oneroom.GdxJamGame;

public class GameLogic {
    private final GdxJamGame app;
    private HashMap<String, Participant> participantsMap = new HashMap<>();
    private Array<Item> items = new Array<>();

    public GameLogic(GdxJamGame app) {
        this.app = app;

        parseFile(Gdx.files.internal("data.json"));

        items.shuffle();

        // TODO: use only 9 items, delete all others

        // TODO: 3 participants with position
    }

    private void parseFile(FileHandle file) {
        JsonValue json = new JsonReader().parse(file);

        JsonValue participants = json.get("participants");
        for (JsonValue participant = participants.child; participant != null; participant = participant.next) {
            Participant ptcpt = new Participant(participant, app);
            participantsMap.put(ptcpt.getName(), ptcpt);
        }

        JsonValue items = json.get("items");
        for (JsonValue itemJson = items.child; itemJson != null; itemJson = itemJson.next) {
            Item item = new Item(itemJson, app);
            if (participantsMap.containsKey(item.getOwner()))
                this.items.add(item);
            else
                Gdx.app.error("DATA", "Owner not known: " + item.getOwner());
        }
    }
}
