package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.Collection;
import java.util.OptionalInt;
public final class ArbitraryBuilding implements Building{
    private int maxNumberOfResources;
    public ArbitraryBuilding(int maxNumberOfResources) {
        this.maxNumberOfResources = maxNumberOfResources;
    }

    @Override
    public OptionalInt build(Collection<Effect> resources) {
        if(resources.size() > maxNumberOfResources) {return OptionalInt.empty();}
        
        int sum = 0;
        for (Effect resource : resources) {
            sum += resource.points();
        }
        return OptionalInt.of(sum);
    }
}
