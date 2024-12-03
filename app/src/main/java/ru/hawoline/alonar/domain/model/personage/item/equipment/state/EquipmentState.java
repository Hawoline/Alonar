package ru.hawoline.alonar.domain.model.personage.item.equipment.state;

import ru.hawoline.alonar.domain.model.personage.item.equipment.Equipment;
import ru.hawoline.alonar.domain.model.personage.item.state.ItemState;
import ru.hawoline.alonar.domain.model.personage.item.state.ItemStateName;
import ru.hawoline.alonar.domain.model.personage.Personage;

public abstract class EquipmentState extends ItemState {
    private Equipment equipment;

    public EquipmentState(Equipment equipment) {
        super(equipment);
        this.equipment =  equipment;
    }

    @Override
    public abstract ItemStateName onEquip(Personage personage);

    @Override
    public abstract ItemStateName onUnequip(Personage personage);

    @Override
    public Equipment getItem() {
        return equipment;
    }
}
