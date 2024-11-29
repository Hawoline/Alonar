package ru.hawoline.alonar.domain.model.personage.spell;

import ru.hawoline.alonar.domain.model.Range;

public class AreaDamageSpell extends DamageSpell {
    private int radius;

    public AreaDamageSpell(String name, int distance, int requiredMana, int restoreTime, Range damage, int radius) {
        super(name, distance, requiredMana, restoreTime, damage);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
