package ru.hawoline.alonar.util;

import java.io.Serializable;

public class Pair<T, V> implements Serializable {
    private T first;
    private V second;

    private static final long serialVersionUID = 7005557444853977271L;

    public Pair(T first, V second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public V getSecond() {
        return second;
    }

    public void setSecond(V second) {
        this.second = second;
    }
}
