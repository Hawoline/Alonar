package ru.hawoline.alonar.domain.personage.effect;

import ru.hawoline.alonar.domain.personage.specification.attribute.AttributeName;

public class TemporaryEffect extends Effect {
    private int mDuration;

    public TemporaryEffect(AttributeName attributeName, int value) {
        super(attributeName, value);
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }
}
