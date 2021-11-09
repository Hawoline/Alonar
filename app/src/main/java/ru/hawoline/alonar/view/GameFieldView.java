package ru.hawoline.alonar.view;

import android.widget.LinearLayout;
import ru.hawoline.alonar.presenter.GameFieldPresenter;

public interface GameFieldView extends BaseView {
    void showEnemiesList(int slotIndex);

    void removeEnemyTextView();

    void render();

    void drawMap();

    void setParentGameLogLayout(LinearLayout parentGameLogLayout);

    GameFieldPresenter getGameFieldPresenter();
}
