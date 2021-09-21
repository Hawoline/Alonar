package ru.hawoline.alonar.presenter;

import android.os.Bundle;
import ru.hawoline.alonar.model.map.LandscapeMap;
import ru.hawoline.alonar.model.map.Map;
import ru.hawoline.alonar.model.personage.*;
import ru.hawoline.alonar.model.personage.heroclass.HeroClass;
import ru.hawoline.alonar.model.personage.usecase.DamageComputationUseCase;
import ru.hawoline.alonar.view.MainView;
import ru.hawoline.alonar.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public class MainPresenterImpl implements MainPresenter {
    private MainView mMainView;
    private Map mGameMap;
    private ArrayList<Enemy> mEnemiesAroundHero;
    private Personage mPersonage;
    private Location mPersonageLocation;
    private HashMap<Personage, Location> mPersonages;
    private HashMap<Enemy, Location> mEnemies;

    public MainPresenterImpl() {
        mGameMap = new LandscapeMap(30);
        mEnemiesAroundHero = new ArrayList<>();

        mPersonages = new HashMap<>();
        mPersonage = PersonageFactory.createPersonage(HeroClass.MAGE);
        mPersonageLocation = new Location(1, 1);
        mPersonages.put(mPersonage, mPersonageLocation);

        mEnemies = new HashMap<>();
        for (int enemyIndex = 0; enemyIndex < 20; enemyIndex++) {
            Enemy enemy = (Enemy) Enemy.createEnemy("Rat");
            mEnemies.put(enemy, new Location(
                    (int) Math.floor(Math.random() * (mGameMap.getSize() - 2) + 1),
                    (int) Math.floor(Math.random() * mGameMap.getSize()))
            );
        }
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
        return mPersonage;
    }

    @Override
    public Location getPersonageLocation() {
        return mPersonageLocation;
    }

    @Override
    public void onPersonageMove(int x, int y) {
        Location personageLocation = getPersonageLocation();
        if (mGameMap.getSize() > personageLocation.getX() + x && mGameMap.getSize() > personageLocation.getY() + y) {
            personageLocation.move(x, y);
        }
    }

    @Override
    public ArrayList<Enemy> findEnemiesAroundHero() {
        mEnemiesAroundHero.clear();
        HashMap<Enemy, Location> enemies = mEnemies;
        Location personageLocation = getPersonageLocation();
        for (Enemy enemy: enemies.keySet()) {
            if (Math.abs(enemies.get(enemy).getX() - personageLocation.getX()) < 3
                    && Math.abs(enemies.get(enemy).getY() - personageLocation.getY()) < 3) {
                mEnemiesAroundHero.add(enemy);
            }
        }
        return mEnemiesAroundHero;
    }

    @Override
    public ArrayList<Enemy> findEnemiesAroundHero(int slotIndex) {
        mEnemiesAroundHero.clear();
        DamageSlot slot = (DamageSlot) mPersonage.getSlots().get(slotIndex);
        Location personageLocation = getPersonageLocation();
        int distance = slot.getDistance();
        for (Enemy enemy: mEnemies.keySet()) {
            Location enemyLocation = mEnemies.get(enemy);
            int xDistance = personageLocation.getX() - enemyLocation.getX();
            int yDistance = personageLocation.getY() - enemyLocation.getY();
            int sum = Math.abs(xDistance) + Math.abs(yDistance);
            int diagonalSquaredDistance = xDistance * xDistance + yDistance * yDistance;
            switch (slot.getDistance()) {
                case 0:
                    if (xDistance == 0 && yDistance == 0) {
                        mEnemiesAroundHero.add(enemy);
                    }
                    break;
                case 3:
                    if (sum < 2) {
                        mEnemiesAroundHero.add(enemy);
                    }
                    break;
                case 4:
                    if (diagonalSquaredDistance == 2) {
                        mEnemiesAroundHero.add(enemy);
                    }
                    break;
                case 6:
                    if (sum < 3) {
                        mEnemiesAroundHero.add(enemy);
                    }
                    break;
            }
        }

        return mEnemiesAroundHero;
    }

    @Override
    public void enemyAttacked(Enemy enemy) {
        DamageComputationUseCase.compute(getPersonage(), enemy, 1);
        if (enemy.getHealth() < 1) {
            mEnemies.remove(enemy);
            mMainView.removeEnemyTextView();
        }
    }

    @Override
    public Location getEnemyLocation(Enemy enemy) {
        return mEnemies.get(enemy);
    }
}
