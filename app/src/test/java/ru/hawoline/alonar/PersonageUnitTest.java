package ru.hawoline.alonar;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import ru.hawoline.alonar.domain.model.personage.DamageSlot;
import ru.hawoline.alonar.domain.model.personage.Personage;
import ru.hawoline.alonar.domain.model.personage.PersonageFactory;
import ru.hawoline.alonar.domain.model.personage.effect.Effect;
import ru.hawoline.alonar.domain.model.personage.enemy.Enemy;
import ru.hawoline.alonar.domain.model.personage.heroclass.HeroClass;
import ru.hawoline.alonar.domain.model.personage.item.Quality;
import ru.hawoline.alonar.domain.model.personage.item.equipment.Body;
import ru.hawoline.alonar.domain.model.personage.item.equipment.clothing.Clothing;
import ru.hawoline.alonar.domain.model.personage.specification.attribute.AttributeName;
import ru.hawoline.alonar.domain.model.personage.specification.attribute.PersonageAttribute;
import ru.hawoline.alonar.domain.model.personage.spell.DamageSpell;
import ru.hawoline.alonar.domain.model.personage.usecase.DamageComputationUseCase;
import ru.hawoline.alonar.util.Pair;

import java.util.ArrayList;

public class PersonageUnitTest {
    private Personage hero;

    @Before
    public void initTestVariables() {
        hero = PersonageFactory.createPersonage(HeroClass.MAGE);
    }

    @Test
    public void testMagicAttackUseCase() {
        Personage enemy = Enemy.createEnemy("Rat");

        TestCase.assertEquals(1600, hero.getMp());
        int damage = ((DamageSpell) hero.getDamageSlots().get(1)).calculateDamage();
        TestCase.assertTrue(damage < 237 && damage > 196);

        DamageComputationUseCase.compute(hero, enemy, 1);
        TestCase.assertTrue(enemy.getHealth() < 1000 - 196 && enemy.getHealth() > 1000 - 237);
        TestCase.assertEquals(1550, hero.getMp());
    }

    @Test
    public void testWeaponAttackUseCase() {
        Enemy enemy = Enemy.createEnemy("Rat");

        TestCase.assertEquals(1000, enemy.getHealth());
        DamageComputationUseCase.compute(hero, enemy, 0);
        TestCase.assertEquals(997, enemy.getHealth());
        TestCase.assertEquals(1600, hero.getMp());

        TestCase.assertEquals(3, ((DamageSlot) hero.getDamageSlots().get(0)).getDamage());
    }

    @Test
    public void testEquipment() {
        //Протестить прибавление аттрибутов после надевания одежды
        PersonageAttribute[] personageAttributes = new PersonageAttribute[5];
        personageAttributes[0] = hero.getAttribute(AttributeName.ENDURANCE);
        personageAttributes[1] = hero.getAttribute(AttributeName.STRENGTH);
        personageAttributes[2] = hero.getAttribute(AttributeName.INTELLIGENCE);
        personageAttributes[3] = hero.getAttribute(AttributeName.AGILITY);
        personageAttributes[4] = hero.getAttribute(AttributeName.SPIRIT);

        int[] tempAttributesMaxes = new int[5];
        for (int i = 0; i < personageAttributes.length; i++) {
            tempAttributesMaxes[i] = personageAttributes[i].getMax();
        }


        Clothing cap = new Clothing("Cap", 1, Quality.NORMAL, new Pair<>(100, 100), Body.HEAD);
        Clothing vest = new Clothing("Vest", 1, Quality.NORMAL, new Pair<>(100, 100), Body.BODY);

        ArrayList<Effect> capEffects = new ArrayList<>();
        capEffects.add(new Effect(AttributeName.INTELLIGENCE, 5));
        capEffects.add(new Effect(AttributeName.AGILITY, 4));
        cap.setEffects(capEffects);

        ArrayList<Effect> vestEffects = new ArrayList<>();
        vestEffects.add(new Effect(AttributeName.INTELLIGENCE, 7));
        vest.setEffects(vestEffects);

        hero.equip(Body.HEAD, cap);
        hero.equip(Body.BODY, vest);
        TestCase.assertEquals(tempAttributesMaxes[2] + 12, hero.getAttribute(AttributeName.INTELLIGENCE).getMax());

        hero.equip(Body.HEAD, cap);
        hero.equip(Body.BODY, vest);

        TestCase.assertEquals(3, hero.getEquipment().size());
        TestCase.assertEquals(tempAttributesMaxes[2] + 12, hero.getAttribute(AttributeName.INTELLIGENCE).getMax());

        hero.unequip(Body.HEAD);
        hero.unequip(Body.BODY);

        TestCase.assertEquals(tempAttributesMaxes[2], hero.getAttribute(AttributeName.INTELLIGENCE).getMax());
        TestCase.assertEquals(1, hero.getEquipment().size());

        hero.unequip(Body.HEAD);
    }
}
