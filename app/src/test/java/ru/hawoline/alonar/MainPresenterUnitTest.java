package ru.hawoline.alonar;

import org.junit.Before;
import ru.hawoline.alonar.presenter.GameFieldPresenter;
import ru.hawoline.alonar.presenter.GameFieldPresenterImpl;

public class MainPresenterUnitTest {
    private GameFieldPresenter gameFieldPresenter;
    private long counter;

    @Before
    public void initPresenter() {
        gameFieldPresenter = new GameFieldPresenterImpl();
        counter = 0;
    }
}