package ru.hawoline.alonar.model.personage.usecase;

import ru.hawoline.alonar.model.personage.Location;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.enemy.Enemy;
import ru.hawoline.alonar.util.Pair;

import java.util.HashMap;

public final class EnemyAttackComputationUseCase implements Runnable {
    private HashMap<Enemy, Location> mEnemies;
    private Pair<Personage, Location> mHero;
    private Thread mThread;

    public EnemyAttackComputationUseCase(HashMap<Enemy, Location> enemies, Pair<Personage, Location> hero) {
        mEnemies = enemies;
        mHero = hero;
        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void run() {
        while (mHero.getFirst().getHealth() >= 1 && !mEnemies.isEmpty()) {

            for (Enemy enemy : mEnemies.keySet()) {
                if (enemy.getHealth() < 1) {
                    mEnemies.remove(enemy);
                    continue;
                }
                Location enemyLocation = mEnemies.get(enemy);
                Location heroLocation = mHero.getSecond();
                if (enemyLocation.getX() == heroLocation.getX() && enemyLocation.getY() == heroLocation.getY()) {
                    if (enemy.canAttack()) {
                        DamageComputationUseCase.compute(enemy, mHero.getFirst(), 0);
                    }
                }
            }
        }
    }

    public void setEnemies(HashMap<Enemy, Location> enemies) {
        mEnemies = enemies;
    }

    public void setHero(Pair<Personage, Location> hero) {
        mHero = hero;
    }
}
