package ru.hawoline.alonar.domain.model.personage.inventory;

import ru.hawoline.alonar.domain.model.personage.item.Item;

import java.util.ArrayList;

public class Bag {
    private ArrayList<Item> items;
    private int capacity;

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
