package ru.hawoline.alonar.model.personage.inventory;

import ru.hawoline.alonar.model.personage.item.Item;

import java.io.Serializable;
import java.util.ArrayList;

public class Inventory {
    private ArrayList<Bag> bags;

    public Inventory() {
        bags = new ArrayList<>();
        bags.add(new Bag(new ArrayList<>(), 10));
        bags.add(new Bag(new ArrayList<>(), 10));
        bags.add(new Bag(new ArrayList<>(), 10));
        bags.add(new Bag(new ArrayList<>(), 10));
        bags.add(new Bag(new ArrayList<>(), 10));
        bags.add(new Bag(new ArrayList<>(), 10));
        bags.add(new Bag(new ArrayList<>(), 10));
    }

    public void addItem(Item item) {
        for (Bag bag: getBags()) {
            if (bag.addItem(item)) {
                return;
            }
        }
    }

    public boolean removeItem(Item item) {
        for (Bag bag: getBags()) {
            if (bag.removeItem(item)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasFreeSpace() {
        for (Bag bag: getBags()) {
            if (bag.getCapacity() > bag.getItemCount()) {
                return true;
            }
        }

        return false;
    }

    public int getItemCount() {
        int sum = 0;
        for (Bag bag: getBags()) {
            sum += bag.getItemCount();
        }

        return sum;
    }

    public int getCapacity() {
        int sum = 0;
        for (Bag bag: getBags()) {
            sum += bag.getCapacity();
        }

        return sum;
    }

    public ArrayList<Bag> getBags() {
        return bags;
    }

    public void setBags(ArrayList<Bag> bags) {
        this.bags = bags;
    }
}
