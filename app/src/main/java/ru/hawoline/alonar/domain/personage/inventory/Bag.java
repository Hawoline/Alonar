package ru.hawoline.alonar.domain.personage.inventory;

import ru.hawoline.alonar.domain.personage.item.Item;

import java.io.Serializable;
import java.util.ArrayList;

public class Bag implements Serializable {
    private ArrayList<Item> mItems;
    private int mCapacity;

    private static final long serialVersionUID = -3153572391865397165L;

    public Bag(ArrayList<Item> items, int capacity) {
        mCapacity = capacity;
        mItems = items;
    }

    public boolean addItem(Item item) {
        if (mItems.size() < getCapacity()) {
            mItems.add(item);
            return true;
        }

        return false;
    }

    public boolean removeItem(Item item) {
        return mItems.remove(item);
    }

    public Item getItem(int position) {
        return mItems.get(position);
    }
    public int getCapacity() {
        return mCapacity;
    }

    public int getItemCount() {
        return mItems.size();
    }
}
