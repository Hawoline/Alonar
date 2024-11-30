package ru.hawoline.alonar.presenter;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import ru.hawoline.alonar.model.map.LandscapeMap;
import ru.hawoline.alonar.model.map.Map;
import ru.hawoline.alonar.model.personage.*;
import ru.hawoline.alonar.model.personage.enemy.Enemy;
import ru.hawoline.alonar.model.personage.heroclass.HeroClass;
import ru.hawoline.alonar.model.personage.inventory.Inventory;
import ru.hawoline.alonar.model.personage.item.Quality;
import ru.hawoline.alonar.model.personage.item.equipment.Body;
import ru.hawoline.alonar.model.personage.item.equipment.clothing.Clothing;
import ru.hawoline.alonar.model.personage.usecase.DamageComputationUseCase;
import ru.hawoline.alonar.model.personage.usecase.EnemyAttackComputationUseCase;
import ru.hawoline.alonar.util.Pair;
import ru.hawoline.alonar.view.GameFieldView;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class GameFieldPresenterImpl implements GameFieldPresenter {
    private GameFieldView view;
    private Map gameMap;
    private ArrayList<Enemy> enemiesAroundHero;
    private ArrayList<Enemy> nearbyEnemies;
    private Personage hero;
    private Location personageLocation;
    private HashMap<Personage, Location> personages;
    private ConcurrentHashMap<Enemy, Location> enemies;
    private EnemyAttackComputationUseCase enemyAttackComputationUseCase;

    public GameFieldPresenterImpl() {
        gameMap = new LandscapeMap(30);

        enemiesAroundHero = new ArrayList<>();
        nearbyEnemies = new ArrayList<>();

        personages = new HashMap<>();
        hero = PersonageFactory.createPersonage(HeroClass.MAGE);
        personageLocation = new Location(1, 1);
        personages.put(hero, personageLocation);
        hero.setInventory(new Inventory());
        for (int item = 0; item < 80; item++) {
            hero.getInventory().addItem(new Clothing("Helmet", 1, Quality.EXPENSIVE, new Pair<>(100, 100), Body.HEAD));
        }

        enemies = new ConcurrentHashMap<>();
        for (int enemyIndex = 0; enemyIndex < 80; enemyIndex++) {
            Enemy enemy = Enemy.createEnemy("Rat");
            enemies.put(enemy, new Location(
                    (int) Math.floor(Math.random() * (gameMap.getSize() - 2) + 1),
                    (int) Math.floor(Math.random() * (gameMap.getSize() - 2) + 1))
            );
        }
        enemyAttackComputationUseCase =
                new EnemyAttackComputationUseCase(enemies, new Pair<>(hero, personageLocation));
    }

    @Override
    public void attachView(GameFieldView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void saveInstance(Bundle state) {
        try {
            saveObject(hero, "Hero.out");
            saveObject(personageLocation, "HeroLocation.out");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restoreInstance(Bundle state) {
        try {
            FileInputStream heroFileInputStream = view.getContext().openFileInput("Hero.out");
            ObjectInputStream objectInputStream = new ObjectInputStream(heroFileInputStream);
            hero = (Personage) objectInputStream.readObject();
            objectInputStream.close();
            FileInputStream heroLocationFileInputStream = view.getContext().openFileInput("HeroLocation.out");
            ObjectInputStream heroLocationInputStream = new ObjectInputStream(heroLocationFileInputStream);
            personageLocation = (Location) heroLocationInputStream.readObject();
            heroLocationInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        enemyAttackComputationUseCase.setEnemies(enemies);
        enemyAttackComputationUseCase.setHero(new Pair<>(hero, personageLocation));
    }


    private void saveObject(Object object, String filename) throws IOException {
        FileOutputStream heroFileOutputStream = view.getContext().openFileOutput(filename, Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(heroFileOutputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
    }

    @Override
    public int[][] getGameMap() {
        return gameMap.getMap();
    }

    @Override
    public Personage getHero() {
        return hero;
    }

    @Override
    public Location getPersonageLocation() {
        if (hero.getHealth() < 1) {
            reBirthHero();
        }
        return personageLocation;
    }
    private void reBirthHero() {
        hero.setHealth(100);
        hero.setMp(1600);
        personageLocation.setX(1);
        personageLocation.setY(1);
    }

    @Override
    public synchronized void onPersonageMove(int x, int y) {
        Location personageLocation = getPersonageLocation();
        int newXCoordinate = personageLocation.getX() + x;
        int newYCoordinate = personageLocation.getY() + y;
        if (gameMap.getSize() - 1 > newXCoordinate && gameMap.getSize() - 1 > newYCoordinate && newXCoordinate > 0 && newYCoordinate > 0) {
            personageLocation.move(x, y);
        }
    }

    @Override
    public ArrayList<Enemy> findEnemiesAroundHero() {
        enemiesAroundHero.clear();
        ConcurrentHashMap<Enemy, Location> enemies = this.enemies;
        Location personageLocation = getPersonageLocation();
        for (Enemy enemy: enemies.keySet()) {
            Location enemyLocation = enemies.get(enemy);
            if (Math.abs(Objects.requireNonNull(enemyLocation).getX() - personageLocation.getX()) < 3
                    && Math.abs(Objects.requireNonNull(enemyLocation).getY() - personageLocation.getY()) < 3) {
                enemiesAroundHero.add(enemy);
            }
        }
        return enemiesAroundHero;
    }

    @Override
    public ArrayList<Enemy> findEnemiesAroundHero(int slotIndex) {
        enemiesAroundHero.clear();
        DamageSlot slot = hero.getDamageSlots().get(slotIndex);

        if (!(slot instanceof DamageSlot)) {
            return enemiesAroundHero;
        }
        DamageSlot damageSlot = (DamageSlot) slot;
        Location personageLocation = getPersonageLocation();
        int distance = damageSlot.getDistance();
        for (Enemy enemy: enemies.keySet()) {
            @NonNull
            Location enemyLocation = Objects.requireNonNull(enemies.get(enemy));
            if (checkDistance(personageLocation, enemyLocation, distance)) {
                enemiesAroundHero.add(enemy);
            }
        }

        return enemiesAroundHero;
    }

    @Override
    public ArrayList<Enemy> getNearbyEnemies() {
        nearbyEnemies.clear();
        for (Enemy enemy: enemies.keySet()) {
            Location enemyLocation = enemies.get(enemy);
            if (enemyLocation == null) {
                continue;
            }
            Location heroLocation = personageLocation;

            if (enemyLocation.getX() == heroLocation.getX() && enemyLocation.getY() == heroLocation.getY()) {
                nearbyEnemies.add(enemy);
            }
        }

        return nearbyEnemies;
    }

    @Override
    public void attackEnemy(Enemy enemy, int slotIndex) {
        if (!checkAttackDistanceFromHeroToEnemy(enemy, slotIndex)) {
            enemiesAroundHero.remove(enemy);
            view.removeEnemyTextView();
            return;
        }
        DamageComputationUseCase.compute(getHero(), enemy, slotIndex);
        if (enemy.getHealth() < 1) {
            enemies.remove(enemy);
            view.removeEnemyTextView();
        }
    }

    @Override
    public boolean checkAttackDistanceFromHeroToEnemy(Enemy enemy, int slotIndex) {
        DamageSlot slot = hero.getDamageSlots().get(slotIndex);
        DamageSlot damageSlot = (DamageSlot) slot;
        int damageSlotDistance = damageSlot.getDistance();

        Location enemyLocation = enemies.get(enemy);
        if (enemyLocation == null) {
            return false;
        }

        return checkDistance(personageLocation, enemyLocation, damageSlotDistance);
    }

    public boolean checkDistance(Location firstPersonageLocation, Location secondPersonageLocation, int requiredDistance) {
        int xDistanceBetweenPersonages = firstPersonageLocation.getX() - secondPersonageLocation.getX();
        int yDistanceBetweenPersonages = firstPersonageLocation.getY() - secondPersonageLocation.getY();
        int sum = Math.abs(xDistanceBetweenPersonages) + Math.abs(yDistanceBetweenPersonages);
        int diagonalSquaredDistance = xDistanceBetweenPersonages * xDistanceBetweenPersonages
                + yDistanceBetweenPersonages * yDistanceBetweenPersonages;

        boolean distanceIsCloserOrEqualToRequired = false;

        if (requiredDistance == 0) {
            if (xDistanceBetweenPersonages == 0 && yDistanceBetweenPersonages == 0) {
                distanceIsCloserOrEqualToRequired = true;
            }
        } else if (requiredDistance == 3) {
            if (sum < 2) {
                distanceIsCloserOrEqualToRequired = true;
            }
        } else if (requiredDistance == 4) {
            if (diagonalSquaredDistance == 2) {
                distanceIsCloserOrEqualToRequired = true;
            }
        } else if (requiredDistance == 6) {
            if (sum < 3) {
                distanceIsCloserOrEqualToRequired = true;
            }
        }

        return distanceIsCloserOrEqualToRequired;
    }

    @Override
    public Location getEnemyLocation(Enemy enemy) {
        return enemies.get(enemy);
    }
}
