package sk.uniba.fmph.dcs.game_board;

import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;
import static org.junit.Assert.assertEquals;

public class GetSomethingThrowTest {

    @Test
    public void testCalculation() {
        CurrentThrowInterface currentThrowMock = new CurrentThrowInterface() {
            @Override
            public void initiate(Player player, Effect effect, int dices) {

            }

            @Override
            public boolean isInitiated() {
                return false;
            }
        };

        Player p = new Player(null, null);
        GetSomethingThrow getThrow = new GetSomethingThrow(currentThrowMock, Effect.WOOD);
        assertEquals(getThrow.performEffect(p, null), ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE);
        getThrow = new GetSomethingThrow(currentThrowMock, Effect.FOOD);
        assertEquals(getThrow.performEffect(p, null), ActionResult.FAILURE);
    }
}
