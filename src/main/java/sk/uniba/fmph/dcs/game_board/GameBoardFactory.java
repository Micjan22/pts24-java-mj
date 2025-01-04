package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.CivilisationCard;
import java.util.ArrayList;

public final class GameBoardFactory {

    private GameBoardFactory() {
    }

    public static GameBoard createGameBoard(final ArrayList<Player> players, final ArrayList<CivilisationCard> cards, final ThrowInterface throw1,
                                            final CurrentThrow currentThrow, final ArrayList<Building> buildings) {
        CivilisationCardDeck deck = new CivilisationCardDeck(cards);
        return new GameBoard(deck, buildings, players, throw1, currentThrow);
    }

}