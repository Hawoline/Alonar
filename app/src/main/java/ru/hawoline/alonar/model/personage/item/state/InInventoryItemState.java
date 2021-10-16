package ru.hawoline.alonar.model.personage.item.state;

import ru.hawoline.alonar.model.personage.inventory.Inventory;
import ru.hawoline.alonar.model.personage.item.Item;
import ru.hawoline.alonar.model.personage.item.equipment.Equipment;

public class InInventoryItemState extends ItemState {
    private Inventory mInventory;

    public InInventoryItemState(Item item, Inventory inventory) {
        super(item);
        mInventory = inventory;
        mInventory.addItem(item);
    }

    @Override
    public ItemStateName onAddToInventory(Inventory inventory) {
        return ItemStateName.IN_INVENTORY;
    }

    @Override
    public ItemStateName onThrowAway(Inventory inventory) {
        inventory.removeItem(mItem);
        mItem.setState(new OnGroundItemState(mItem));
        return ItemStateName.ON_GROUND;
    }

    @Override
    public ItemStateName onUse() {
        return ItemStateName.IN_INVENTORY;
    }
}
