package ru.hawoline.alonar.model.spells;

import ru.hawoline.alonar.model.Range;

public class DamageSpell extends Spell {
    private Range mDamage;

    public DamageSpell(int distance, int requiredMana, int restoreTime, Range damage) {
        super(distance, requiredMana, restoreTime);
        this.mDamage = damage;
    }

    public Range getDamage() {
        return mDamage;
    }

    public void setDamage(Range damage) {
        mDamage = damage;
    }
}
