package ru.hawoline.alonar.domain.personage.spell;

import ru.hawoline.alonar.domain.Range;

public class AreaDamageSpell extends DamageSpell {
    private int mRadius;

    public AreaDamageSpell(String name, int distance, int requiredMana, int restoreTime, Range damage, int radius) {
        super(name, distance, requiredMana, restoreTime, damage);
        this.mRadius = radius;
    }

    public int getRadius() {
        return mRadius;
    }

    public void setRadius(int radius) {
        mRadius = radius;
    }
}
