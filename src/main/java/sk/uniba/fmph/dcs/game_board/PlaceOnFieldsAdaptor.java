
package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;

import java.util.Collection;

public final class PlaceOnFieldsAdaptor implements InterfaceFigureLocationInternal {
    private final ToolMakerHutFields fields;

    public PlaceOnFieldsAdaptor(final ToolMakerHutFields fields) {
        this.fields = fields;
    }

    @Override
    public boolean placeFigures(final Player player, final int figureCount) {
        if (tryToPlaceFigures(player, figureCount).equals(HasAction.NO_ACTION_POSSIBLE)) {
            return false;
        }
        return fields.placeOnFields(player);
    }

    @Override
    public HasAction tryToPlaceFigures(final Player player, final int count) {
        if (count != 1 || !fields.canPlaceOnFields(player) || !player.playerBoard().hasFigures(count)) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        return HasAction.WAITING_FOR_PLAYER_ACTION;
    }

    @Override
    public ActionResult makeAction(final Player player, final Collection<Effect> inputResources,
                                   final Collection<Effect> outputResources) {
        if (tryToMakeAction(player).equals(HasAction.NO_ACTION_POSSIBLE) || !fields.actionFields(player)) {
            return ActionResult.FAILURE;
        }
        return ActionResult.ACTION_DONE;
    }

    @Override
    public boolean skipAction(final Player player) {
        return fields.skipActionFields(player);
    }

    @Override
    public HasAction tryToMakeAction(final Player player) {
        if (fields.tryToMakeActionFields(player)) {
            return HasAction.WAITING_FOR_PLAYER_ACTION;
        }
        return HasAction.NO_ACTION_POSSIBLE;
    }

    @Override
    public boolean newTurn() {
        return fields.newTurn();
    }

    @Override
    public String state() {
        return "";
    }
}
