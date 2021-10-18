package ru.hawoline.alonar.model.personage.item.state;

import ru.hawoline.alonar.model.personage.inventory.Inventory;
import ru.hawoline.alonar.model.personage.item.Item;

public class OnMapItemState extends ItemState {
    public OnMapItemState(Item item) {
        super(item);
    }

    @Override
    public ItemStateName onAddToInventory(Inventory inventory) {
        mItem.setState(new InInventoryItemState(mItem, inventory));
        return ItemStateName.IN_INVENTORY;
    }

    @Override
    public ItemStateName onThrowAway(Inventory inventory) {
        return ItemStateName.ON_MAP;
    }

    @Override
    public ItemStateName getItemStateName() {
        return ItemStateName.ON_MAP;
    }
}
