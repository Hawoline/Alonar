package ru.hawoline.alonar.presenter;

import android.os.Bundle;
import ru.hawoline.alonar.view.AppView;

public interface Presenter<T extends AppView> {
    void attachView(T view);

    void detachView();

    void saveInstance(Bundle state);

    void restoreInstance(Bundle state);
}
