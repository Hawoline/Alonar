package ru.hawoline.alonar.model;

public class Range {
    private int mLow;
    private int mHigh;

    public Range(int low, int high) {
        mLow = low;
        mHigh = high;
    }

    public int getLow() {
        return mLow;
    }

    public void setLow(int low) {
        mLow = low;
    }

    public int getHigh() {
        return mHigh;
    }

    public void setHigh(int high) {
        mHigh = high;
    }
}
