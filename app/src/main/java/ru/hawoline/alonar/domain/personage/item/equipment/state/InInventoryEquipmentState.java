package ru.hawoline.alonar.domain.personage.item.equipment.state;

import ru.hawoline.alonar.domain.personage.Personage;
import ru.hawoline.alonar.domain.personage.inventory.Inventory;
import ru.hawoline.alonar.domain.personage.item.equipment.Equipment;
import ru.hawoline.alonar.domain.personage.item.state.ItemStateName;

public class InInventoryEquipmentState extends EquipmentState {
    private static final long serialVersionUID = 6364178126197537879L;

    public InInventoryEquipmentState(Equipment equipment, Inventory inventory) {
        super(equipment);
        inventory.addItem(equipment);
    }

    @Override
    public ItemStateName onAddToInventory(Inventory inventory) {
        return getItemStateName();
    }

    @Override
    public ItemStateName onThrowAway(Inventory inventory) {
        if (inventory.removeItem(getItem())) {
            getItem().setState(new OnMapEquipmentState(getItem()));
            return ItemStateName.ON_MAP;
        }

        return getItemStateName();
    }

    @Override
    public ItemStateName onEquip(Personage personage) {
        if (personage.getInventory().removeItem(getItem())) {
            personage.equip(getItem().getRequiredBody(), getItem());
            getItem().setState(new OnBodyEquipmentState(getItem(), personage));
            return ItemStateName.ON_BODY;
        }

        return getItemStateName();
    }

    @Override
    public ItemStateName onUnequip(Personage personage) {
        return getItemStateName();
    }

    @Override
    public ItemStateName getItemStateName() {
        return ItemStateName.IN_INVENTORY;
    }
}
