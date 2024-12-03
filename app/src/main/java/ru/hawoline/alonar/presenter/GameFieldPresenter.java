package ru.hawoline.alonar.presenter;

import ru.hawoline.alonar.model.personage.Location;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.view.GameFieldView;

import java.util.ArrayList;

public interface GameFieldPresenter extends Presenter<GameFieldView> {
    int[][] getGameMap();

    Personage getHero();

    Location getPersonageLocation();

    void onPersonageMove(int x, int y);
}
