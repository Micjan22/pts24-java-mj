package sk.uniba.fmph.dcs.game_board;

import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.Collection;
import java.util.OptionalInt;
import static org.junit.Assert.assertEquals;

public class GetChoiceTest {

    @Test
    public void testCalculation() {

        PlayerBoardMock board = new PlayerBoardMock();
        Player p = new Player(null, board);
        GetChoice getChoice = new GetChoice();

        getChoice = new GetChoice();
        assertEquals(getChoice.performEffect(p, Effect.FOOD), ActionResult.FAILURE);
        assertEquals(getChoice.performEffect(p, Effect.WOOD), ActionResult.ACTION_DONE);
    }
}
