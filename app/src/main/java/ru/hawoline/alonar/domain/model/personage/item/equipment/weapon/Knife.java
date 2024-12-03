package ru.hawoline.alonar.domain.model.personage.item.equipment.weapon;

import ru.hawoline.alonar.domain.model.personage.item.Quality;
import ru.hawoline.alonar.domain.model.personage.item.equipment.Body;
import ru.hawoline.alonar.domain.model.Range;
import ru.hawoline.alonar.util.Pair;

public class Knife extends Weapon {
    public Knife(String name, int requiredLevel, Quality quality, Pair<Integer, Integer> strength, Body requiredBody,
                 int distance, Range damageRange, int restoreTime, boolean requiredTwoArms) {
        super(name, requiredLevel, quality, strength, requiredBody, distance, damageRange, restoreTime, requiredTwoArms);
    }
}
