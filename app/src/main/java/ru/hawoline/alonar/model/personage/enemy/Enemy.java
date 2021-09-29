package ru.hawoline.alonar.model.personage.enemy;

import ru.hawoline.alonar.model.Range;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.Slot;
import ru.hawoline.alonar.model.personage.specification.attribute.Attribute;
import ru.hawoline.alonar.model.personage.specification.attribute.AttributeName;
import ru.hawoline.alonar.model.personage.weapon.Knife;

import java.util.ArrayList;

public class Enemy extends Personage {
    private String mName;
    private int mCooldown; // in seconds
    private long mTimeFromLastAttack;

    private static final long serialVersionUID = -7276415188406948963L;

    private Enemy(String name) {
//        super();
        mName = name;
        mCooldown = 4;
        setAttribute(AttributeName.ENDURANCE, new Attribute(100));
        ArrayList<Slot> slots = new ArrayList<>();
        slots.add(new Knife(0, new Range(3, 4), 4));
        setSlots(slots);
    }

    public static Enemy createEnemy(String name) {
        return new Enemy(name);
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getCooldown() {
        return mCooldown;
    }

    public void setCooldown(int cooldown) {
        mCooldown = cooldown;
    }

    public boolean canAttack() {
        boolean result = ((System.currentTimeMillis() - mTimeFromLastAttack) / 1000) > mCooldown;
        if (result) {
            mTimeFromLastAttack = System.currentTimeMillis();
        }
        return result;
    }
}
