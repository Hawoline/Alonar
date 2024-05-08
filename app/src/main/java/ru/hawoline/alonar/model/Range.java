package ru.hawoline.alonar.model;

import java.io.Serializable;

public class Range implements Serializable {
    private int low;
    private int high;
    private int distance;

    private static final long serialVersionUID = -3928741113671976358L;

    public Range(int low, int high) {
        this.low = low;
        this.high = high;
        setDistance();
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
        setDistance();
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
        setDistance();
    }

    public int getDistance() {
        return distance;
    }

    private void setDistance() {
        distance = Math.abs(high - low);
    }
}
