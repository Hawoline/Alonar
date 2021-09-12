package ru.hawoline.alonar;

import junit.framework.TestCase;
import org.junit.Test;
import ru.hawoline.alonar.model.personage.Enemy;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.heroclass.Mage;
import ru.hawoline.alonar.model.personage.usecase.DamageComputationUseCase;
import ru.hawoline.alonar.model.spell.DamageSpell;

public class PersonageUnitTest {
    @Test
    public void testAttackUseCase() {
        Personage hero = Mage.createPersonage();
        Personage enemy = Enemy.createEnemy("Rat");

        TestCase.assertEquals( 500, ((DamageSpell) hero.getSlots().get(1)).getDamage());
        TestCase.assertEquals(1000, enemy.getHealth());

        DamageComputationUseCase.compute(hero, enemy, 1);
        TestCase.assertEquals(500, enemy.getHealth());

        DamageComputationUseCase.compute(hero, enemy, 1);
        TestCase.assertEquals(0, enemy.getHealth());
    }
}
