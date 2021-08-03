package ru.hawoline.alonar.model.map;

import ru.hawoline.alonar.model.personage.Enemy;
import ru.hawoline.alonar.model.personage.Mage;
import ru.hawoline.alonar.model.personage.Personage;

public class Map {
    private int[][] mMap;
    private int[][] mEnemiesMap;
    private int mSize;
    private Personage mPersonage;
    private Enemy[] mEnemies;

    public static final int GRASS = 0;
    public static final int MOUNTAIN = 1;

    public static final int NO_ENEMIES = 0;
    public static final int ENEMY_RAT = 1;


    public Map(int size) {
        mSize = size;
        mMap = new int[size][size];
        mEnemiesMap = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mMap[i][j] = GRASS;
                mEnemiesMap[i][j] = NO_ENEMIES;
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

        mEnemies = new Enemy[10];

        for (int i = 0; i < mEnemies.length; i++) {
            mEnemies[i] = (Enemy) Enemy.createEnemy("Rat");
            mEnemies[i].setX((int) Math.floor(Math.random() * (mMap.length - 2) + 1));
            mEnemies[i].setY((int) Math.floor(Math.random() * mMap.length));
            mEnemiesMap[mEnemies[i].getY()][mEnemies[i].getX()] = ENEMY_RAT;
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(mEnemiesMap[i][j]);
            }
            System.out.println();
        }
    }

    public int[][] getMap() {
        return mMap;
    }

    public int[][] getEnemiesMap() {
        return mEnemiesMap;
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

    public Enemy[] getEnemies() {
        return mEnemies;
    }
}
