package de.golfgl.gdxjamgame.oneroom.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;

import de.golfgl.gdxjamgame.oneroom.GdxJamGame;

public class GameLogic {
    public static final int MAX_ANSWER_TIME = 5;
    public static final int NUM_ITEMS_SHOWN = 1;
    private final GdxJamGame app;
    private HashMap<String, Participant> participantsMap = new HashMap<>();
    private Array<Item> items = new Array<>();

    private int currentShownItem;
    private int timeOuts;
    private IntSet correctAnswers = new IntSet();

    public GameLogic(GdxJamGame app) {
        this.app = app;

        parseFile(Gdx.files.internal("data.json"));

        reset();
    }

    public void reset() {
        items.shuffle();
        timeOuts = 0;
        correctAnswers.clear();
        currentShownItem = 0;
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

    public Item getItem() {
        if (currentShownItem < NUM_ITEMS_SHOWN)
            return items.get(currentShownItem);
        else
            return null;
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

    public void onItemTimeOut() {
        timeOuts++;
        currentShownItem++;
    }

    public void onItemChoosen(boolean correct) {
        if (correct)
            correctAnswers.add(currentShownItem);

        currentShownItem++;
    }

    public boolean isBonusRound() {
        //TODO
        return false;
    }

    public boolean hasItem() {
        return getItem() != null;
    }

    public IntSet getCorrectAnswers() {
        return correctAnswers;
    }

    public int getScore() {
        // TODO Bonus
        return getCorrectAnswers().size;
    }
}
