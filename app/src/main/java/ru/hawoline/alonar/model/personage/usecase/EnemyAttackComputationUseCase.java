package ru.hawoline.alonar.model.personage.usecase;

import ru.hawoline.alonar.model.personage.Location;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.enemy.Enemy;
import ru.hawoline.alonar.util.Pair;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class EnemyAttackComputationUseCase implements Runnable {
    private HashMap<Enemy, Location> mEnemies;
    private Pair<Personage, Location> mHero;
    private ExecutorService mExecutorService;
    private Thread mThread;

    public EnemyAttackComputationUseCase(HashMap<Enemy, Location> enemies, Pair<Personage, Location> hero) {
        mEnemies = enemies;
        mHero = hero;
    }

    @Override
    public void run() {
        try {
            while(true) {
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
                if (mHero.getFirst().getHealth() < 1 || mEnemies.isEmpty()) {
                    break;
                }
                for (Enemy enemy: mEnemies.keySet()) {
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startThread() {
        mExecutorService = Executors.newSingleThreadExecutor();
        mThread = new Thread(this);
        mExecutorService.execute(mThread);
    }

    public void stopThread() {
        mExecutorService.shutdownNow();
        mThread.interrupt();
    }

    public void setEnemies(HashMap<Enemy, Location> enemies) {
        mEnemies = enemies;
    }

    public void setHero(Pair<Personage, Location> hero) {
        mHero = hero;
    }
}
