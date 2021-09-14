package ru.hawoline.alonar.model.map;

import android.util.ArrayMap;
import ru.hawoline.alonar.model.personage.Enemy;
import ru.hawoline.alonar.model.personage.Location;
import ru.hawoline.alonar.model.personage.heroclass.Mage;
import ru.hawoline.alonar.model.personage.Personage;

import java.util.ArrayList;

public class Map {
    private int[][] mMap;
    private int mSize;
    private ArrayMap<Personage, Location> mPersonages = new ArrayMap<>();
    private ArrayMap<Enemy, Location> mEnemies = new ArrayMap<>();
    private Mage mPersonage;
    private Location mPersonageLocation;

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

        mPersonage = (Mage) Mage.createPersonage();
        mPersonageLocation = new Location(1, 1);
        mPersonages.put(mPersonage, mPersonageLocation);

        for (int enemyIndex = 0; enemyIndex < 20; enemyIndex++) {
            Enemy enemy = (Enemy) Enemy.createEnemy("Rat");
            mEnemies.put(enemy, new Location(
                    (int) Math.floor(Math.random() * (mMap.length - 2) + 1),
                    (int) Math.floor(Math.random() * mMap.length))
            );
        }
    }

    public void removeEnemy(Enemy enemy) {
        mEnemies.remove(enemy);
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

    public int getSize() {
        return mSize;
    }

    public Personage getPersonage() {
        return mPersonage;
    }

    public Location getPersonageLocation() {
        return mPersonages.get(mPersonage);
    }

    public ArrayMap<Enemy, Location> getEnemies() {
        return mEnemies;
    }
}
