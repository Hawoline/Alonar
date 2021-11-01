package ru.hawoline.alonar;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import ru.hawoline.alonar.model.personage.Location;
import ru.hawoline.alonar.model.personage.enemy.Enemy;
import ru.hawoline.alonar.presenter.MainPresenterImpl;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MainPresenterUnitTest {
    private MainPresenterImpl mMainPresenter;

    @Before
    public void testEnemiesAroundHero() {
        mMainPresenter = new MainPresenterImpl();
    }

    @Test
    public void testEnemyAttacks() {
        do {
            mMainPresenter.onPersonageMove(1, 1);
        } while (mMainPresenter.findEnemiesAroundHero().isEmpty());

        ArrayList<Enemy> enemies = mMainPresenter.findEnemiesAroundHero();
        Location enemyLocation = mMainPresenter.getEnemyLocation(enemies.get(0));
        mMainPresenter.getPersonageLocation().setX(enemyLocation.getX());
        mMainPresenter.getPersonageLocation().setY(enemyLocation.getY());
        while(mMainPresenter.getPersonage().getHealth() > 0) {
            System.out.println(mMainPresenter.getPersonage().getHealth());
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        TestCase.assertEquals(1, mMainPresenter.getPersonageLocation().getX());
        TestCase.assertEquals(1, mMainPresenter.getPersonageLocation().getY());
        TestCase.assertEquals(100, mMainPresenter.getPersonage().getHealth());
    }
}