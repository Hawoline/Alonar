package ru.hawoline.alonar.model.personage.item.state;

import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.inventory.Inventory;
import ru.hawoline.alonar.model.personage.item.equipment.Equipment;

public abstract class EquipmentState extends ItemState {
    private Equipment mEquipment;

    public EquipmentState(Equipment equipment) {
        super(equipment);
        mEquipment =  equipment;
    }

    @Override
    public abstract ItemStateName onEquip(Inventory inventory, Personage personage);

    @Override
    public abstract ItemStateName onUnequip(Inventory inventory, Personage personage);

    @Override
    public Equipment getItem() {
        return mEquipment;
    }
}
