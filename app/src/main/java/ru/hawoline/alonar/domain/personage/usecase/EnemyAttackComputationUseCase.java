package ru.hawoline.alonar.domain.model.personage.usecase;

import ru.hawoline.alonar.domain.model.personage.Location;
import ru.hawoline.alonar.domain.model.personage.Personage;
import ru.hawoline.alonar.domain.model.personage.enemy.Enemy;
import ru.hawoline.alonar.util.Pair;

import java.util.concurrent.ConcurrentHashMap;

public final class EnemyAttackComputationUseCase implements Runnable {
    private ConcurrentHashMap<Enemy, Location> enemies;
    private Pair<Personage, Location> hero;
    private Thread thread;
    private long lastUpdate;

    private final int UPDATE_TIME = 60;

    public EnemyAttackComputationUseCase(ConcurrentHashMap<Enemy, Location> enemies, Pair<Personage, Location> hero) {
        this.enemies = enemies;
        this.hero = hero;
        thread = new Thread(this);
        thread.start();
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
        while (hero.getFirst().getHealth() >= 1 && !enemies.isEmpty()) {
            if (!checkUpdateTime()) {
                continue;
            }

            performEnemyAttacks();
        }
    }

    private void waitHeroHealthRestoration() throws InterruptedException {
        while (hero.getFirst().getHealth() < 1) {
            Thread.sleep(1000 / UPDATE_TIME);
        }
    }

    private void performEnemyAttacks() {
        for (Enemy enemy : enemies.keySet()) {
            if (enemy.getHealth() < 1) {
                enemies.remove(enemy);
                continue;
            }
            performAttack(enemy, hero.getSecond(), enemies.get(enemy));
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
            DamageComputationUseCase.compute(enemy, hero.getFirst(), 0);
            enemy.attack();
        }
    }

    public void setEnemies(ConcurrentHashMap<Enemy, Location> enemies) {
        this.enemies = enemies;
    }

    public void setHero(Pair<Personage, Location> hero) {
        this.hero = hero;
    }
}
