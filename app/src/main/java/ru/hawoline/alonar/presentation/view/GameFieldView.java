package ru.hawoline.alonar.presentation.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import ru.hawoline.alonar.presentation.presenter.GameFieldPresenter;

public interface GameFieldView extends BaseView {
    void showEnemiesList(int slotIndex);

    void removeEnemyTextView();

    void render();

    void drawMap();

    void setParentGameLogLayout(LinearLayout parentGameLogLayout);

    GameFieldPresenter getGameFieldPresenter();

    void inflateLayout(Context context, LayoutInflater layoutInflater, FrameLayout root);
}
