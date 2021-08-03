package ru.hawoline.alonar.model.spells;

import ru.hawoline.alonar.model.Range;

public class AreaDamageSpell extends DamageSpell {
    private int mRadius;

    public AreaDamageSpell(int distance, int requiredMana, int restoreTime, Range damage, int radius) {
        super(distance, requiredMana, restoreTime, damage);
        this.mRadius = radius;
    }

    public int getRadius() {
        return mRadius;
    }

    public void setRadius(int radius) {
        mRadius = radius;
    }
}
