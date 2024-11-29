package ru.hawoline.alonar.domain.model.personage.item.equipment.state;

import ru.hawoline.alonar.domain.model.personage.Personage;
import ru.hawoline.alonar.domain.model.personage.inventory.Inventory;
import ru.hawoline.alonar.domain.model.personage.item.equipment.Equipment;
import ru.hawoline.alonar.domain.model.personage.item.state.ItemStateName;

public class OnBodyEquipmentState extends EquipmentState {
    private static final long serialVersionUID = -6456743503099049589L;

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
    public ItemStateName onEquip(Personage personage) {
        return getItemStateName();
    }

    @Override
    public ItemStateName onUnequip(Personage personage) {
        if (personage.getInventory().hasFreeSpace()) {
            personage.unequip(getItem().getRequiredBody());
            getItem().setState(new InInventoryEquipmentState(getItem(), personage.getInventory()));
            return ItemStateName.IN_INVENTORY;
        }

        return getItemStateName();
    }

    @Override
    public ItemStateName getItemStateName() {
        return ItemStateName.ON_BODY;
    }
}
