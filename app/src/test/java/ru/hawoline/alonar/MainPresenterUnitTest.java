package ru.hawoline.alonar;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import ru.hawoline.alonar.model.personage.Location;
import ru.hawoline.alonar.model.personage.enemy.Enemy;
import ru.hawoline.alonar.presenter.GameFieldPresenter;
import ru.hawoline.alonar.presenter.GameFieldPresenterImpl;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MainPresenterUnitTest {
    private GameFieldPresenter mGameFieldPresenter;

    @Before
    public void testEnemiesAroundHero() {
        mGameFieldPresenter = new GameFieldPresenterImpl();
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
}