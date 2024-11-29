package ru.hawoline.alonar.domain.personage;

import ru.hawoline.alonar.domain.model.Range;
import ru.hawoline.alonar.domain.personage.heroclass.HeroClass;
import ru.hawoline.alonar.domain.personage.heroclass.Mage;
import ru.hawoline.alonar.domain.personage.heroclass.Paladin;
import ru.hawoline.alonar.domain.personage.heroclass.Priest;
import ru.hawoline.alonar.domain.personage.heroclass.Ranger;
import ru.hawoline.alonar.domain.personage.heroclass.Robber;
import ru.hawoline.alonar.domain.personage.heroclass.Warrior;
import ru.hawoline.alonar.domain.personage.item.equipment.Body;
import ru.hawoline.alonar.domain.personage.specification.attribute.AttributeName;
import ru.hawoline.alonar.domain.personage.specification.attribute.PersonageAttribute;
import ru.hawoline.alonar.domain.personage.spell.DamageSpell;
import ru.hawoline.alonar.model.personage.heroclass.*;
import ru.hawoline.alonar.domain.personage.item.Quality;
import ru.hawoline.alonar.domain.personage.item.equipment.weapon.Knife;
import ru.hawoline.alonar.util.Pair;

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
        personage.setAttribute(AttributeName.ENDURANCE, new PersonageAttribute(10));
        personage.setAttribute(AttributeName.STRENGTH, new PersonageAttribute(5));
        personage.setAttribute(AttributeName.INTELLIGENCE, new PersonageAttribute(100));
        personage.setAttribute(AttributeName.AGILITY, new PersonageAttribute(5));
        personage.setAttribute(AttributeName.SPIRIT, new PersonageAttribute(5));

        Knife knife = new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 0, new Range(6, 8), 4,false);
        personage.equip(Body.ARMS, knife);
        personage.setArmor(10);
        ArrayList<DamageSlot> slots = new ArrayList<>();
        slots.add(knife);
        slots.add(new DamageSpell("Fireball", 6, 50, 6, new Range(201, 239)));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 0, new Range(6, 8), 4,false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 0, new Range(6, 8), 4,false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 0, new Range(6, 8), 4, false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 0, new Range(6, 8), 4, false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 0, new Range(6, 8), 4, false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 0, new Range(6, 8), 4, false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 0, new Range(6, 8), 4, false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 0, new Range(6, 8), 4, false));
        personage.setSlots(slots);

        return personage;
    }
}
