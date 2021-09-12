package ru.hawoline.alonar.model.personage.usecase;

import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.Slot;
import ru.hawoline.alonar.model.spell.DamageSpell;
import ru.hawoline.alonar.model.weapons.Weapon;

public final class DamageComputationUseCase {
    public static void compute(Personage attacker, Personage defender, int damageSlotIndex) {
        Slot slot = attacker.getSlots().get(damageSlotIndex);
        if (slot instanceof DamageSpell) {
            if (attacker.getMp() >= ((DamageSpell) slot).getRequiredMana()) {
                defender.setHealth(defender.getHealth() - ((DamageSpell) slot).getDamage());
                attacker.setMp(attacker.getMp() - ((DamageSpell) slot).getRequiredMana());
            }
        } else if (slot instanceof Weapon) {
            defender.setHealth(defender.getHealth() - ((Weapon) slot).getDamage());
        }
    }
}
