package ru.hawoline.alonar.model.personage.item.equipment;

import ru.hawoline.alonar.model.personage.effect.Effect;

import java.util.ArrayList;

public class Gem {
    private boolean mUnlocked;
    private ArrayList<Effect> mEffects;

    public Gem() {
        mUnlocked = false;
    }

    public boolean isUnlocked() {
        return mUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        mUnlocked = unlocked;
    }
}
