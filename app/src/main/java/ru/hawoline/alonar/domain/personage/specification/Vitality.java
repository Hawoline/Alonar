package ru.hawoline.alonar.domain.model.personage.specification;

import ru.hawoline.alonar.util.Pair;

import java.io.Serializable;

public class Vitality implements Serializable {
    private VitalityType type;
    private Pair<Integer, Integer> residualMax;

    private static final long serialVersionUID = -990341370978716524L;

    public static final int HP = 0;
    public static final int MP = 1;

    public Vitality(VitalityType type, int max) {
        setVitality(type, new Pair<>(max, max));
    }

    public VitalityType getType() {
        return type;
    }

    public Pair<Integer, Integer> getResidualMax() {
        return residualMax;
    }

    public void setVitality(VitalityType type, Pair<Integer, Integer> residualMax) {
        this.type = type;
        this.residualMax = residualMax;
    }

    public void setMaxValue(int maxValue) {
        residualMax.setFirst(maxValue);
        residualMax.setSecond(maxValue);
    }

    public void setValue(int value) {
        if (value > getResidualMax().getFirst()) {
            setMaxValue(value);
        } else {
            residualMax.setSecond(value);
        }
    }
}
