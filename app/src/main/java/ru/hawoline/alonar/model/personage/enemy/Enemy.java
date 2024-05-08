package ru.hawoline.alonar.model.personage.enemy;

import ru.hawoline.alonar.model.Range;
import ru.hawoline.alonar.model.personage.DamageSlot;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.item.equipment.Body;
import ru.hawoline.alonar.model.personage.item.Quality;
import ru.hawoline.alonar.model.personage.specification.attribute.PersonageAttribute;
import ru.hawoline.alonar.model.personage.specification.attribute.AttributeName;
import ru.hawoline.alonar.model.personage.item.equipment.weapon.Knife;
import ru.hawoline.alonar.util.Pair;

import java.util.ArrayList;

public class Enemy extends Personage {
    private String name;
    private int cooldown; // in seconds
    private long timeFromLastAttack;

    private static final long serialVersionUID = -7276415188406948963L;

    private Enemy(String name) {
        this.name = name;
        cooldown = 4;
        setAttribute(AttributeName.ENDURANCE, new PersonageAttribute(100));
        ArrayList<DamageSlot> slots = new ArrayList<>();
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 1,
                new Range(3, 4), 4, false));
        setSlots(slots);
    }

    public static Enemy createEnemy(String name) {
        return new Enemy(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public boolean canAttack() {
        long currentTime = System.currentTimeMillis();
        boolean result = ((currentTime - timeFromLastAttack) / 1000) > cooldown;
        return result;
    }

    public boolean attack() {
        long currentTime = System.currentTimeMillis();

        boolean result = canAttack();
        if (result) {
            timeFromLastAttack = currentTime;
        }

        return result;
    }
}
