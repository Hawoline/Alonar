package ru.hawoline.alonar.model.personage.inventory;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import ru.hawoline.alonar.domain.model.personage.Personage;
import ru.hawoline.alonar.domain.model.personage.PersonageFactory;
import ru.hawoline.alonar.domain.model.personage.heroclass.HeroClass;
import ru.hawoline.alonar.domain.model.personage.item.Item;
import ru.hawoline.alonar.domain.model.personage.item.Quality;
import ru.hawoline.alonar.domain.model.personage.item.equipment.Body;
import ru.hawoline.alonar.domain.model.personage.item.equipment.clothing.Clothing;
import ru.hawoline.alonar.util.Pair;

public class InventoryTest {
    private Personage hero;
    private Item item;

    @Before
    public void initTestVariables() {
        hero = PersonageFactory.createPersonage(HeroClass.MAGE);
        item = new Clothing("Hat", 1, Quality.LEGENDARY, new Pair<>(100, 100), Body.HEAD);
    }

    @Test
    public void testTakingItems() {
        item.getState().onAddToInventory(hero.getInventory());
        item.getState().onAddToInventory(hero.getInventory());
        TestCase.assertEquals(1, hero.getInventory().getItemCount());

        for (int i = 0; i < 20; i++) {
            Item hat = new Clothing("Hat", 1, Quality.LEGENDARY, new Pair<>(100, 100), Body.HEAD);
            hat.getState().onAddToInventory(hero.getInventory());
        }
        TestCase.assertEquals(12, hero.getInventory().getItemCount());
    }

    @Test
    public void testRemovingItems() {
        item.getState().onAddToInventory(hero.getInventory());
        item.getState().onThrowAway(hero.getInventory());
        TestCase.assertEquals(0, hero.getInventory().getItemCount());

        item.getState().onThrowAway(hero.getInventory());
        TestCase.assertEquals(0, hero.getInventory().getItemCount());

        item.getState().onAddToInventory(hero.getInventory());
        TestCase.assertEquals(1, hero.getInventory().getItemCount());
    }
}