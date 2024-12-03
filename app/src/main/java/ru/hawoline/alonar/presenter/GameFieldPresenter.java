package ru.hawoline.alonar.presenter;

import ru.hawoline.alonar.domain.model.personage.Location;
import ru.hawoline.alonar.domain.model.personage.Personage;
import ru.hawoline.alonar.view.GameFieldView;

public interface GameFieldPresenter extends Presenter<GameFieldView> {
    int[][] getGameMap();

    Personage getHero();

    Location getPersonageLocation();

    void onPersonageMove(int x, int y);
}
