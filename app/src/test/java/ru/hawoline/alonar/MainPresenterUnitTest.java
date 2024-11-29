package ru.hawoline.alonar;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.hawoline.alonar.domain.model.personage.Location;
import ru.hawoline.alonar.domain.model.personage.enemy.Enemy;
import ru.hawoline.alonar.presentation.presenter.GameFieldPresenter;
import ru.hawoline.alonar.presentation.presenter.GameFieldPresenterImpl;

import java.util.ArrayList;

public class MainPresenterUnitTest {
    private GameFieldPresenter gameFieldPresenter;
    private long counter;

    @Before
    public void initPresenter() {
        gameFieldPresenter = new GameFieldPresenterImpl();
        counter = 0;
    }

    @Test
    public void testEnemyAttacks() {
        do {
            gameFieldPresenter.onPersonageMove(1, 1);
        } while (gameFieldPresenter.findEnemiesAroundHero().isEmpty());

        ArrayList<Enemy> enemies = gameFieldPresenter.findEnemiesAroundHero();
        Location enemyLocation = gameFieldPresenter.getEnemyLocation(enemies.get(0));
        gameFieldPresenter.getPersonageLocation().setX(enemyLocation.getX());
        gameFieldPresenter.getPersonageLocation().setY(enemyLocation.getY());

        TestCase.assertEquals(100, gameFieldPresenter.getHero().getHealth());
        while (gameFieldPresenter.getHero().getHealth() > 0) {
            System.out.println(gameFieldPresenter.getHero().getHealth());
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        TestCase.assertEquals(1, gameFieldPresenter.getPersonageLocation().getX());
        TestCase.assertEquals(1, gameFieldPresenter.getPersonageLocation().getY());
        TestCase.assertEquals(100, gameFieldPresenter.getHero().getHealth());
        TestCase.assertEquals(1600, gameFieldPresenter.getHero().getMp());
    }

    @Test
    public void checkFalseAttacks() {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 3600 * 1000) {
            int randomPersonageMovementX = (int) (Math.random() * 5) - 2;
            int randomPersonageMovementY = (int) (Math.random() * 5) - 2;
            boolean isEnemyCloseToHero = false;
            for (Enemy enemy: gameFieldPresenter.findEnemiesAroundHero()) {
                Location enemyLocation = gameFieldPresenter.getEnemyLocation(enemy);
                Location heroLocation = gameFieldPresenter.getPersonageLocation();
                if (heroLocation.getX() + randomPersonageMovementX == enemyLocation.getX() && heroLocation.getY() + randomPersonageMovementY == enemyLocation.getY()) {
                    isEnemyCloseToHero = true;
                    break;
                }
            }

            if (!isEnemyCloseToHero) {
                gameFieldPresenter.onPersonageMove(randomPersonageMovementX, randomPersonageMovementY);
                TestCase.assertEquals(100, gameFieldPresenter.getHero().getHealth());
                counter++;
            }
        }
    }

    @After
    public void showHeroAndEnemyLocations() {
        for (Enemy enemy: gameFieldPresenter.findEnemiesAroundHero()) {
            Location enemyLocation = gameFieldPresenter.getEnemyLocation(enemy);
            System.out.println("Enemy location: " + enemyLocation.getX() + " " + enemyLocation.getY());
        }

        Location heroLocation = gameFieldPresenter.getPersonageLocation();
        System.out.println("\nHero location: " + heroLocation.getX() + " " + heroLocation.getY());

        System.out.println("Iterations: " + counter);
    }
}