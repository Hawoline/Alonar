package ru.hawoline.alonar.model.personage.item.state;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import ru.hawoline.alonar.domain.model.Range;
import ru.hawoline.alonar.domain.model.personage.Personage;
import ru.hawoline.alonar.domain.model.personage.PersonageFactory;
import ru.hawoline.alonar.domain.model.personage.heroclass.HeroClass;
import ru.hawoline.alonar.domain.model.personage.item.Quality;
import ru.hawoline.alonar.domain.model.personage.item.equipment.Body;
import ru.hawoline.alonar.domain.model.personage.item.equipment.state.OnMapEquipmentState;
import ru.hawoline.alonar.domain.model.personage.item.equipment.weapon.Knife;
import ru.hawoline.alonar.domain.model.personage.item.state.ItemStateName;
import ru.hawoline.alonar.domain.model.personage.item.state.OnMapItemState;
import ru.hawoline.alonar.util.Pair;

public class ItemStateTest {
    private Knife knife;

    @Before
    public void initTestVariables() {
        knife = new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 1,
                new Range(3, 4), 4,false);
    }

    @Test
    public void testItemStates() {
        Personage mage = PersonageFactory.createPersonage(HeroClass.MAGE);

        knife.setState(new OnMapItemState(knife));
        TestCase.assertEquals(ItemStateName.ON_MAP, knife.getState().getItemStateName());

        knife.getState().onThrowAway(mage.getInventory());
        TestCase.assertEquals(ItemStateName.ON_MAP, knife.getState().getItemStateName());
        TestCase.assertEquals(0, mage.getInventory().getBags().get(0).getItemCount());


        knife.getState().onAddToInventory(mage.getInventory());
        TestCase.assertEquals(ItemStateName.IN_INVENTORY, knife.getState().getItemStateName());
        TestCase.assertEquals(1, mage.getInventory().getBags().get(0).getItemCount());

        knife.getState().onAddToInventory(mage.getInventory());
        TestCase.assertEquals(ItemStateName.IN_INVENTORY, knife.getState().getItemStateName());
        TestCase.assertEquals(1, mage.getInventory().getBags().get(0).getItemCount());

        knife.getState().onThrowAway(mage.getInventory());
        TestCase.assertEquals(ItemStateName.ON_MAP, knife.getState().getItemStateName());
        TestCase.assertEquals(0, mage.getInventory().getBags().get(0).getItemCount());
    }

    @Test
    public void testEquipmentStates() {
        Personage mage = PersonageFactory.createPersonage(HeroClass.MAGE);

        knife.setState(new OnMapEquipmentState(knife));
        TestCase.assertEquals(ItemStateName.ON_MAP, knife.getState().getItemStateName());

        knife.getState().onThrowAway(mage.getInventory());
        TestCase.assertEquals(ItemStateName.ON_MAP, knife.getState().getItemStateName());
        TestCase.assertEquals(0, mage.getInventory().getBags().get(0).getItemCount());

        knife.getState().onAddToInventory(mage.getInventory());
        TestCase.assertEquals(ItemStateName.IN_INVENTORY, knife.getState().getItemStateName());
        TestCase.assertEquals(1, mage.getInventory().getBags().get(0).getItemCount());

        knife.getState().onAddToInventory(mage.getInventory());
        TestCase.assertEquals(ItemStateName.IN_INVENTORY, knife.getState().getItemStateName());
        TestCase.assertEquals(1, mage.getInventory().getBags().get(0).getItemCount());

        knife.getState().onThrowAway(mage.getInventory());
        TestCase.assertEquals(ItemStateName.ON_MAP, knife.getState().getItemStateName());
        TestCase.assertEquals(0, mage.getInventory().getBags().get(0).getItemCount());

        knife.getState().onEquip(mage);
        TestCase.assertEquals(ItemStateName.ON_MAP, knife.getState().getItemStateName());

        knife.getState().onAddToInventory(mage.getInventory());
        TestCase.assertEquals(ItemStateName.IN_INVENTORY, knife.getState().getItemStateName());
        TestCase.assertEquals(1, mage.getInventory().getBags().get(0).getItemCount());

        knife.getState().onAddToInventory(mage.getInventory());
        TestCase.assertEquals(ItemStateName.IN_INVENTORY, knife.getState().getItemStateName());
        TestCase.assertEquals(1, mage.getInventory().getBags().get(0).getItemCount());

        knife.getState().onEquip(mage);
        TestCase.assertEquals(ItemStateName.ON_BODY, knife.getState().getItemStateName());
        TestCase.assertEquals(0, mage.getInventory().getBags().get(0).getItemCount());

        knife.getState().onUnequip(mage);
        TestCase.assertEquals(ItemStateName.IN_INVENTORY, knife.getState().getItemStateName());
        TestCase.assertEquals(1, mage.getInventory().getBags().get(0).getItemCount());
    }
}