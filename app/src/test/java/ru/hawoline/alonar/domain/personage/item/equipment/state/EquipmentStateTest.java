package ru.hawoline.alonar.domain.personage.item.equipment.state;

import org.junit.Before;
import org.junit.Test;
import ru.hawoline.alonar.domain.personage.item.Quality;
import ru.hawoline.alonar.domain.personage.item.equipment.Body;
import ru.hawoline.alonar.domain.personage.item.equipment.clothing.Clothing;
import ru.hawoline.alonar.domain.personage.item.state.ItemState;
import ru.hawoline.alonar.util.Pair;

public class EquipmentStateTest {
    private ItemState mItemState;
    private Clothing mHelmet;
    @Before
    public void initTestVariables() {
        mItemState = new OnMapEquipmentState(mHelmet);
        mHelmet = new Clothing("helmet", 1, Quality.LEGENDARY, new Pair<>(100, 100), Body.HEAD);
    }
    @Test
    public void test() {
    }
}