package ru.hawoline.alonar.presenter;

import android.os.Bundle;
import ru.hawoline.alonar.view.MainAppView;

public class MainPresenterImpl implements MainPresenter {
    private MainAppView mMainView;

    public MainPresenterImpl() {
    }

    @Override
    public void attachView(MainAppView view) {
        this.mMainView = view;
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
}
