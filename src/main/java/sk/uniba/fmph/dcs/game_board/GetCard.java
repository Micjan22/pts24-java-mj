package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.CivilisationCard;
import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.Optional;

public final class GetCard implements EvaluateCivilisationCardImmediateEffect {
    private final CivilisationCardDeckInterface deck;

    public GetCard(final CivilisationCardDeckInterface deck) {
        this.deck = deck;
    }

    public ActionResult performEffect(final Player player, final Effect choice) {
        Optional<CivilisationCard> cardFromDeck = deck.getTop();
        if (!cardFromDeck.isPresent()) {
            return ActionResult.FAILURE;
        }
        cardFromDeck.ifPresent(card -> player.playerBoard().giveEndOfGameEffect(card.endOfGameEffect()));
        return ActionResult.ACTION_DONE;
    }

    @Override
    public boolean tryToPerformEffect(final Player player, final Effect choice) {
        Optional<CivilisationCard> cardFromDeck = deck.peek();
        return cardFromDeck.isPresent();
    }
}
