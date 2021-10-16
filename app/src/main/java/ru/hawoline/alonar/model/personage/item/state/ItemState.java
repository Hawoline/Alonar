package ru.hawoline.alonar.model.personage.item.state;

import ru.hawoline.alonar.model.personage.inventory.Inventory;
import ru.hawoline.alonar.model.personage.item.Item;
import ru.hawoline.alonar.model.personage.item.equipment.Equipment;

/**
 * Состояния:
 * 1. Валяется на карте;
 * 2. В инвентаре;
 * 3. В трупе;
 *
 * Для экипировки еще:
 * 4. На персонаже;
 *
 * Для расходников еще:
 * 4. Использовать
 *
 * Переходы:
 * 1. (Валяется на дороге || в трупе(группа предметов)) <-> Инвентарь <-> Экипирован
 * 2. (Валяется на дороге || в трупе(группа предметов)) <-> Инвентарь -> Использован(исчез, для расходников)
 *
 * Действия с предметами:
 * 1. Положить в инвентарь;
 * 2. Выбросить n-ое количество предметов;
 * 3. Надеть(для снаряжения);
 * 4. Использовать(для зелий и других расходников);
 */
public abstract class ItemState {
    protected Item mItem;

    public ItemState(Item item) {
        mItem = item;
    }

    public abstract ItemStateName onAddToInventory(Inventory inventory);
    public abstract ItemStateName onThrowAway(Inventory inventory);
    public abstract ItemStateName onUse();
}
