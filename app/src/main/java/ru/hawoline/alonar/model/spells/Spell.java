package ru.hawoline.alonar.model.spells;

import ru.hawoline.alonar.model.personage.Slot;

public abstract class Spell implements Slot {
    /**
     * 0 - close distance
     * 3 - one cell distance
     * 4 - one diagonal cell distance
     * 6 - two cell distance
     */
    private int mDistance;
    private int mRequiredMana;
    private String mDescription;
    private int mRestoreTime;
    private int mCurrentRestoreTime;

    public Spell(int distance, int requiredMana, int restoreTime) {
        mDistance = distance;
        mRequiredMana = requiredMana;
        mRestoreTime = restoreTime;
        mCurrentRestoreTime = restoreTime;
    }

    public int getDistance() {
        return mDistance;
    }

    public void setDistance(int distance) {
        this.mDistance = distance;
    }

    public int getRequiredMana() {
        return mRequiredMana;
    }

    public void setRequiredMana(int requiredMana) {
        this.mRequiredMana = requiredMana;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
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
