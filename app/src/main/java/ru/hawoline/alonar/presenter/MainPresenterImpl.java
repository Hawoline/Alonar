package ru.hawoline.alonar.presenter;

import android.os.Bundle;
import ru.hawoline.alonar.model.Map;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.view.MainView;
import ru.hawoline.alonar.view.View;

public class MainPresenterImpl implements MainPresenter {
    private MainView mMainView;
    private Map mGameMap;

    public MainPresenterImpl() {
        mGameMap = new Map(30);
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
    public void move(int x, int y) {
        if (mGameMap.getSize() > mGameMap.getPersonage().getX() + x && mGameMap.getSize() > mGameMap.getPersonage().getY() + y) {
            mGameMap.getPersonage().move(x, y);
        }
    }
}
