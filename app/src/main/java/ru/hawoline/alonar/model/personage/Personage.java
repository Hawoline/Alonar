package ru.hawoline.alonar.model.personage;

import ru.hawoline.alonar.model.Range;
import ru.hawoline.alonar.model.weapons.Knife;

import java.util.ArrayList;

public abstract class Personage {
    private int mHealth;
    private int mMaxHealth;
    private int mMp;
    private int mMaxMp;
    private int mX;
    private int mY;
    private int mDamage;
    private int mExperience;
    private int mEndurance;
    private int mStrength;
    private int mIntelligence;
    private int mAgility;
    private int mSpirit;
    private int mArmor;
    private int mDirection;
    private ArrayList<Slot> mSlots;

    public static final int DIRECTION_FORWARD = 0;
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_BACK = 2;
    public static final int DIRECTION_LEFT = 3;

    protected Personage() {
        mDamage = 100;
        mExperience = 0;
        mEndurance = 5;
        mStrength = 5;
        mIntelligence = 10;
        mAgility = 5;
        mSpirit = 5;
        mArmor = 100;
        mMaxHealth = mEndurance * 10;
        mHealth = mMaxHealth;
        mMaxMp = mIntelligence * 16;
        mDirection = DIRECTION_RIGHT;
        mX = 0;
        mY = 0;
        mSlots = new ArrayList<>();
        mSlots.add(new Knife(0, new Range(3, 4), 2));
    }

    public Personage attack(Personage personage) {
        personage.setHealth(personage.getHealth() - this.getDamage());
        return personage;
    }

    public void move(int xStep, int yStep) {
        if (mY + yStep >= 0) {
            if (yStep > 0) {
                mDirection = DIRECTION_BACK;
            } else if (yStep < 0) {
                mDirection = DIRECTION_FORWARD;
            }

            if (Math.abs(yStep) < 3) {
                mY += yStep;
            }
        }
        if (mX + xStep >= 0) {
            if (xStep > Math.abs(yStep)) {
                mDirection = DIRECTION_RIGHT;
            } else if (xStep < 0 && Math.abs(xStep) > Math.abs(yStep)) {
                mDirection = DIRECTION_LEFT;
            }

            if (Math.abs(xStep) < 3) {
                mX += xStep;
            }
        }
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

    public int getX() {
        return mX;
    }

    public void setX(int x) {
        mX = x;
    }

    public int getY() {
        return mY;
    }

    public void setY(int y) {
        mY = y;
    }

    public int getDamage() {
        return mDamage;
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
        mMaxHealth = mEndurance * 10;
    }

    public int getStrength() {
        return mStrength;
    }

    public void setStrength(int strength) {
        mStrength = strength;
        mDamage = strength * 5;
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

    public int getDirection() {
        return mDirection;
    }
}
