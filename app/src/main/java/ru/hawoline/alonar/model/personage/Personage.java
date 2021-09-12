package ru.hawoline.alonar.model.personage;

import ru.hawoline.alonar.model.Range;
import ru.hawoline.alonar.model.spell.DamageSpell;
import ru.hawoline.alonar.model.weapons.Knife;

import java.util.ArrayList;

public abstract class Personage {
    private int mHealth;
    private int mMaxHealth;
    private int mMp;
    private int mMaxMp;
    private int mExperience;
    private int mEndurance;
    private int mStrength;
    private int mIntelligence;
    private int mAgility;
    private int mSpirit;
    private int mArmor;
    private ArrayList<Slot> mSlots;

    protected Personage() {
        mExperience = 0;
        mEndurance = 5;
        mStrength = 5;
        mIntelligence = 100;
        mAgility = 5;
        mSpirit = 5;
        mArmor = 100;
        mMaxHealth = mEndurance * 10;
        mHealth = mMaxHealth;
        mMaxMp = mIntelligence * 16;
        mMp = mMaxMp;
        mSlots = new ArrayList<>();
        mSlots.add(new Knife(0, new Range(3, 4), 2));
        mSlots.add(new DamageSpell("Fireball", 6, 50, 6, new Range(20, 100)));
        mSlots.add(new Knife(0, new Range(3, 4), 2));
        mSlots.add(new Knife(0, new Range(3, 4), 2));
        mSlots.add(new Knife(0, new Range(3, 4), 2));
        mSlots.add(new Knife(0, new Range(3, 4), 2));
        mSlots.add(new Knife(0, new Range(3, 4), 2));
        mSlots.add(new Knife(0, new Range(3, 4), 2));
        mSlots.add(new Knife(0, new Range(3, 4), 2));
        mSlots.add(new Knife(0, new Range(3, 4), 2));
    }

    public int getHealth() {
        return mHealth;
    }

    public void setHealth(int health) {
        mHealth = health;
    }

    public int getMp() {
        return mMp;
    }

    public void setMp(int mp) {
        mMp = mp;
    }


    public int getExperience() {
        return mExperience;
    }

    public void setExperience(int experience) {
        mExperience = experience;
    }

    public int getEndurance() {
        return mEndurance;
    }

    public void setEndurance(int endurance) {
        mEndurance = endurance;
        int oldMaxHeath = mMaxHealth;
        mMaxHealth = mEndurance * 10;
        if (mHealth == oldMaxHeath) {
            setHealth(mMaxHealth);
        }
    }

    public int getStrength() {
        return mStrength;
    }

    public void setStrength(int strength) {
        mStrength = strength;
    }

    public int getIntelligence() {
        return mIntelligence;
    }

    public void setIntelligence(int intelligence) {
        mIntelligence = intelligence;
        mMaxMp = mIntelligence * 16;
    }

    public int getAgility() {
        return mAgility;
    }

    public void setAgility(int agility) {
        mAgility = agility;
    }

    public int getSpirit() {
        return mSpirit;
    }

    public void setSpirit(int spirit) {
        mSpirit = spirit;
    }

    public int getArmor() {
        return mArmor;
    }

    public void setArmor(int armor) {
        mArmor = armor;
    }

    public int getMaxHealth() {
        return mMaxHealth;
    }

    public int getMaxMp() {
        return mMaxMp;
    }

    public ArrayList<Slot> getSlots() {
        return mSlots;
    }

    public void setSlots(ArrayList<Slot> slots) {
        mSlots = slots;
    }
}
