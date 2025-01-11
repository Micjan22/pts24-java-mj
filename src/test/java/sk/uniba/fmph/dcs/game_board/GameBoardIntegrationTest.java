package sk.uniba.fmph.dcs.game_board;

import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.*;
import java.util.ArrayList;
import java.util.Map;
import static org.junit.Assert.*;

public class GameBoardIntegrationTest {

    private static class ThrowMock implements ThrowInterface {
        private ArrayList<Integer> rolls;
        private int index;
        @Override
        public ArrayList<Integer> throwDice(int dices) {
            ArrayList<Integer> result = new ArrayList<>();
            for (int i = 0; i < dices; i++) {
                result.add(rolls.get(index));
                index++;
            }
            return result;
        }

        @Override
        public void setRolls(ArrayList<Integer> rolls) {
            this.rolls = rolls;
            index = 0;
        }
    }

    private static ArrayList<Player> playersInitiate() {
        ArrayList<Player> players = new ArrayList<>();
        PlayerOrder playerOrder1 = new PlayerOrder(0,2);
        PlayerOrder playerOrder2 = new PlayerOrder(1,2);
        PlayerBoardMock board1 = new PlayerBoardMock();
        PlayerBoardMock board2 = new PlayerBoardMock();
        Player player1 = new Player(playerOrder1, board1);
        Player player2 = new Player(playerOrder2, board2);
        players.add(player1);
        players.add(player2);

        ArrayList<Effect> resources = new ArrayList<>();
        resources.add(Effect.WOOD);
        resources.add(Effect.WOOD);
        resources.add(Effect.WOOD);
        resources.add(Effect.WOOD);
        resources.add(Effect.WOOD);
        player1.playerBoard().giveEffect(resources);
        player1.playerBoard().giveFigures(50);
        player2.playerBoard().giveEffect(resources);
        player2.playerBoard().giveFigures(50);
        return players;
    }

    private static GameBoard gameBoardInitiate(ThrowMock throwMock, ArrayList<Player> players) {
        ArrayList<CivilisationCard> cards = new ArrayList<>();
        ArrayList<ImmediateEffect> effects6 = new ArrayList<>();
        effects6.add(ImmediateEffect.ALL_PLAYERS_TAKE_REWARD);
        ArrayList<EndOfGameEffect> endOfGameEffects = new ArrayList<>();
        endOfGameEffects.add(EndOfGameEffect.SHAMAN);
        CivilisationCard card1 = new CivilisationCard(null, null);
        CivilisationCard card2 = new CivilisationCard(null, null);
        CivilisationCard card3 = new CivilisationCard(null, null);
        CivilisationCard card4 = new CivilisationCard(null, null);
        CivilisationCard card5 = new CivilisationCard(null, null);
        CivilisationCard card6 = new CivilisationCard(effects6, endOfGameEffects);
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);
        cards.add(card6);
        CurrentThrow currentThrow = new CurrentThrow(throwMock);

        ArrayList<Building> buildings = new ArrayList<>();
        Building building1 = new ArbitraryBuilding(1);
        Building building2 = new ArbitraryBuilding(1);
        Building building3 = new ArbitraryBuilding(1);
        Building building4 = new ArbitraryBuilding(1);
        Building building5 = new ArbitraryBuilding(1);
        Building building6 = new ArbitraryBuilding(1);
        Building building7 = new ArbitraryBuilding(1);
        Building building8 = new ArbitraryBuilding(1);
        buildings.add(building1);
        buildings.add(building2);
        buildings.add(building3);
        buildings.add(building4);
        buildings.add(building5);
        buildings.add(building6);
        buildings.add(building7);
        buildings.add(building8);

        return GameBoardFactory.createGameBoard(players, cards, throwMock, currentThrow, buildings);
    }

    @Test
    public void rewardMenuTest() {
        ThrowMock throwMock = new ThrowMock();
        ArrayList<Integer> throwResult = new ArrayList<>();
        throwResult.add(6);
        throwResult.add(1);
        throwMock.setRolls(throwResult);

        ArrayList<Player> players = playersInitiate();
        Player player1 = players.get(0);
        Player player2 = players.get(1);

        GameBoard gameBoard = gameBoardInitiate(throwMock, players);
        RewardMenu rewardMenu = gameBoard.getRewardMenu();
        Map<Location, InterfaceFigureLocation> locationMap = gameBoard.getLocationToFigureLocationMap();
        InterfaceFigureLocation location = locationMap.get(Location.CIVILISATION_CARD4);
        assertEquals(location.tryToPlaceFigures(player1.playerOrder(), 1), HasAction.WAITING_FOR_PLAYER_ACTION);
        assertTrue(location.placeFigures(player1.playerOrder(), 1));

        ArrayList<Effect> inputResources = new ArrayList<>();
        inputResources.add(Effect.WOOD);
        ArrayList<Effect> outputResources = new ArrayList<>();
        outputResources.add(Effect.WOOD);

        assertEquals(location.makeAction(player1.playerOrder(), inputResources, outputResources), ActionResult.ACTION_DONE_ALL_PLAYERS_TAKE_A_REWARD);
        assertEquals(rewardMenu.tryMakeAction(player1.playerOrder()), HasAction.WAITING_FOR_PLAYER_ACTION);
        assertEquals(rewardMenu.tryMakeAction(player2.playerOrder()), HasAction.WAITING_FOR_PLAYER_ACTION);

        assertTrue(rewardMenu.takeReward(player1.playerOrder(), Effect.WOOD));
        assertEquals(rewardMenu.tryMakeAction(player2.playerOrder()), HasAction.AUTOMATIC_ACTION_DONE);
        assertEquals(rewardMenu.tryMakeAction(player2.playerOrder()), HasAction.NO_ACTION_POSSIBLE);
    }

    @Test
    public void currentThrowTest() {
        ThrowMock throwMock = new ThrowMock();
        ArrayList<Integer> throwResult = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            throwResult.add(1);
            throwResult.add(2);
            throwResult.add(3);
            throwResult.add(4);
            throwResult.add(5);
        }
        throwMock.setRolls(throwResult);

        ArrayList<Player> players = playersInitiate();
        Player player1 = players.get(0);
        Player player2 = players.get(1);

        GameBoard gameBoard = gameBoardInitiate(throwMock, players);
        CurrentThrow currentThrow = gameBoard.getCurrentThrow();
        Map<Location, InterfaceFigureLocation> locationMap = gameBoard.getLocationToFigureLocationMap();
        InterfaceFigureLocation location1 = locationMap.get(Location.FOREST);
        InterfaceFigureLocation location2 = locationMap.get(Location.RIVER);

        assertFalse(currentThrow.canUseTools());
        assertEquals(location1.tryToPlaceFigures(player1.playerOrder(), 3), HasAction.WAITING_FOR_PLAYER_ACTION);
        assertTrue(location1.placeFigures(player1.playerOrder(), 3));
        assertTrue(location1.placeFigures(player2.playerOrder(), 2));
        assertTrue(location2.placeFigures(player1.playerOrder(), 2));
        assertTrue(location2.placeFigures(player2.playerOrder(), 3));

        assertEquals(location1.makeAction(player1.playerOrder(), null, null), ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE);
        assertEquals(currentThrow.getThrowResult(), 6);

        assertEquals(location2.makeAction(player1.playerOrder(), null, null), ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE);
        assertEquals(currentThrow.getThrowResult(), 9);
        assertTrue(currentThrow.finishUsingTools());

        assertEquals(location1.makeAction(player2.playerOrder(), null,null), ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE);
        assertEquals(currentThrow.getThrowResult(), 3);
        assertTrue(currentThrow.finishUsingTools());

        assertEquals(location2.makeAction(player2.playerOrder(), null,null), ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE);
        assertEquals(currentThrow.getThrowResult(), 12);
        assertTrue(currentThrow.finishUsingTools());
    }

    @Test
    public void locationTest() {
        ThrowMock throwMock = new ThrowMock();
        ArrayList<Integer> rolls = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            rolls.add(6);
        }
        throwMock.setRolls(rolls);
        ArrayList<Player> players = playersInitiate();
        Player player1 = players.get(0);
        Player player2 = players.get(1);

        GameBoard gameBoard = gameBoardInitiate(throwMock, players);
        CurrentThrow currentThrow = gameBoard.getCurrentThrow();
        Map<Location, InterfaceFigureLocation> locationMap = gameBoard.getLocationToFigureLocationMap();
        InterfaceFigureLocation toolMaker = locationMap.get(Location.TOOL_MAKER);
        InterfaceFigureLocation hut = locationMap.get(Location.HUT);
        InterfaceFigureLocation field = locationMap.get(Location.FIELD);
        InterfaceFigureLocation huntingGrounds = locationMap.get(Location.HUNTING_GROUNDS);
        InterfaceFigureLocation forest = locationMap.get(Location.FOREST);
        InterfaceFigureLocation clayMound = locationMap.get(Location.CLAY_MOUND);
        InterfaceFigureLocation quarry = locationMap.get(Location.QUARRY);
        InterfaceFigureLocation river = locationMap.get(Location.RIVER);
        InterfaceFigureLocation building1 = locationMap.get(Location.BUILDING_TILE1);
        InterfaceFigureLocation building2 = locationMap.get(Location.BUILDING_TILE2);
        InterfaceFigureLocation building3 = locationMap.get(Location.BUILDING_TILE3);
        InterfaceFigureLocation building4 = locationMap.get(Location.BUILDING_TILE4);

        assertTrue(toolMaker.placeFigures(player1.playerOrder(), 1));
        assertTrue(huntingGrounds.placeFigures(player1.playerOrder(), 5));
        assertTrue(hut.placeFigures(player1.playerOrder(), 2));
        assertFalse(field.placeFigures(player1.playerOrder(), 1));
        assertTrue(clayMound.placeFigures(player1.playerOrder(), 4));
        assertTrue(forest.placeFigures(player1.playerOrder(), 1));
        assertTrue(quarry.placeFigures(player1.playerOrder(), 5));
        assertTrue(river.placeFigures(player1.playerOrder(), 5));
        assertTrue(building1.placeFigures(player1.playerOrder(), 1));
        assertTrue(building3.placeFigures(player1.playerOrder(), 1));

        assertFalse(toolMaker.placeFigures(player2.playerOrder(), 1));
        assertTrue(huntingGrounds.placeFigures(player2.playerOrder(), 5));
        assertFalse(hut.placeFigures(player2.playerOrder(), 2));
        assertFalse(field.placeFigures(player2.playerOrder(), 1));
        assertTrue(clayMound.placeFigures(player2.playerOrder(), 3));
        assertTrue(forest.placeFigures(player2.playerOrder(), 6));
        assertTrue(quarry.placeFigures(player2.playerOrder(), 2));
        assertTrue(river.placeFigures(player2.playerOrder(), 2));
        assertTrue(building2.placeFigures(player2.playerOrder(), 1));
        assertTrue(building4.placeFigures(player2.playerOrder(), 1));

        assertEquals(toolMaker.makeAction(player1.playerOrder(),null,null), ActionResult.ACTION_DONE);
        assertEquals(huntingGrounds.makeAction(player1.playerOrder(),null,null), ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE);
        currentThrow.finishUsingTools();
        assertEquals(huntingGrounds.makeAction(player2.playerOrder(),null,null), ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE);
        currentThrow.finishUsingTools();
        assertEquals(hut.makeAction(player1.playerOrder(),null,null), ActionResult.ACTION_DONE);
        assertEquals(clayMound.makeAction(player1.playerOrder(),null,null), ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE);
        currentThrow.finishUsingTools();
        assertEquals(clayMound.makeAction(player2.playerOrder(),null,null), ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE);
        currentThrow.finishUsingTools();
        ArrayList<Effect> resources = new ArrayList<>();
        resources.add(Effect.WOOD);
        assertEquals(building1.makeAction(player1.playerOrder(),resources,null), ActionResult.ACTION_DONE);
        assertEquals(building2.makeAction(player2.playerOrder(),resources,null), ActionResult.ACTION_DONE);
    }
}
