package ru.hawoline.alonar.presenter;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import ru.hawoline.alonar.model.map.LandscapeMap;
import ru.hawoline.alonar.model.map.Map;
import ru.hawoline.alonar.model.personage.*;
import ru.hawoline.alonar.model.personage.enemy.Enemy;
import ru.hawoline.alonar.model.personage.heroclass.HeroClass;
import ru.hawoline.alonar.model.personage.usecase.DamageComputationUseCase;
import ru.hawoline.alonar.model.personage.usecase.EnemyAttackComputationUseCase;
import ru.hawoline.alonar.util.Pair;
import ru.hawoline.alonar.view.GameFieldView;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameFieldPresenterImpl implements GameFieldPresenter {
    private GameFieldView mView;
    private Map mGameMap;
    private ArrayList<Enemy> mEnemiesAroundHero;
    private ArrayList<Enemy> mNearbyEnemies;
    private Personage mPersonage;
    private Location mPersonageLocation;
    private HashMap<Personage, Location> mPersonages;
    private ConcurrentHashMap<Enemy, Location> mEnemies;
    private EnemyAttackComputationUseCase mEnemyAttackComputationUseCase;

    private int[][] mEnemiesMap = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    public GameFieldPresenterImpl() {
        mGameMap = new LandscapeMap(30);

        mEnemiesAroundHero = new ArrayList<>();
        mNearbyEnemies = new ArrayList<>();

        mPersonages = new HashMap<>();
        mPersonage = PersonageFactory.createPersonage(HeroClass.MAGE);
        mPersonageLocation = new Location(1, 1);
        mPersonages.put(mPersonage, mPersonageLocation);

        mEnemies = new ConcurrentHashMap<>();
        for (int enemyIndex = 0; enemyIndex < 80; enemyIndex++) {
            Enemy enemy = Enemy.createEnemy("Rat");
            mEnemies.put(enemy, new Location(
                    (int) Math.floor(Math.random() * (mGameMap.getSize() - 2) + 1),
                    (int) Math.floor(Math.random() * (mGameMap.getSize() - 2) + 1))
            );
        }
        mEnemyAttackComputationUseCase =
                new EnemyAttackComputationUseCase(mEnemies, new Pair<>(mPersonage, mPersonageLocation));
    }

    @Override
    public void attachView(GameFieldView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void saveInstance(Bundle state) {
        try {
            saveObject(mPersonage, "Hero.out");
            saveObject(mPersonageLocation, "HeroLocation.out");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restoreInstance(Bundle state) {
        try {
            FileInputStream heroFileInputStream = mView.getContext().openFileInput("Hero.out");
            ObjectInputStream objectInputStream = new ObjectInputStream(heroFileInputStream);
            mPersonage = (Personage) objectInputStream.readObject();
            objectInputStream.close();
            FileInputStream heroLocationFileInputStream = mView.getContext().openFileInput("HeroLocation.out");
            ObjectInputStream heroLocationInputStream = new ObjectInputStream(heroLocationFileInputStream);
            mPersonageLocation = (Location) heroLocationInputStream.readObject();
            heroLocationInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        mEnemyAttackComputationUseCase.setEnemies(mEnemies);
        mEnemyAttackComputationUseCase.setHero(new Pair<>(mPersonage, mPersonageLocation));
    }


    private void saveObject(Object object, String filename) throws IOException {
        FileOutputStream heroFileOutputStream = mView.getContext().openFileOutput(filename, Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(heroFileOutputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
    }


    @Override
    public int[][] getGameMap() {
        return mGameMap.getMap();
    }

    @Override
    public Personage getPersonage() {
        return mPersonage;
    }

    @Override
    public Location getPersonageLocation() {
        if (mPersonage.getHealth() < 1) {
            mPersonage.setHealth(100);
            mPersonageLocation.setX(1);
            mPersonageLocation.setY(1);
        }
        return mPersonageLocation;
    }

    @Override
    public synchronized void onPersonageMove(int x, int y) {
        Location personageLocation = getPersonageLocation();
        int newXCoordinate = personageLocation.getX() + x;
        int newYCoordinate = personageLocation.getY() + y;
        if (mGameMap.getSize() - 1 > newXCoordinate && mGameMap.getSize() - 1 > newYCoordinate && newXCoordinate > 0 && newYCoordinate > 0) {
            personageLocation.move(x, y);
        }
    }

    @Override
    public ArrayList<Enemy> findEnemiesAroundHero() {
        mEnemiesAroundHero.clear();
        ConcurrentHashMap<Enemy, Location> enemies = mEnemies;
        Location personageLocation = getPersonageLocation();
        for (Enemy enemy: enemies.keySet()) {
            Location enemyLocation = enemies.get(enemy);
            if (Math.abs(Objects.requireNonNull(enemyLocation).getX() - personageLocation.getX()) < 3
                    && Math.abs(Objects.requireNonNull(enemyLocation).getY() - personageLocation.getY()) < 3) {
                mEnemiesAroundHero.add(enemy);
            }
        }
        return mEnemiesAroundHero;
    }

    @Override
    public ArrayList<Enemy> findEnemiesAroundHero(int slotIndex) {
        mEnemiesAroundHero.clear();
        Slot slot = mPersonage.getSlots().get(slotIndex);

        if (!(slot instanceof DamageSlot)) {
            return mEnemiesAroundHero;
        }
        DamageSlot damageSlot = (DamageSlot) slot;
        Location personageLocation = getPersonageLocation();
        int distance = damageSlot.getDistance();
        for (Enemy enemy: mEnemies.keySet()) {
            @NonNull
            Location enemyLocation = Objects.requireNonNull(mEnemies.get(enemy));
            if (checkDistance(personageLocation, enemyLocation, distance)) {
                mEnemiesAroundHero.add(enemy);
            }
        }

        return mEnemiesAroundHero;
    }

    @Override
    public ArrayList<Enemy> getNearbyEnemies() {
        mNearbyEnemies.clear();
        for (Enemy enemy: mEnemies.keySet()) {
            Location enemyLocation = mEnemies.get(enemy);
            Location heroLocation = mPersonageLocation;

            if (enemyLocation.getX() == heroLocation.getX() && enemyLocation.getY() == heroLocation.getY()) {
                mNearbyEnemies.add(enemy);
            }
        }

        return mNearbyEnemies;
    }

    @Override
    public void attackEnemy(Enemy enemy, int slotIndex) {
        if (!checkAttackDistanceFromHeroToEnemy(enemy, slotIndex)) {
            mEnemiesAroundHero.remove(enemy);
            mView.removeEnemyTextView();
            return;
        }
        DamageComputationUseCase.compute(getPersonage(), enemy, slotIndex);
        if (enemy.getHealth() < 1) {
            mEnemies.remove(enemy);
            mView.removeEnemyTextView();
        }
    }

    @Override
    public boolean checkAttackDistanceFromHeroToEnemy(Enemy enemy, int slotIndex) {
        Slot slot = mPersonage.getSlots().get(slotIndex);
        if (!(slot instanceof DamageSlot)) {
            return false;
        }
        DamageSlot damageSlot = (DamageSlot) slot;
        int damageSlotDistance = damageSlot.getDistance();

        Location enemyLocation = mEnemies.get(enemy);
        if (enemyLocation == null) {
            return false;
        }

        return checkDistance(mPersonageLocation, enemyLocation, damageSlotDistance);
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
        return mEnemies.get(enemy);
    }
}
