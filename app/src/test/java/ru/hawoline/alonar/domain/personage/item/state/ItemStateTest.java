package ru.hawoline.alonar.domain.personage.item.state;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import ru.hawoline.alonar.domain.Range;
import ru.hawoline.alonar.domain.personage.Personage;
import ru.hawoline.alonar.domain.personage.heroclass.HeroClass;
import ru.hawoline.alonar.domain.personage.item.Quality;
import ru.hawoline.alonar.domain.personage.item.equipment.Body;
import ru.hawoline.alonar.domain.personage.item.equipment.state.OnMapEquipmentState;
import ru.hawoline.alonar.domain.personage.item.equipment.weapon.Knife;
import ru.hawoline.alonar.util.Pair;

public class ItemStateTest {
    private Knife mKnife;

    @Before
    public void initTestVariables() {
        mKnife = new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 1,
                new Range(3, 4), 4,false);
    }

    @Test
    public void testItemStates() {
        Personage mage = Personage.createPersonage(HeroClass.MAGE);

        mKnife.setState(new OnMapItemState(mKnife));
        TestCase.assertEquals(ItemStateName.ON_MAP, mKnife.getState().getItemStateName());

        mKnife.getState().onThrowAway(mage.getInventory());
        TestCase.assertEquals(ItemStateName.ON_MAP, mKnife.getState().getItemStateName());
        TestCase.assertEquals(0, mage.getInventory().getBags().get(0).getItemCount());


        mKnife.getState().onAddToInventory(mage.getInventory());
        TestCase.assertEquals(ItemStateName.IN_INVENTORY, mKnife.getState().getItemStateName());
        TestCase.assertEquals(1, mage.getInventory().getBags().get(0).getItemCount());

        mKnife.getState().onAddToInventory(mage.getInventory());
        TestCase.assertEquals(ItemStateName.IN_INVENTORY, mKnife.getState().getItemStateName());
        TestCase.assertEquals(1, mage.getInventory().getBags().get(0).getItemCount());

        mKnife.getState().onThrowAway(mage.getInventory());
        TestCase.assertEquals(ItemStateName.ON_MAP, mKnife.getState().getItemStateName());
        TestCase.assertEquals(0, mage.getInventory().getBags().get(0).getItemCount());
    }

    @Test
    public void testEquipmentStates() {
        Personage mage = Personage.createPersonage(HeroClass.MAGE);

        mKnife.setState(new OnMapEquipmentState(mKnife));
        TestCase.assertEquals(ItemStateName.ON_MAP, mKnife.getState().getItemStateName());

        mKnife.getState().onThrowAway(mage.getInventory());
        TestCase.assertEquals(ItemStateName.ON_MAP, mKnife.getState().getItemStateName());
        TestCase.assertEquals(0, mage.getInventory().getBags().get(0).getItemCount());

        mKnife.getState().onAddToInventory(mage.getInventory());
        TestCase.assertEquals(ItemStateName.IN_INVENTORY, mKnife.getState().getItemStateName());
        TestCase.assertEquals(1, mage.getInventory().getBags().get(0).getItemCount());

        mKnife.getState().onAddToInventory(mage.getInventory());
        TestCase.assertEquals(ItemStateName.IN_INVENTORY, mKnife.getState().getItemStateName());
        TestCase.assertEquals(1, mage.getInventory().getBags().get(0).getItemCount());

        mKnife.getState().onThrowAway(mage.getInventory());
        TestCase.assertEquals(ItemStateName.ON_MAP, mKnife.getState().getItemStateName());
        TestCase.assertEquals(0, mage.getInventory().getBags().get(0).getItemCount());

        mKnife.getState().onEquip(mage);
        TestCase.assertEquals(ItemStateName.ON_MAP, mKnife.getState().getItemStateName());

        mKnife.getState().onAddToInventory(mage.getInventory());
        TestCase.assertEquals(ItemStateName.IN_INVENTORY, mKnife.getState().getItemStateName());
        TestCase.assertEquals(1, mage.getInventory().getBags().get(0).getItemCount());

        mKnife.getState().onAddToInventory(mage.getInventory());
        TestCase.assertEquals(ItemStateName.IN_INVENTORY, mKnife.getState().getItemStateName());
        TestCase.assertEquals(1, mage.getInventory().getBags().get(0).getItemCount());

        mKnife.getState().onEquip(mage);
        TestCase.assertEquals(ItemStateName.ON_BODY, mKnife.getState().getItemStateName());
        TestCase.assertEquals(0, mage.getInventory().getBags().get(0).getItemCount());

        mKnife.getState().onUnequip(mage);
        TestCase.assertEquals(ItemStateName.IN_INVENTORY, mKnife.getState().getItemStateName());
        TestCase.assertEquals(1, mage.getInventory().getBags().get(0).getItemCount());
    }
}