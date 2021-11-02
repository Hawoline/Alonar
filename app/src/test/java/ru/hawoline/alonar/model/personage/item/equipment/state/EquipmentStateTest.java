package ru.hawoline.alonar.model.personage.item.equipment.state;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import ru.hawoline.alonar.model.personage.item.Item;
import ru.hawoline.alonar.model.personage.item.Quality;
import ru.hawoline.alonar.model.personage.item.equipment.Body;
import ru.hawoline.alonar.model.personage.item.equipment.clothing.Clothing;
import ru.hawoline.alonar.model.personage.item.state.InInventoryItemState;
import ru.hawoline.alonar.model.personage.item.state.ItemState;
import ru.hawoline.alonar.util.Pair;

import static org.junit.Assert.*;

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