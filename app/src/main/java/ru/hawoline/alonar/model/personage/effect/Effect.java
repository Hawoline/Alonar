package ru.hawoline.alonar.model.personage.effect;

import ru.hawoline.alonar.model.personage.specification.attribute.AttributeName;

import java.io.Serializable;

public class Effect {
    private AttributeName attributeName;
    private int value;

    private static final long serialVersionUID = -543847832486841389L;

    public Effect(AttributeName attributeName, int value) {
        this.attributeName = attributeName;
        this.value = value;
    }

    public AttributeName getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(AttributeName attributeName) {
        this.attributeName = attributeName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
