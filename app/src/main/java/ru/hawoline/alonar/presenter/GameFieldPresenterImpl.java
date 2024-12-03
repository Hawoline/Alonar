package ru.hawoline.alonar.presenter;

import android.os.Bundle;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import ru.hawoline.alonar.data.entity.PlayerPositionEntity;
import ru.hawoline.alonar.data.repository.PlayerPositionFirebaseDataStore;
import ru.hawoline.alonar.data.repository.PlayerPositionRepository;
import ru.hawoline.alonar.domain.model.map.GameMap;
import ru.hawoline.alonar.domain.model.map.LandscapeGameMap;
import ru.hawoline.alonar.domain.model.personage.Location;
import ru.hawoline.alonar.domain.model.personage.Personage;
import ru.hawoline.alonar.domain.model.personage.PersonageFactory;
import ru.hawoline.alonar.domain.model.personage.heroclass.HeroClass;
import ru.hawoline.alonar.domain.model.personage.inventory.Inventory;
import ru.hawoline.alonar.domain.model.personage.item.Quality;
import ru.hawoline.alonar.domain.model.personage.item.equipment.Body;
import ru.hawoline.alonar.domain.model.personage.item.equipment.clothing.Clothing;
import ru.hawoline.alonar.util.Pair;
import ru.hawoline.alonar.view.GameFieldView;

public class GameFieldPresenterImpl implements GameFieldPresenter {
    private GameFieldView view;
    private GameMap gameMap;
    private Personage hero;
    private Location personageLocation;
    private HashMap<Personage, Location> personages;
    private PlayerPositionRepository playerPositionRepository;
    private int playerId;
    private HashMap<Integer, Location> anotherPersonagesLocation = new HashMap<>();

    public GameFieldPresenterImpl() {
        gameMap = new LandscapeGameMap(30);

        personages = new HashMap<>();
        hero = PersonageFactory.createPersonage(HeroClass.MAGE);
        personageLocation = new Location(1, 1);
        personages.put(hero, personageLocation);
        hero.setInventory(new Inventory());
        for (int item = 0; item < 80; item++) {
            hero.getInventory().addItem(new Clothing("Helmet", 1, Quality.EXPENSIVE, new Pair<>(100, 100), Body.HEAD));
        }
        playerId = new Random().nextInt(20);
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                String key = dataSnapshot.getKey();
                if (key == null || key.equals(String.valueOf(playerId))) {
                    return;
                }
                PlayerPositionEntity playerPosition = dataSnapshot.getValue(PlayerPositionEntity.class);
                System.out.println("x and y: " + playerPosition.getX() + " " + playerPosition.getY());
                System.out.println("Key: " + key);
                int xPositionRelativeHero = playerPosition.getX() - personageLocation.getX() + 2;
                int yPositionRelativeHero = playerPosition.getY() - personageLocation.getY() + 2;
                anotherPersonagesLocation.put(Integer.parseInt(key), new Location(playerPosition.getX(), playerPosition.getY()));
                if (xPositionRelativeHero > 4 || yPositionRelativeHero > 4) {
                    return;
                }
                view.drawAnotherPersonage(xPositionRelativeHero, yPositionRelativeHero);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                String key = dataSnapshot.getKey();
                if (key == null) {
                    return;
                }
                PlayerPositionEntity playerPosition = dataSnapshot.getValue(PlayerPositionEntity.class);

                System.out.println("x and y: " + playerPosition.getX() + " " + playerPosition.getY());
                System.out.println("Key: " + key);
                if (!key.equals(String.valueOf(playerId))) {
                    anotherPersonagesLocation.put(Integer.parseInt(key), new Location(playerPosition.getX(), playerPosition.getY()));
                }
                for (Map.Entry<Integer, Location> set :
                    anotherPersonagesLocation.entrySet()) {
                    Location location = set.getValue();
                    int xPositionOnVisibleMap = location.getX() - personageLocation.getX() + 2;
                    int yPositionOnVisibleMap = location.getY() - personageLocation.getY() + 2;
                    if (xPositionOnVisibleMap > 4 || xPositionOnVisibleMap < 0 ||
                        yPositionOnVisibleMap > 4 || yPositionOnVisibleMap < 0) {
                        continue;
                    }
                    view.drawAnotherPersonage(xPositionOnVisibleMap, yPositionOnVisibleMap);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        playerPositionRepository = new PlayerPositionRepository(new PlayerPositionFirebaseDataStore(childEventListener));
        playerPositionRepository.save(playerId, new PlayerPositionEntity(personageLocation.getX(), personageLocation.getY()));
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

    }

    @Override
    public void restoreInstance(Bundle state) {

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
        playerPositionRepository.save(playerId, new PlayerPositionEntity(personageLocation.getX(), personageLocation.getY()));
    }
}
