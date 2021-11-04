package ru.hawoline.alonar.view;

import android.widget.LinearLayout;

public interface GameFieldView extends AppView {
    void showEnemiesList(int slotIndex);

    void removeEnemyTextView();

    void render();

    public void setParentGameLogLayout(LinearLayout parentGameLogLayout);
}
