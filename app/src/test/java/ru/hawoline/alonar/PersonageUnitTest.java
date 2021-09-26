package ru.hawoline.alonar;

import junit.framework.TestCase;
import org.junit.Test;
import ru.hawoline.alonar.model.personage.DamageSlot;
import ru.hawoline.alonar.model.personage.Enemy;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.PersonageFactory;
import ru.hawoline.alonar.model.personage.heroclass.HeroClass;
import ru.hawoline.alonar.model.personage.spell.DamageSpell;
import ru.hawoline.alonar.model.personage.usecase.DamageComputationUseCase;

public class PersonageUnitTest {
    @Test
    public void testMagicAttackUseCase() {
        Personage hero = PersonageFactory.createPersonage(HeroClass.MAGE);
        Personage enemy = Enemy.createEnemy("Rat");

        TestCase.assertEquals(1600, hero.getMp());
        int damage = ((DamageSpell) hero.getSlots().get(1)).calculateDamage();
        TestCase.assertTrue(damage < 237 && damage > 196);

        DamageComputationUseCase.compute(hero, enemy, 1);
        TestCase.assertTrue(enemy.getHealth() < 1000 - 196 && enemy.getHealth() > 1000 - 237);
    }

    @Test
    public void testWeaponAttackUseCase() {
        Personage hero = PersonageFactory.createPersonage(HeroClass.MAGE);
        Enemy enemy = Enemy.createEnemy("Rat");

        TestCase.assertEquals(1000, enemy.getHealth());
        DamageComputationUseCase.compute(hero, enemy, 0);
        TestCase.assertEquals(997, enemy.getHealth());

        TestCase.assertEquals(4, ((DamageSlot) hero.getSlots().get(0)).getDamage());
    }
}
