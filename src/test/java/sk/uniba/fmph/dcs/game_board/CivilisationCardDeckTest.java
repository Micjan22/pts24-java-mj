package sk.uniba.fmph.dcs.game_board;

import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.CivilisationCard;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CivilisationCardDeckTest {

    @Test
    public void testCalculation() {
        ArrayList<CivilisationCard> cards = new ArrayList<>();
        CivilisationCard card1 = new CivilisationCard(null, null);
        CivilisationCard card2 = new CivilisationCard(null, null);
        cards.add(card1);
        cards.add(card2);
        CivilisationCardDeck deck = new CivilisationCardDeck(cards);
        assertEquals(deck.peek().get(), card2);
        assertEquals(deck.getTop().get(), card2);
        assertEquals(deck.getTop().get(), card1);
        assertTrue(deck.getTop().isEmpty());
    }
}
