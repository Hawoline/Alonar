package ru.hawoline.alonar.model.spell;

import ru.hawoline.alonar.model.Range;

import java.util.Random;

public class DamageSpell extends Spell {
    private int mDamage;
    private Range mDamageRange;

    public DamageSpell(String name, int distance, int requiredMana, int restoreTime, Range damageRange) {
        super(name, distance, requiredMana, restoreTime);
        this.mDamageRange = damageRange;
        mDamage = 500;
    }

    public Range getDamageRange() {
        return mDamageRange;
    }

    public void setDamageRange(Range damageRange) {
        mDamageRange = damageRange;
    }

    public int getDamage() {
//        mDamage = (int) (Math.random() * (mDamageRange.getHigh() - mDamageRange.getLow()) + mDamageRange.getLow());
        return mDamage;
    }
}
