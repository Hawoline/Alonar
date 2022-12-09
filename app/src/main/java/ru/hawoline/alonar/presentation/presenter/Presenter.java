package ru.hawoline.alonar.presentation.presenter;

import android.os.Bundle;
import ru.hawoline.alonar.presentation.view.BaseView;

public interface Presenter<T extends BaseView> {
    void attachView(T view);

    void detachView();

    void saveInstance(Bundle state);

    void restoreInstance(Bundle state);
}
