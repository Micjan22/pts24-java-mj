package sk.uniba.fmph.dcs.game_board;

import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.OptionalInt;
import static org.junit.Assert.assertEquals;

public class AllPlayersTakeRewardTest {

    @Test
    public void testCalculation() {

        ThrowInterface throwMock = new ThrowInterface() {
            @Override
            public ArrayList<Integer> throwDice(int dices) {
                ArrayList<Integer> result = new ArrayList<>();
                result.add(6);
                return result;
            }

            @Override
            public void setRolls(ArrayList<Integer> rolls) {

            }
        };

        RewardMenuInterface rewardMenuMock = new RewardMenuInterface() {
            @Override
            public void initiate(ArrayList<Effect> items) {

            }

            @Override
            public boolean takeReward(PlayerOrder player, Effect reward) {
                return false;
            }

            @Override
            public HasAction tryMakeAction(PlayerOrder player) {
                return null;
            }

            @Override
            public String state() {
                return null;
            }
        };

        PlayerBoardMock board = new PlayerBoardMock();
        AllPlayersTakeReward a = new AllPlayersTakeReward(rewardMenuMock, throwMock);
        PlayerOrder playerOrder = new PlayerOrder(0,0);
        Player p = new Player(playerOrder, board);
        assertEquals(a.performEffect(p, null), ActionResult.ACTION_DONE_ALL_PLAYERS_TAKE_A_REWARD);
    }
}
