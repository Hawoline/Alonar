package ru.hawoline.alonar.model.personage.spell;

import java.io.Serializable;

public abstract class Spell implements Serializable {
    /**
     * 0 - close distance
     * 3 - one cell distance
     * 4 - one diagonal cell distance
     * 6 - two cell distance
     */
    private int distance;
    private int requiredMana;
    private String name;
    private String description;
    private int restoreTime;
    private int currentRestoreTime;

    private static final long serialVersionUID = 3070102216000128252L;

    public Spell(String name, int distance, int requiredMana, int restoreTime) {
        this.name = name;
        this.distance = distance;
        this.requiredMana = requiredMana;
        this.restoreTime = restoreTime;
        currentRestoreTime = restoreTime;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getRequiredMana() {
        return requiredMana;
    }

    public void setRequiredMana(int requiredMana) {
        this.requiredMana = requiredMana;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRestoreTime() {
        return restoreTime;
    }

    public void setRestoreTime(int restoreTime) {
        this.restoreTime = restoreTime;
    }

    public int getCurrentRestoreTime() {
        return currentRestoreTime;
    }

    public void setCurrentRestoreTime(int currentRestoreTime) {
        this.currentRestoreTime = currentRestoreTime;
    }
}
