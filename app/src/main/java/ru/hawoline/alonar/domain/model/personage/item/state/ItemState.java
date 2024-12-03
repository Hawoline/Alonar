package ru.hawoline.alonar.domain.model.personage.item.state;

import ru.hawoline.alonar.domain.model.personage.inventory.Inventory;
import ru.hawoline.alonar.domain.model.personage.item.Item;
import ru.hawoline.alonar.domain.model.personage.Personage;

/**
 * Состояния:
 * 1. На карте;
 * 2. В инвентаре;
 *
 * Для экипировки еще:
 * 3. На персонаже;
 *
 * Для расходников еще:
 * 4. Использован;
 *
 * Переходы:
 * 1. (Валяется на дороге || в трупе(группа предметов)) <-> Инвентарь <-> Экипирован
 * 2. (Валяется на дороге || в трупе(группа предметов)) <-> Инвентарь -> Использован(исчез, для расходников)
 *
 * Действия с предметами:
 * 1. Положить в инвентарь;
 * 2. Выбросить n-ое количество предметов;
 * 3. Надеть(для снаряжения);
 * 4. Снять снаряжение в инвентарь(для снаряжения);
 * 5. Использовать(для зелий и других расходников);
 */
public abstract class ItemState {
    private Item item;

    public ItemState(Item item) {
        this.item = item;
    }

    public abstract ItemStateName onAddToInventory(Inventory inventory);
    public abstract ItemStateName onThrowAway(Inventory inventory);
    public ItemStateName onEquip(Personage personage) {
        return getItemStateName();
    }
    public ItemStateName onUnequip(Personage personage) {
        return getItemStateName();
    }

    public abstract ItemStateName getItemStateName();
    public Item getItem() {
        return item;
    }
}
