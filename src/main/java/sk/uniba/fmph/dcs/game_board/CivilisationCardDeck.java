package sk.uniba.fmph.dcs.game_board;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.CivilisationCard;


public class CivilisationCardDeck implements CivilisationCardDeckInterface {
    private final ArrayList<CivilisationCard> deck;
    private int index;

    public CivilisationCardDeck(final ArrayList<CivilisationCard> deck) {
        this.deck = deck;
        index = deck.size() - 1;
    }

    public Optional<CivilisationCard> getTop() {
        if (index == -1) {
            return Optional.empty();
        }

        CivilisationCard card = deck.get(index);
        index--;
        return Optional.of(card);
    }

    public Optional<CivilisationCard> peek() {
        if (index == -1) {
            return Optional.empty();
        }

        CivilisationCard card = deck.get(index);
        return Optional.of(card);
    }

    @Override
    public String state() {
        int size = index + 1;
        Map<String, String> state = Map.of("size", String.valueOf(size));
        return new JSONObject(state).toString();
    }
}
