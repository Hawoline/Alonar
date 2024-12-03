package ru.hawoline.alonar.model.personage.item.equipment.weapon;

import ru.hawoline.alonar.model.Range;
import ru.hawoline.alonar.model.personage.item.equipment.Body;
import ru.hawoline.alonar.model.personage.item.equipment.Equipment;
import ru.hawoline.alonar.model.personage.item.Quality;
import ru.hawoline.alonar.util.Pair;

public class Weapon extends Equipment {
    private int distance;
    private Range damageRange;
    private int restoreTime;
    private int currentRestoreTime;
    private boolean requiredTwoArms;

    public Weapon(String name, int requiredLevel, Quality quality, Pair<Integer, Integer> strength, Body requiredBody,
                  int distance, Range damageRange, int restoreTime, boolean requiredTwoArms) {
        super(name, requiredLevel, quality, strength, requiredBody);
        this.distance = distance;
        this.damageRange = damageRange;
        this.restoreTime = restoreTime;
        currentRestoreTime = restoreTime;
        this.requiredTwoArms = requiredTwoArms;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Range getDamageRange() {
        return damageRange;
    }

    public void setDamageRange(Range damageRange) {
        this.damageRange = damageRange;
    }

    public int getRestoreTime() {
        return restoreTime;
    }

    public void setRestoreTime(int restoreTime) {
        this.restoreTime = restoreTime;
    }

    public int getCurrentRestoreTime() {
        return currentRestoreTime;
    }

    public void setCurrentRestoreTime(int currentRestoreTime) {
        this.currentRestoreTime = currentRestoreTime;
    }

    public boolean isRequiredTwoArms() {
        return requiredTwoArms;
    }
}
