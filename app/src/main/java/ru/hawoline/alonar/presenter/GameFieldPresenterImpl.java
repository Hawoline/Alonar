package ru.hawoline.alonar.presenter;

import android.os.Bundle;
import java.util.HashMap;
import ru.hawoline.alonar.model.map.LandscapeMap;
import ru.hawoline.alonar.model.map.Map;
import ru.hawoline.alonar.model.personage.Location;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.PersonageFactory;
import ru.hawoline.alonar.model.personage.heroclass.HeroClass;
import ru.hawoline.alonar.model.personage.inventory.Inventory;
import ru.hawoline.alonar.model.personage.item.Quality;
import ru.hawoline.alonar.model.personage.item.equipment.Body;
import ru.hawoline.alonar.model.personage.item.equipment.clothing.Clothing;
import ru.hawoline.alonar.util.Pair;
import ru.hawoline.alonar.view.GameFieldView;

public class GameFieldPresenterImpl implements GameFieldPresenter {
    private GameFieldView view;
    private Map gameMap;
    private Personage hero;
    private Location personageLocation;
    private HashMap<Personage, Location> personages;

    public GameFieldPresenterImpl() {
        gameMap = new LandscapeMap(30);

        personages = new HashMap<>();
        hero = PersonageFactory.createPersonage(HeroClass.MAGE);
        personageLocation = new Location(1, 1);
        personages.put(hero, personageLocation);
        hero.setInventory(new Inventory());
        for (int item = 0; item < 80; item++) {
            hero.getInventory().addItem(new Clothing("Helmet", 1, Quality.EXPENSIVE, new Pair<>(100, 100), Body.HEAD));
        }
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
    }
}
