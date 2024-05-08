package ru.hawoline.alonar.model.personage.effect;

import ru.hawoline.alonar.model.personage.specification.attribute.AttributeName;

public class TemporaryEffect extends Effect {
    private int duration;

    public TemporaryEffect(AttributeName attributeName, int value) {
        super(attributeName, value);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
