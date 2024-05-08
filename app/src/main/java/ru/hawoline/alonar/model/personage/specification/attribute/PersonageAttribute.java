package ru.hawoline.alonar.model.personage.specification.attribute;

import java.io.Serializable;

public class PersonageAttribute implements Serializable {
    private int value;
    private int max;

    private static final long serialVersionUID = 5405874440905936295L;

    public PersonageAttribute(int max) {
        value = max;
        this.max = max;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value < max) {
            this.value = value;
        }
    }

    public void increase(int value) {
        max += value;
        this.value += value;
    }

    public void decrease(int value) {
        this.value -= value;
        if (this.value < 0) {
            this.value = 0;
        }
        max -= value;
        if (max < 0) {
            max = 0;
        }
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        if (this.max < value) {
            value = this.max;
        }
    }
}
