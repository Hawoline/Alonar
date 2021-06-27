package ru.hawoline.alonar.presenter;

import android.os.Bundle;
import ru.hawoline.alonar.model.Map;
import ru.hawoline.alonar.model.personage.Enemy;
import ru.hawoline.alonar.model.personage.Personage;
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
    public int[][] getEnemiesMap() {
        return mGameMap.getEnemiesMap();
    }

    @Override
    public Personage getPersonage() {
        return mGameMap.getPersonage();
    }

    @Override
    public void move(int x, int y) {
        if (mGameMap.getSize() > mGameMap.getPersonage().getX() + x && mGameMap.getSize() > mGameMap.getPersonage().getY() + y) {
            mGameMap.getPersonage().move(x, y);
        }
    }

    private void findEnemiesAroundHero() {
        int[][] enemiesMap = mGameMap.getEnemiesMap();
        ArrayList<Enemy> enemiesAroundHero = new ArrayList<>();

        for (int i = mGameMap.getPersonage().getY() - 2; i < mGameMap.getPersonage().getY() + 3; i++) {
            for (int j = mGameMap.getPersonage().getX() - 2; j < mGameMap.getPersonage().getX() + 3; j++) {
                if (enemiesMap[i][j] == Map.ENEMY_RAT) {
                    enemiesAroundHero.add((Enemy) Enemy.createEnemy("Rat"));
                }
            }
        }
        mEnemiesAroundHero = enemiesAroundHero;
    }
}
