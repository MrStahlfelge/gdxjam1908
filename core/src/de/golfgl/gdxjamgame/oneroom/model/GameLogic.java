package de.golfgl.gdxjamgame.oneroom.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;

import de.golfgl.gdxjamgame.oneroom.GdxJamGame;

public class GameLogic {
    public static final int MAX_ANSWER_TIME = 5;
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

    public Participant getParticipant(String name) {
        return participantsMap.get(name);
    }

    public Item getNextItem() {
        return items.get(0);
    }

    public Array<Participant> getItemParticipantSuggestions(Item item) {
        Array<Participant> participants = new Array<>(3);
        ArrayList<Participant> allParticipants = new ArrayList(participantsMap.values());

        participants.add(participantsMap.get(item.getOwner()));

        while (participants.size < 3) {
            Participant newSuggestion = allParticipants.get(MathUtils.random(0, allParticipants.size() - 1));
            if (!participants.contains(newSuggestion, true))
                participants.add(newSuggestion);
        }

        participants.shuffle();

        return participants;
    }
}
