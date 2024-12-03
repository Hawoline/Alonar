package ru.hawoline.alonar.view;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import ru.hawoline.alonar.presenter.GameFieldPresenter;

public interface GameFieldView extends BaseView {
    void render();

    void drawMap();

    void setParentGameLogLayout(LinearLayout parentGameLogLayout);

    GameFieldPresenter getGameFieldPresenter();

    void inflateLayout(Context context, LayoutInflater layoutInflater, FrameLayout root);

  void drawAnotherPersonage(int x, int y);
}
