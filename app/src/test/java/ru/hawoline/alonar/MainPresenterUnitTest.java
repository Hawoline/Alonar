package ru.hawoline.alonar;

import org.junit.Test;
import ru.hawoline.alonar.presenter.MainPresenterImpl;

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
}