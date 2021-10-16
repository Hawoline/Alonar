package ru.hawoline.alonar.model.personage.inventory;

import ru.hawoline.alonar.model.personage.item.Item;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Bag> mBags;

    public Inventory() {
        mBags = new ArrayList<>();
    }

    public ArrayList<Bag> getBags() {
        return mBags;
    }

    public void setBags(ArrayList<Bag> bags) {
        mBags = bags;
    }

    public void addItem(Item item) {
        for (Bag bag: getBags()) {
            bag.removeItem(item);
        }
    }
    public void removeItem(Item item) {
        for (Bag bag: getBags()) {
            if (bag.removeItem(item)) {
                break;
            }
        }
    }
}
