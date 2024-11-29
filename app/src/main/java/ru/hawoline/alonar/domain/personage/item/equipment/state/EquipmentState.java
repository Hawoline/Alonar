package ru.hawoline.alonar.domain.model.personage.item.equipment.state;

import ru.hawoline.alonar.domain.model.personage.Personage;
import ru.hawoline.alonar.domain.model.personage.item.equipment.Equipment;
import ru.hawoline.alonar.domain.model.personage.item.state.ItemState;
import ru.hawoline.alonar.domain.model.personage.item.state.ItemStateName;

public abstract class EquipmentState extends ItemState {
    private Equipment equipment;

    private static final long serialVersionUID = -4548388089537561490L;

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
