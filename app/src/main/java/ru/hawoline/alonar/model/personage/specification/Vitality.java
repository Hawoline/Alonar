package ru.hawoline.alonar.model.personage.specification;

import ru.hawoline.alonar.util.Pair;

public class Vitality {
    private VitalityType mType;
    private Pair<Integer, Integer> mResidualMax;

    public Vitality(VitalityType type, int max) {
        setVitality(type, new Pair<>(max, max));
    }

    public VitalityType getType() {
        return mType;
    }

    public Pair<Integer, Integer> getResidualMax() {
        return mResidualMax;
    }

    public void setVitality(VitalityType type, Pair<Integer, Integer> residualMax) {
        mType = type;
        mResidualMax = residualMax;
    }

    public void setMaxValue(int maxValue) {
        mResidualMax.setFirst(maxValue);
        mResidualMax.setSecond(maxValue);
    }

    public void setValue(int value) {
        if (value < getResidualMax().getFirst()) {
            mResidualMax.setSecond(value);
        }
    }
}
