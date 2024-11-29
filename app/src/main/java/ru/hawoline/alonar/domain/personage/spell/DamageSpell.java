package ru.hawoline.alonar.domain.model.personage.spell;

import ru.hawoline.alonar.domain.model.Range;
import ru.hawoline.alonar.domain.model.personage.DamageSlot;

public class DamageSpell extends Spell implements DamageSlot {
    private Range range;

    private static final long serialVersionUID = -7669810234865246632L;

    public DamageSpell(String name, int distance, int requiredMana, int restoreTime, Range range) {
        super(name, distance, requiredMana, restoreTime);
        this.range = range;
        calculateDamage();
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public int calculateDamage() {
        return (int) (Math.random() * (range.getDistance()) + range.getLow());
    }

    @Override
    public int getDamage() {
        return calculateDamage();
    }
}
