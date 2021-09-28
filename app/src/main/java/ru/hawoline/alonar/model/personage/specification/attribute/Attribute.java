package ru.hawoline.alonar.model.personage.specification.attribute;

import java.io.Serializable;

public class Attribute implements Serializable {
    private int mValue;
    private int mMax;

    private static final long serialVersionUID = 5405874440905936295L;

    public Attribute(int max) {
        mValue = max;
        mMax = max;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        if (value < mMax) {
            mValue = value;
        }
    }

    public void increase(int value) {
        if (mValue + value < mMax) {
            mValue += value;
        }
    }

    public int getMax() {
        return mMax;
    }

    public void setMax(int max) {
        mMax = max;
        if (mMax < mValue) {
            mValue = mMax;
        }
    }
}
