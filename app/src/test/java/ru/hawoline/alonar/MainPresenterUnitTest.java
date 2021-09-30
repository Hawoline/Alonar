package ru.hawoline.alonar;

import android.util.Log;
import junit.framework.TestCase;
import org.junit.Test;
import ru.hawoline.alonar.model.personage.Location;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.enemy.Enemy;
import ru.hawoline.alonar.presenter.MainPresenterImpl;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MainPresenterUnitTest {

    @Test
    public void testEnemiesAroundHero() {
        MainPresenterImpl mainPresenter = new MainPresenterImpl();
        mainPresenter.findEnemiesAroundHero();
    }

    @Test
    public void testEnemyAttacks() {
        MainPresenterImpl mainPresenter = new MainPresenterImpl();
        do {
            mainPresenter.onPersonageMove(1, 1);
        } while (mainPresenter.findEnemiesAroundHero().isEmpty());

        ArrayList<Enemy> enemies = mainPresenter.findEnemiesAroundHero();
        Location enemyLocation = mainPresenter.getEnemyLocation(enemies.get(0));
        mainPresenter.getPersonageLocation().setX(enemyLocation.getX());
        mainPresenter.getPersonageLocation().setY(enemyLocation.getY());
        while(mainPresenter.getPersonage().getHealth() > 0) {
            System.out.println(mainPresenter.getPersonage().getHealth());
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        TestCase.assertEquals(1, mainPresenter.getPersonageLocation().getX());
        TestCase.assertEquals(1, mainPresenter.getPersonageLocation().getY());
        TestCase.assertEquals(100, mainPresenter.getPersonage().getHealth());
    }
}