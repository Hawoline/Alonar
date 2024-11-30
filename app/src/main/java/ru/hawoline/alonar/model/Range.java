package ru.hawoline.alonar.model;

public class Range {
    private int low;
    private int high;
    private int distance;

    public Range(int low, int high) {
        this.low = low;
        this.high = high;
        calculateDistance();
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getDistance() {
        return distance;
    }

    private void calculateDistance() {
        distance = Math.abs(high - low);
    }
}
