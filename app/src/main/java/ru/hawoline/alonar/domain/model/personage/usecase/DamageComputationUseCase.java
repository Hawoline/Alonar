package ru.hawoline.alonar.domain.model.personage.usecase;

public final class DamageComputationUseCase {
    public int compute(int defenderHealth, int attackerDamage) {
        return defenderHealth - attackerDamage;
    }
}
