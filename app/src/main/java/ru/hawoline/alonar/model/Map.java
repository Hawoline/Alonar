package ru.hawoline.alonar.model;

public class Map {
    private int[][] mMap;
    private Personage[] mPersonages;
    private int mSize;

    private static final int GRASS = 0;

    public Map(int size) {
        mSize = size;
        mMap = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mMap[i][j] = GRASS;
            }
        }
    }

    public int[][] getMap() {
        return mMap;
    }

    public void setMap(int[][] map) {
        if (map.length == map[0].length) {
            mMap = map;
        }
    }

    public int getSize() {
        return mSize;
    }
}
