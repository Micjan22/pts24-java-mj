
package sk.uniba.fmph.dcs.stone_age;

import sk.uniba.fmph.dcs.game_phase_controller.GamePhase;

import java.util.Collection;

public interface InterfaceGamePhaseController {
    boolean placeFigures(PlayerOrder player, Location location, int figuresCount);

    boolean makeAction(PlayerOrder player, Location location, Collection<Effect> inputResources,
                       Collection<Effect> outputResources);

    boolean skipAction(PlayerOrder player, Location location);

    boolean useTools(PlayerOrder player, int toolIndex);

    boolean noMoreToolsThisThrow(PlayerOrder player);

    boolean feedTribe(PlayerOrder player, Collection<Effect> resources);

    boolean doNotFeedThisTurn(PlayerOrder player);

    boolean makeAllPlayersTakeARewardChoice(PlayerOrder player, Effect reward);

    String state();

}
