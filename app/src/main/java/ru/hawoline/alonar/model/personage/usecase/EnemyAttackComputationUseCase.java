package ru.hawoline.alonar.model.personage.usecase;

import ru.hawoline.alonar.model.personage.Location;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.enemy.Enemy;
import ru.hawoline.alonar.util.Pair;

import java.util.concurrent.ConcurrentHashMap;

public final class EnemyAttackComputationUseCase implements Runnable {
    private ConcurrentHashMap<Enemy, Location> mEnemies;
    private Pair<Personage, Location> mHero;
    private Thread mThread;
    private long lastUpdate;

    private final int UPDATE_TIME = 60;

    public EnemyAttackComputationUseCase(ConcurrentHashMap<Enemy, Location> enemies, Pair<Personage, Location> hero) {
        mEnemies = enemies;
        mHero = hero;
        mThread = new Thread(this);
        mThread.start();
        lastUpdate = System.currentTimeMillis();
    }

    @Override
    public void run() {
        try {
            for (; ; ) {
                computeEnemyAttacks();
                waitHeroHealthRestoration();
            }
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    private void computeEnemyAttacks() {
        while (mHero.getFirst().getHealth() >= 1 && !mEnemies.isEmpty()) {
            if (!checkUpdateTime()) {
                continue;
            }

            performEnemyAttacks();
        }
    }

    private void waitHeroHealthRestoration() throws InterruptedException {
        while (mHero.getFirst().getHealth() < 1) {
            Thread.sleep(1000 / UPDATE_TIME);
        }
    }

    private void performEnemyAttacks() {
        for (Enemy enemy : mEnemies.keySet()) {
            if (enemy.getHealth() < 1) {
                mEnemies.remove(enemy);
                continue;
            }
            performAttack(enemy, mHero.getSecond(), mEnemies.get(enemy));
        }
    }

    private boolean checkUpdateTime() {
        long currentUpdateTime = System.currentTimeMillis();
        if (currentUpdateTime - lastUpdate <= 1000 / UPDATE_TIME) {
            return false;
        }
        lastUpdate = currentUpdateTime;
        return true;
    }

    private synchronized void performAttack(Enemy enemy, Location personageLocation, Location enemyLocation) {
        if (enemy.canAttack()) {
            if (enemyLocation.getX() != personageLocation.getX() || enemyLocation.getY() != personageLocation.getY()) {
                return;
            }
            DamageComputationUseCase.compute(enemy, mHero.getFirst(), 0);
            enemy.attack();
        }
    }

    public void setEnemies(ConcurrentHashMap<Enemy, Location> enemies) {
        mEnemies = enemies;
    }

    public void setHero(Pair<Personage, Location> hero) {
        mHero = hero;
    }
}
