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

    void onPersonageMove(int x, int y);

    ArrayList<Enemy> findEnemiesAroundHero();

    ArrayList<Enemy> findEnemiesAroundHero(int slotIndex);

    void enemyAttacked(Enemy enemy);

    Location getEnemyLocation(Enemy enemy);
}
