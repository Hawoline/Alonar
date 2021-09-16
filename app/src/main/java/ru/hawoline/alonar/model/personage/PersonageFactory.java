package ru.hawoline.alonar.model.personage;

import ru.hawoline.alonar.model.Range;
import ru.hawoline.alonar.model.personage.heroclass.*;
import ru.hawoline.alonar.model.personage.specification.attribute.Attribute;
import ru.hawoline.alonar.model.personage.specification.attribute.AttributeName;
import ru.hawoline.alonar.model.spell.DamageSpell;
import ru.hawoline.alonar.model.weapons.Knife;

import java.util.ArrayList;

public class PersonageFactory {

    public static Personage createPersonage(HeroClass heroClass) {
        Personage personage;
        if (heroClass == HeroClass.MAGE) {
            personage = new Mage();
        } else if (heroClass == HeroClass.PALADIN) {
            personage = new Paladin();
        } else if (heroClass == HeroClass.PRIEST) {
            personage = new Priest();
        } else if (heroClass == HeroClass.RANGER) {
            personage = new Ranger();
        } else if (heroClass == HeroClass.ROBBER) {
            personage = new Robber();
        } else {
            personage = new Warrior();
        }
        personage.setAttribute(AttributeName.ENDURANCE, new Attribute(5));
        personage.setAttribute(AttributeName.STRENGTH, new Attribute(5));
        personage.setAttribute(AttributeName.INTELLIGENCE, new Attribute(100));
        personage.setAttribute(AttributeName.AGILITY, new Attribute(5));
        personage.setAttribute(AttributeName.SPIRIT, new Attribute(5));
        personage.setArmor(100);
        ArrayList<Slot> slots = new ArrayList<>();
        slots.add(new Knife(0, new Range(3, 4), 2));
        slots.add(new DamageSpell("Fireball", 6, 50, 6, new Range(197, 236)));
        slots.add(new Knife(0, new Range(3, 4), 2));
        slots.add(new Knife(0, new Range(3, 4), 2));
        slots.add(new Knife(0, new Range(3, 4), 2));
        slots.add(new Knife(0, new Range(3, 4), 2));
        slots.add(new Knife(0, new Range(3, 4), 2));
        slots.add(new Knife(0, new Range(3, 4), 2));
        slots.add(new Knife(0, new Range(3, 4), 2));
        slots.add(new Knife(0, new Range(3, 4), 2));
        personage.setSlots(slots);
        return personage;
    }
}
