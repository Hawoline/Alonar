package ru.hawoline.alonar.model.personage.item.state;

import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.inventory.Inventory;
import ru.hawoline.alonar.model.personage.item.equipment.Equipment;

public class OnBodyEquipmentState extends EquipmentState {

    public OnBodyEquipmentState(Equipment equipment, Personage personage) {
        super(equipment);
        personage.equip(equipment.getRequiredBody(), equipment);
    }

    // TODO Додумать снятие предмета с тела
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
    public ItemStateName onEquip(Inventory inventory, Personage personage) {
        return getItemStateName();
    }

    @Override
    public ItemStateName onUnequip(Inventory inventory, Personage personage) {
        if (inventory.hasFreeSpace()) {
            inventory.addItem(getItem());
            personage.unequip(getItem().getRequiredBody());
            getItem().setState(new InInventoryEquipmentState(getItem(), inventory));
            return ItemStateName.IN_INVENTORY;
        }

        return getItemStateName();
    }

    @Override
    public ItemStateName getItemStateName() {
        return ItemStateName.ON_BODY;
    }
}
