package ru.hawoline.alonar.model.personage.weapon;

import ru.hawoline.alonar.model.Range;
import ru.hawoline.alonar.model.personage.DamageSlot;

public abstract class Weapon implements DamageSlot {
    private int mDistance;
    private Range mDamageRange;
    private int mRestoreTime;
    private int mCurrentRestoreTime;

    public Weapon(int distance, Range damageRange, int restoreTime) {
        mDistance = distance;
        mDamageRange = damageRange;
        mRestoreTime = restoreTime;
        mCurrentRestoreTime = restoreTime;
    }

    @Override
    public int getDistance() {
        return mDistance;
    }

    public void setDistance(int distance) {
        mDistance = distance;
    }

    public Range getDamageRange() {
        return mDamageRange;
    }

    public void setDamageRange(Range damageRange) {
        mDamageRange = damageRange;
    }

    @Override
    public int getDamage() {
        return (int) (Math.random() * (mDamageRange.getHigh() - mDamageRange.getLow()) + mDamageRange.getLow());
    }

    public int getRestoreTime() {
        return mRestoreTime;
    }

    public void setRestoreTime(int restoreTime) {
        mRestoreTime = restoreTime;
    }

    public int getCurrentRestoreTime() {
        return mCurrentRestoreTime;
    }

    public void setCurrentRestoreTime(int currentRestoreTime) {
        mCurrentRestoreTime = currentRestoreTime;
    }
}
