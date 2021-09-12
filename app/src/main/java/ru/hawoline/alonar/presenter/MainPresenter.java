package ru.hawoline.alonar.presenter;

import android.util.ArrayMap;
import ru.hawoline.alonar.model.personage.Enemy;
import ru.hawoline.alonar.model.personage.Location;
import ru.hawoline.alonar.model.personage.Personage;

import java.util.ArrayList;

public interface MainPresenter extends Presenter {
    int[][] getGameMap();

    Personage getPersonage();

    Location getPersonageLocation();

    void move(int x, int y);

    ArrayList<Integer> findEnemiesAroundHero();

    void enemyAttacked(int enemy);

    Enemy getEnemyAt(int index);

    Location getEnemyLocationAt(int index);
}
