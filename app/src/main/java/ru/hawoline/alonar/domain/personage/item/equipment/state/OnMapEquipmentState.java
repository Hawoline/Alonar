package ru.hawoline.alonar.domain.model.personage.item.equipment.state;

import ru.hawoline.alonar.domain.model.personage.Personage;
import ru.hawoline.alonar.domain.model.personage.inventory.Inventory;
import ru.hawoline.alonar.domain.model.personage.item.equipment.Equipment;
import ru.hawoline.alonar.domain.model.personage.item.state.ItemStateName;

public class OnMapEquipmentState extends EquipmentState {
    private static final long serialVersionUID = 8291921646890135095L;

    public OnMapEquipmentState(Equipment equipment) {
        super(equipment);
    }

    @Override
    public ItemStateName onAddToInventory(Inventory inventory) {
        if (inventory.hasFreeSpace()) {
            getItem().setState(new InInventoryEquipmentState(getItem(), inventory));
            return ItemStateName.IN_INVENTORY;
        }
        return getItemStateName();
    }

    @Override
    public ItemStateName onThrowAway(Inventory inventory) {
        return getItemStateName();
    }

    @Override
    public ItemStateName onEquip(Personage personage) {
        return getItemStateName();
    }

    @Override
    public ItemStateName onUnequip(Personage personage) {
        return getItemStateName();
    }

    @Override
    public ItemStateName getItemStateName() {
        return ItemStateName.ON_MAP;
    }
}
