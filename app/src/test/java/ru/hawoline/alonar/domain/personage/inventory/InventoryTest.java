package ru.hawoline.alonar.domain.personage.inventory;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import ru.hawoline.alonar.domain.personage.Personage;
import ru.hawoline.alonar.domain.personage.PersonageFactory;
import ru.hawoline.alonar.domain.personage.heroclass.HeroClass;
import ru.hawoline.alonar.domain.personage.item.Item;
import ru.hawoline.alonar.domain.personage.item.Quality;
import ru.hawoline.alonar.domain.personage.item.equipment.Body;
import ru.hawoline.alonar.domain.personage.item.equipment.clothing.Clothing;
import ru.hawoline.alonar.util.Pair;

public class InventoryTest {
    private Personage mHero;
    private Item mItem;

    @Before
    public void initTestVariables() {
        mHero = PersonageFactory.createPersonage(HeroClass.MAGE);
        mItem = new Clothing("Hat", 1, Quality.LEGENDARY, new Pair<>(100, 100), Body.HEAD);
    }

    @Test
    public void testTakingItems() {
        mItem.getState().onAddToInventory(mHero.getInventory());
        mItem.getState().onAddToInventory(mHero.getInventory());
        TestCase.assertEquals(1, mHero.getInventory().getItemCount());

        for (int i = 0; i < 20; i++) {
            Item hat = new Clothing("Hat", 1, Quality.LEGENDARY, new Pair<>(100, 100), Body.HEAD);
            hat.getState().onAddToInventory(mHero.getInventory());
        }
        TestCase.assertEquals(12, mHero.getInventory().getItemCount());
    }

    @Test
    public void testRemovingItems() {
        mItem.getState().onAddToInventory(mHero.getInventory());
        mItem.getState().onThrowAway(mHero.getInventory());
        TestCase.assertEquals(0, mHero.getInventory().getItemCount());

        mItem.getState().onThrowAway(mHero.getInventory());
        TestCase.assertEquals(0, mHero.getInventory().getItemCount());

        mItem.getState().onAddToInventory(mHero.getInventory());
        TestCase.assertEquals(1, mHero.getInventory().getItemCount());
    }
}