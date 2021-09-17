package ru.hawoline.alonar.presenter;

import android.os.Bundle;
import android.util.ArrayMap;
import ru.hawoline.alonar.model.map.Map;
import ru.hawoline.alonar.model.personage.Enemy;
import ru.hawoline.alonar.model.personage.Location;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.usecase.DamageComputationUseCase;
import ru.hawoline.alonar.view.MainView;
import ru.hawoline.alonar.view.View;

import java.util.ArrayList;

public class MainPresenterImpl implements MainPresenter {
    private MainView mMainView;
    private Map mGameMap;
    private ArrayList<Enemy> mEnemiesAroundHero;

    public MainPresenterImpl() {
        mGameMap = new Map(30);
        mEnemiesAroundHero = new ArrayList<>();
    }

    @Override
    public void attachView(View view) {
        this.mMainView = (MainView) view;
    }

    @Override
    public void detachView() {
        this.mMainView = null;
    }

    @Override
    public void saveInstance(Bundle state) {

    }

    @Override
    public void restoreInstance(Bundle state) {

    }

    @Override
    public int[][] getGameMap() {
        return mGameMap.getMap();
    }

    @Override
    public Personage getPersonage() {
        return mGameMap.getPersonage();
    }

    @Override
    public Location getPersonageLocation() {
        return mGameMap.getPersonageLocation();
    }

    @Override
    public void move(int x, int y) {
        Location personageLocation = mGameMap.getPersonageLocation();
        if (mGameMap.getSize() > personageLocation.getX() + x && mGameMap.getSize() > personageLocation.getY() + y) {
            personageLocation.move(x, y);
        }
    }

    @Override
    public ArrayList<Enemy> findEnemiesAroundHero() {
        mEnemiesAroundHero.clear();
        ArrayMap<Enemy, Location> enemies = mGameMap.getEnemies();
        for (Enemy enemy: enemies.keySet()) {
            Location personageLocation = mGameMap.getPersonageLocation();
            if (Math.abs(enemies.get(enemy).getX() - personageLocation.getX()) < 3
                    && Math.abs(enemies.get(enemy).getY() - personageLocation.getY()) < 3) {
                mEnemiesAroundHero.add(enemy);
            }
        }
        return mEnemiesAroundHero;
    }

    @Override
    public void enemyAttacked(Enemy enemy) {
        DamageComputationUseCase.compute(getPersonage(), enemy, 1);
        if (enemy.getHealth() < 1) {
            mGameMap.removeEnemy(enemy);
            mMainView.removeEnemyTextView();
        }
    }

    @Override
    public Enemy getEnemyAt(int index) {
        return mGameMap.getEnemies().keyAt(index);
    }

    @Override
    public Location getEnemyLocation(Enemy index) {
        return mGameMap.getEnemies().get(index);
    }
}
