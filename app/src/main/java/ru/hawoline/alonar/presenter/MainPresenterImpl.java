package ru.hawoline.alonar.presenter;

import android.os.Bundle;
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
    private ArrayList<Integer> mEnemiesAroundHero;

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
        if (mGameMap.getSize() > mGameMap.getPersonageLocation().getX() + x && mGameMap.getSize() > mGameMap.getPersonageLocation().getY() + y) {
            mGameMap.getPersonageLocation().move(x, y);
        }
    }

    @Override
    public ArrayList<Integer> findEnemiesAroundHero() {
        mEnemiesAroundHero.clear();
        for (Enemy enemy: mGameMap.getEnemies().keySet()) {
            if (Math.abs(mGameMap.getEnemies().get(enemy).getX() - mGameMap.getPersonageLocation().getX()) < 3
                    && Math.abs(mGameMap.getEnemies().get(enemy).getY() - mGameMap.getPersonageLocation().getY()) < 3) {
                mEnemiesAroundHero.add(mGameMap.getEnemies().indexOfKey(enemy));
            }
        }
        return mEnemiesAroundHero;
    }

    @Override
    public void enemyAttacked(int enemy) {
        Enemy attackedEnemy = mGameMap.getEnemies().keyAt(enemy);
        DamageComputationUseCase.compute(getPersonage(), attackedEnemy, 1);
        if (attackedEnemy.getHealth() < 1) {
            mGameMap.removeEnemy(enemy);
        }
    }

    @Override
    public Enemy getEnemyAt(int index) {
        return mGameMap.getEnemies().keyAt(index);
    }

    @Override
    public Location getEnemyLocationAt(int index) {
        return mGameMap.getEnemies().get(mGameMap.getEnemies().keyAt(index));
    }
}