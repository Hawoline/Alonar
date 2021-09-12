package ru.hawoline.alonar.model.personage.heroclass;

import ru.hawoline.alonar.model.Range;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.Slot;
import ru.hawoline.alonar.model.spell.DamageSpell;

public class Mage extends Personage {

    private Mage() {
        super();
    }

    public static Personage createPersonage() {
        return new Mage();
    }
}
