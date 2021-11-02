package ru.hawoline.alonar.model.personage.usecase;

import ru.hawoline.alonar.model.gamelog.GameLog;
import ru.hawoline.alonar.model.personage.DamageSlot;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.Slot;
import ru.hawoline.alonar.model.personage.enemy.Enemy;
import ru.hawoline.alonar.model.personage.spell.DamageSpell;
import ru.hawoline.alonar.model.personage.item.equipment.weapon.Weapon;

public final class DamageComputationUseCase {
    public static void compute(Personage attacker, Personage defender, int damageSlotIndex) {
        Slot slot = attacker.getSlots().get(damageSlotIndex);

        if (slot instanceof DamageSlot) {
            DamageSlot damageSlot = (DamageSlot) slot;
            if (attacker.getMp() >= damageSlot.getRequiredMana()) {
                int damage = damageSlot.getDamage();
                defender.setHealth(defender.getHealth() - damage);
                attacker.setMp(attacker.getMp() - damageSlot.getRequiredMana());
                String logText;
                if (attacker instanceof Enemy) {
                    logText = ((Enemy) attacker).getName() + " attacks the hero " +
                            damage + " magic damage";
                } else {
                    logText = "Hero attacks the enemy " + damage +
                            " magic damage";
                }
                GameLog.getInstance().putToLog(logText);
            }
        }
    }
}
