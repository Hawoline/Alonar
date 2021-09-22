package ru.hawoline.alonar.model.personage.weapon;

import ru.hawoline.alonar.model.Range;
import ru.hawoline.alonar.model.personage.DamageSlot;

public abstract class Weapon implements DamageSlot {
    private int mDistance;
    private Range mRangeDamage;
    private int mRestoreTime;
    private int mCurrentRestoreTime;

    public Weapon(int distance, Range rangeDamage, int restoreTime) {
        mDistance = distance;
        mRangeDamage = rangeDamage;
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

    public Range getRangeDamage() {
        return mRangeDamage;
    }

    public void setRangeDamage(Range rangeDamage) {
        mRangeDamage = rangeDamage;
    }

    public int getDamage() {
        return (int) (Math.random() * (mRangeDamage.getHigh() - mRangeDamage.getLow()) + mRangeDamage.getLow());
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
