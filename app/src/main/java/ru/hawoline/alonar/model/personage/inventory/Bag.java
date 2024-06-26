package ru.hawoline.alonar.model.personage.inventory;

import ru.hawoline.alonar.model.personage.item.Item;

import java.io.Serializable;
import java.util.ArrayList;

public class Bag implements Serializable {
    private ArrayList<Item> items;
    private int capacity;

    private static final long serialVersionUID = -3153572391865397165L;

    public Bag(ArrayList<Item> items, int capacity) {
        this.capacity = capacity;
        this.items = items;
    }

    public boolean addItem(Item item) {
        if (items.size() < getCapacity()) {
            items.add(item);
            return true;
        }

        return false;
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public Item getItem(int position) {
        return items.get(position);
    }
    public int getCapacity() {
        return capacity;
    }

    public int getItemCount() {
        return items.size();
    }
}
