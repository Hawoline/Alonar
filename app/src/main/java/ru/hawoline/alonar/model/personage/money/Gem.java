package ru.hawoline.alonar.model.personage.money;

import ru.hawoline.alonar.model.personage.effect.Effect;

import java.util.ArrayList;

public class Gem {
    private boolean unlocked;

    public Gem() {
        unlocked = false;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
}
