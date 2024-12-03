package ru.hawoline.alonar.domain.model.personage;

import ru.hawoline.alonar.domain.model.Range;
import ru.hawoline.alonar.domain.model.personage.heroclass.HeroClass;
import ru.hawoline.alonar.domain.model.personage.heroclass.Mage;
import ru.hawoline.alonar.domain.model.personage.heroclass.Paladin;
import ru.hawoline.alonar.domain.model.personage.heroclass.Priest;
import ru.hawoline.alonar.domain.model.personage.heroclass.Ranger;
import ru.hawoline.alonar.domain.model.personage.heroclass.Robber;
import ru.hawoline.alonar.domain.model.personage.heroclass.Warrior;
import ru.hawoline.alonar.domain.model.personage.item.Quality;
import ru.hawoline.alonar.domain.model.personage.item.equipment.Body;
import ru.hawoline.alonar.domain.model.personage.item.equipment.weapon.Knife;
import ru.hawoline.alonar.domain.model.personage.specification.attribute.AttributeName;
import ru.hawoline.alonar.domain.model.personage.specification.attribute.PersonageAttribute;
import ru.hawoline.alonar.util.Pair;

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

        Knife
            knife = new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 0, new Range(6, 8), 4,false);
        personage.equip(Body.ARMS, knife);
        personage.setArmor(10);
        return personage;
    }
}
