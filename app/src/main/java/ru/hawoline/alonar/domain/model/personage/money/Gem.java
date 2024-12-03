package ru.hawoline.alonar.domain.model.personage.money;

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
