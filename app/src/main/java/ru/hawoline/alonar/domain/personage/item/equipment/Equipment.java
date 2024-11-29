package ru.hawoline.alonar.domain.model.personage.item.equipment;

import androidx.annotation.NonNull;
import ru.hawoline.alonar.domain.model.personage.effect.Effect;
import ru.hawoline.alonar.domain.model.personage.item.Item;
import ru.hawoline.alonar.domain.model.personage.item.Quality;
import ru.hawoline.alonar.domain.model.personage.money.Gem;
import ru.hawoline.alonar.util.Pair;

import java.util.ArrayList;

public abstract class Equipment extends Item {
    protected Pair<Integer, Integer> strength;
    protected ArrayList<Gem> gems;
    protected ArrayList<Effect> effects;
    protected Body requiredBody;

    private static final long serialVersionUID = -1066374173398793188L;

    public Equipment(String name, int requiredLevel, Quality quality, Pair<Integer, Integer> strength, Body requiredBody) {
        super(name, requiredLevel, quality);
        this.strength = strength;
        this.requiredBody = requiredBody;
        gems = new ArrayList<>();
        effects = new ArrayList<>();
    }

    public Pair<Integer, Integer> getStrength() {
        return strength;
    }

    public void setStrength(Pair<Integer, Integer> strength) {
        this.strength = strength;
    }

    public ArrayList<Gem> getGems() {
        return gems;
    }

    public void setGems(ArrayList<Gem> gems) {
        this.gems = gems;
    }

    public void addGem(Gem gem) {
        gems.add(gem);
    }

    @NonNull
    public ArrayList<Effect> getEffects() {
        return effects;
    }

    public void setEffects(ArrayList<Effect> effects) {
        this.effects = effects;
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public Body getRequiredBody() {
        return requiredBody;
    }

    public void setRequiredBody(Body requiredBody) {
        this.requiredBody = requiredBody;
    }
}
