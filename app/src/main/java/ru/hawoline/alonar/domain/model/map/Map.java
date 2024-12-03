package ru.hawoline.alonar.domain.model.map;

public abstract class Map {
    protected int[][] map;
    protected int size;

    public Map(int size) {
        this.size = size;
        map = new int[size][size];
    }

    public int[][] getMap() {
        return map;
    }

    public int getSize() {
        return size;
    }
}