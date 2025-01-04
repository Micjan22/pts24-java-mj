package sk.uniba.fmph.dcs.game_board;
import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.OptionalInt;
import static org.junit.Assert.assertEquals;

public class GetCardTest {

    private class CivilisationCardDeckMock implements CivilisationCardDeckInterface {
        private static ArrayList<Optional<CivilisationCard>> deck;

        CivilisationCardDeckMock() {
            deck = new ArrayList<>();
            deck.add(Optional.empty());
            deck.add(Optional.of(new CivilisationCard(null, null)));
        }
        @Override
        public Optional<CivilisationCard> getTop() {
            Optional<CivilisationCard> card =  deck.get(deck.size() - 1);
            deck.remove(deck.size() - 1);
            return card;
        }

        @Override
        public Optional<CivilisationCard> peek() {
            return Optional.empty();
        }

        @Override
        public String state() {
            return null;
        }
    }

    @Test
    public void testCalculation() {
        InterfacePlayerBoardGameBoard board = new InterfacePlayerBoardGameBoard() {
            @Override
            public void giveEffect(Collection<Effect> stuff) {

            }

            @Override
            public void giveEndOfGameEffect(Collection<EndOfGameEffect> stuff) {

            }

            @Override
            public boolean takeResources(Collection<Effect> stuff) {
                return false;
            }

            @Override
            public boolean takeFigures(int count) {
                return false;
            }

            @Override
            public void giveFigures(int count) {

            }

            @Override
            public boolean hasFigures(int count) {
                return false;
            }

            @Override
            public boolean hasSufficientTools(int goal) {
                return false;
            }

            @Override
            public OptionalInt useTool(int idx) {
                return null;
            }

            @Override
            public void takePoints(int points) {

            }

            @Override
            public void givePoints(int points) {

            }
        };

        CivilisationCardDeckMock civilisationCardDeckMock = new CivilisationCardDeckMock();
        Player p = new Player(null, board);
        GetCard getCard = new GetCard(civilisationCardDeckMock);
        assertEquals(getCard.performEffect(p, Effect.WOOD), ActionResult.ACTION_DONE);
        assertEquals(getCard.performEffect(p, Effect.WOOD), ActionResult.FAILURE);
    }
}
