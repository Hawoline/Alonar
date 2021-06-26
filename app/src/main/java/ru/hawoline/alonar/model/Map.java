package ru.hawoline.alonar.model;

import ru.hawoline.alonar.model.personage.Mage;
import ru.hawoline.alonar.model.personage.Personage;

public class Map {
    private int[][] mMap;
    private Personage mPersonage;
    private int mSize;

    public static final int GRASS = 0;
    public static final int MOUNTAIN = 1;


    public Map(int size) {
        mSize = size;
        mMap = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mMap[i][j] = GRASS;
            }
        }

        for (int i = 0; i < mMap.length; i++) {
            mMap[i][0] = MOUNTAIN;
        }
        for (int i = 0; i < mMap.length; i++) {
            mMap[0][i] = MOUNTAIN;
        }
        for (int i = 0; i < mMap.length; i++) {
            mMap[mMap.length - 1][i] = MOUNTAIN;
        }
        for (int i = 0; i < mMap.length; i++) {
            mMap[i][mMap.length - 1] = MOUNTAIN;
        }

        mPersonage = Mage.createPersonage();
        mPersonage.setX(1);
        mPersonage.setY(1);
    }

    public int[][] getMap() {
        return mMap;
    }

    public void setMap(int[][] map) {
        if (map.length == map[0].length) {
            mMap = map;
            mSize = map.length;
        }
    }

    public Personage getPersonage() {
        return mPersonage;
    }

    public int getSize() {
        return mSize;
    }
}
