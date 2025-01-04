
package sk.uniba.fmph.dcs.game_board;

import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

import static org.junit.Assert.assertEquals;

public class VariableBuildingTest {
    @Test
    public void test_calculation() {
        VariableBuilding building = new VariableBuilding(5, 3);
        ArrayList<Effect> buildingResources = new ArrayList<>();
        assertEquals(building.build(buildingResources), OptionalInt.empty());

        buildingResources.add(Effect.WOOD);
        assertEquals(building.build(buildingResources), OptionalInt.empty());
        buildingResources.add(Effect.STONE);
        buildingResources.add(Effect.CLAY);
        buildingResources.add(Effect.STONE);
        assertEquals(building.build(buildingResources), OptionalInt.empty());
        buildingResources.add(Effect.GOLD);
        assertEquals(building.build(buildingResources), OptionalInt.empty());

        ArrayList<Effect> otherResources = new ArrayList<>(
                List.of(Effect.WOOD, Effect.CLAY, Effect.CLAY, Effect.STONE));
        assertEquals(building.build(otherResources), OptionalInt.empty());
        otherResources.add(Effect.WOOD);
        assertEquals(building.build(otherResources), OptionalInt.of(19));
        otherResources.add(Effect.STONE);
        assertEquals(building.build(otherResources), OptionalInt.empty());

        ArrayList<Effect> otherResources2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            otherResources2.add(Effect.GOLD);
        }
        assertEquals(building.build(otherResources2), OptionalInt.empty());

        ArrayList<Effect> otherResources3 = new ArrayList<>(
                List.of(Effect.FOOD, Effect.CLAY, Effect.WOOD, Effect.WOOD, Effect.WOOD));
        assertEquals(building.build(otherResources3), OptionalInt.empty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenNumberOfResourcesTooSmall() {
        new VariableBuilding(0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenArgumentNumberOfResourceTypesTooSmall() {
        new VariableBuilding(1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenNumberOfResourceTypesGreaterThanNumberOfResources() {
        new VariableBuilding(2, 3);
    }
}
