package ru.hawoline.alonar.domain.model.personage.item;

import ru.hawoline.alonar.domain.model.personage.item.state.ItemState;
import ru.hawoline.alonar.domain.model.personage.item.state.OnMapItemState;

public abstract class Item {
    protected String name;
    protected int requiredLevel;
    protected Quality quality;
    private ItemState state;

    public Item(String name, int requiredLevel, Quality quality) {
        this.name = name;
        this.requiredLevel = requiredLevel;
        this.quality = quality;
        setState(new OnMapItemState(this));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public ItemState getState() {
        return state;
    }

    public void setState(ItemState state) {
        this.state = state;
    }
}
