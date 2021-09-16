package ru.hawoline.alonar;

import junit.framework.TestCase;
import org.junit.Test;
import ru.hawoline.alonar.model.personage.Enemy;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.PersonageFactory;
import ru.hawoline.alonar.model.personage.heroclass.HeroClass;
import ru.hawoline.alonar.model.personage.heroclass.Mage;
import ru.hawoline.alonar.model.personage.usecase.DamageComputationUseCase;
import ru.hawoline.alonar.model.spell.DamageSpell;

public class PersonageUnitTest {
    @Test
    public void testAttackUseCase() {
        Personage hero = PersonageFactory.createPersonage(HeroClass.MAGE);
        Personage enemy = Enemy.createEnemy("Rat");

        TestCase.assertEquals(1600, hero.getMp());
        int damage = ((DamageSpell) hero.getSlots().get(1)).calculateDamage();
        TestCase.assertEquals( true, damage < 237 && damage > 196);

        DamageComputationUseCase.compute(hero, enemy, 1);
        TestCase.assertEquals(true, enemy.getHealth() < 1000 - 196 && enemy.getHealth() > 1000 - 237);
    }
}
