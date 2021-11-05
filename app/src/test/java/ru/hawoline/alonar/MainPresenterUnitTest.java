package ru.hawoline.alonar;

import android.util.Log;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.hawoline.alonar.model.personage.Location;
import ru.hawoline.alonar.model.personage.enemy.Enemy;
import ru.hawoline.alonar.presenter.GameFieldPresenter;
import ru.hawoline.alonar.presenter.GameFieldPresenterImpl;

import java.util.ArrayList;

public class MainPresenterUnitTest {
    private GameFieldPresenter mGameFieldPresenter;
    private long counter;

    @Before
    public void initPresenter() {
        mGameFieldPresenter = new GameFieldPresenterImpl();
        counter = 0;
    }

    @Test
    public void testEnemyAttacks() {
        do {
            mGameFieldPresenter.onPersonageMove(1, 1);
        } while (mGameFieldPresenter.findEnemiesAroundHero().isEmpty());

        ArrayList<Enemy> enemies = mGameFieldPresenter.findEnemiesAroundHero();
        Location enemyLocation = mGameFieldPresenter.getEnemyLocation(enemies.get(0));
        mGameFieldPresenter.getPersonageLocation().setX(enemyLocation.getX());
        mGameFieldPresenter.getPersonageLocation().setY(enemyLocation.getY());

        TestCase.assertEquals(100, mGameFieldPresenter.getPersonage().getHealth());
        while (mGameFieldPresenter.getPersonage().getHealth() > 0) {
            System.out.println(mGameFieldPresenter.getPersonage().getHealth());
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        TestCase.assertEquals(1, mGameFieldPresenter.getPersonageLocation().getX());
        TestCase.assertEquals(1, mGameFieldPresenter.getPersonageLocation().getY());
        TestCase.assertEquals(100, mGameFieldPresenter.getPersonage().getHealth());
    }

    @Test
    public void checkFalseAttacks() {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 3600 * 1000) {
            int randomPersonageMovementX = (int) (Math.random() * 5) - 2;
            int randomPersonageMovementY = (int) (Math.random() * 5) - 2;
            boolean isEnemyCloseToHero = false;
            for (Enemy enemy: mGameFieldPresenter.findEnemiesAroundHero()) {
                Location enemyLocation = mGameFieldPresenter.getEnemyLocation(enemy);
                Location heroLocation = mGameFieldPresenter.getPersonageLocation();
                if (heroLocation.getX() + randomPersonageMovementX == enemyLocation.getX() && heroLocation.getY() + randomPersonageMovementY == enemyLocation.getY()) {
                    isEnemyCloseToHero = true;
                    break;
                }
            }

            if (!isEnemyCloseToHero) {
                mGameFieldPresenter.onPersonageMove(randomPersonageMovementX, randomPersonageMovementY);
                TestCase.assertEquals(100, mGameFieldPresenter.getPersonage().getHealth());
                counter++;
            }
        }
    }

    @After
    public void showHeroAndEnemyLocations() {
        for (Enemy enemy: mGameFieldPresenter.findEnemiesAroundHero()) {
            Location enemyLocation = mGameFieldPresenter.getEnemyLocation(enemy);
            System.out.println("Enemy location: " + enemyLocation.getX() + " " + enemyLocation.getY());
        }

        Location heroLocation = mGameFieldPresenter.getPersonageLocation();
        System.out.println("\nHero location: " + heroLocation.getX() + " " + heroLocation.getY());

        System.out.println("Iterations: " + counter);
    }
}