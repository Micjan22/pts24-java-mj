
package sk.uniba.fmph.dcs.game_board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.OptionalInt;
import java.util.Map;
import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.Effect;

public final class SimpleBuilding implements Building {
    private ArrayList<Effect> requiredResources;

    public SimpleBuilding(final Collection<Effect> resources) {
        this.requiredResources = new ArrayList<Effect>();
        for (Effect resource : resources) {
            if (!resource.isResource()) {
                throw new IllegalArgumentException("Resources must be resources");
            }
            this.requiredResources.add(resource);
        }
    }

    public OptionalInt build(final Collection<Effect> resources) {
        ArrayList<Effect> resourcesCheck = new ArrayList<>(resources);
        for (Effect effect: requiredResources) {
            if (!resourcesCheck.contains(effect)) {
                return OptionalInt.empty();
            }
            resourcesCheck.remove(effect);
        }
        if (!resourcesCheck.isEmpty()) {
            return OptionalInt.empty();
        }

        int sum = 0;
        for (Effect resource : resources) {
            sum += resource.points();
        }
        return OptionalInt.of(sum);
    }

    @Override
    public String state() {
        Map<String, String> stateMap = Map.of("requiredResources", requiredResources.toString());
        return new JSONObject(stateMap).toString();
    }
}
