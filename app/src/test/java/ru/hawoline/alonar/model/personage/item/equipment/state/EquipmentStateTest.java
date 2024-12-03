package ru.hawoline.alonar.model.personage.item.equipment.state;

import org.junit.Before;
import org.junit.Test;
import ru.hawoline.alonar.domain.model.personage.item.Quality;
import ru.hawoline.alonar.domain.model.personage.item.equipment.Body;
import ru.hawoline.alonar.domain.model.personage.item.equipment.clothing.Clothing;
import ru.hawoline.alonar.domain.model.personage.item.equipment.state.OnMapEquipmentState;
import ru.hawoline.alonar.domain.model.personage.item.state.ItemState;
import ru.hawoline.alonar.util.Pair;

public class EquipmentStateTest {
    private ItemState itemState;
    private Clothing helmet;
    @Before
    public void initTestVariables() {
        itemState = new OnMapEquipmentState(helmet);
        helmet = new Clothing("helmet", 1, Quality.LEGENDARY, new Pair<>(100, 100), Body.HEAD);
    }
    @Test
    public void test() {
    }
}