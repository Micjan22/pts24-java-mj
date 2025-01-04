package sk.uniba.fmph.dcs.game_board;

import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;
import static org.junit.Assert.assertEquals;

public class GetSomethingFixedTest {

    @Test
    public void testCalculation() {
        PlayerBoardMock board = new PlayerBoardMock();

        Player p = new Player(null, board);
        GetSomethingFixed getFixed = new GetSomethingFixed(Effect.WOOD);
        assertEquals(getFixed.performEffect(p, null), ActionResult.ACTION_DONE);

            getFixed = new GetSomethingFixed(Effect.BUILDING);
        assertEquals(getFixed.performEffect(p, null), ActionResult.FAILURE);
    }
}
