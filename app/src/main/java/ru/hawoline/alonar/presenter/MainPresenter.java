package ru.hawoline.alonar.presenter;

import ru.hawoline.alonar.model.Map;
import ru.hawoline.alonar.model.personage.Personage;

public interface MainPresenter extends Presenter {
    int[][] getGameMap();

    Personage getPersonage();

    void move(int x, int y);
}
