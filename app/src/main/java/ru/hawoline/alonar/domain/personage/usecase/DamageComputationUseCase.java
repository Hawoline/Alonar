package ru.hawoline.alonar.domain.model.personage.usecase;

import ru.hawoline.alonar.domain.model.gamelog.GameLog;
import ru.hawoline.alonar.domain.model.personage.DamageSlot;
import ru.hawoline.alonar.domain.model.personage.Personage;
import ru.hawoline.alonar.domain.model.personage.enemy.Enemy;

public final class DamageComputationUseCase {
    public static void compute(Personage attacker, Personage defender, int damageSlotIndex) {
        DamageSlot damageSlot = attacker.getDamageSlots().get(damageSlotIndex);
        if (attacker.getMp() >= damageSlot.getRequiredMana()) {
            int damage = damageSlot.getDamage();
            defender.setHealth(defender.getHealth() - damage);
            attacker.setMp(attacker.getMp() - damageSlot.getRequiredMana());
            String battleLogText;
            if (attacker instanceof Enemy) {
                battleLogText = ((Enemy) attacker).getName() + " attacks the hero " +
                    damage + " damage";
            } else {
                battleLogText = "Hero attacks the enemy " + damage +
                    " damage";
            }
            GameLog.getInstance().putAction(battleLogText);
        }
    }
}
