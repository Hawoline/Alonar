package ru.hawoline.alonar.presenter;

import ru.hawoline.alonar.model.personage.Enemy;
import ru.hawoline.alonar.model.personage.Personage;

import java.util.ArrayList;

public interface MainPresenter extends Presenter {
    int[][] getGameMap();

    int[][] getEnemiesMap();

    Personage getPersonage();

    void move(int x, int y);

    ArrayList<Enemy> getEnemiesAroundHero();
}
