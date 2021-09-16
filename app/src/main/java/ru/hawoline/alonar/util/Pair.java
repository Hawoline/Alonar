package ru.hawoline.alonar.util;

public class Pair<T, V> {
    private T mFirst;
    private V mSecond;

    public Pair(T first, V second) {
        mFirst = first;
        mSecond = second;
    }

    public T getFirst() {
        return mFirst;
    }

    public void setFirst(T first) {
        mFirst = first;
    }

    public V getSecond() {
        return mSecond;
    }

    public void setSecond(V second) {
        mSecond = second;
    }
}
