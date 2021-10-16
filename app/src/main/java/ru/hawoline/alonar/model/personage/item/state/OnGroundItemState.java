package ru.hawoline.alonar.model.personage.item.state;

import ru.hawoline.alonar.model.personage.inventory.Inventory;
import ru.hawoline.alonar.model.personage.item.Item;

public class OnGroundItemState extends ItemState {
    public OnGroundItemState(Item item) {
        super(item);
    }

    @Override
    public ItemStateName onAddToInventory(Inventory inventory) {
        mItem.setState(new InInventoryItemState(mItem, inventory));
        return ItemStateName.IN_INVENTORY;
    }

    @Override
    public ItemStateName onThrowAway(Inventory inventory) {
        return ItemStateName.ON_GROUND;
    }

    @Override
    public ItemStateName onUse() {
        return ItemStateName.ON_GROUND;
    }
}
