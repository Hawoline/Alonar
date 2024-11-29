package ru.hawoline.alonar.presentation.presenter;

import ru.hawoline.alonar.domain.model.personage.Location;
import ru.hawoline.alonar.domain.model.personage.Personage;
import ru.hawoline.alonar.domain.model.personage.enemy.Enemy;
import ru.hawoline.alonar.presentation.view.GameFieldView;

import java.util.ArrayList;

public interface GameFieldPresenter extends Presenter<GameFieldView> {
    int[][] getGameMap();

    Personage getHero();

    Location getPersonageLocation();

    void onPersonageMove(int x, int y);

    ArrayList<Enemy> findEnemiesAroundHero();

    ArrayList<Enemy> findEnemiesAroundHero(int slotIndex);

    ArrayList<Enemy> getNearbyEnemies();

    void attackEnemy(Enemy enemy, int slotIndex);

    boolean checkAttackDistanceFromHeroToEnemy(Enemy enemy, int slotIndex);

    Location getEnemyLocation(Enemy enemy);

}
