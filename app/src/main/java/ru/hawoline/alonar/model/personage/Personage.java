package ru.hawoline.alonar.model.personage;

import ru.hawoline.alonar.model.Range;
import ru.hawoline.alonar.model.personage.equipment.Body;
import ru.hawoline.alonar.model.personage.equipment.Equipment;
import ru.hawoline.alonar.model.personage.specification.Vitality;
import ru.hawoline.alonar.model.personage.specification.VitalityType;
import ru.hawoline.alonar.model.personage.specification.attribute.Attribute;
import ru.hawoline.alonar.model.personage.specification.attribute.AttributeName;
import ru.hawoline.alonar.model.spell.DamageSpell;
import ru.hawoline.alonar.model.weapons.Knife;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Personage {
    private int mExperience;
    private int mArmor;
    private ArrayList<Vitality> mVitality;
    private HashMap<AttributeName, Attribute> mAttributes;
    private HashMap<Body, Equipment> mEquipment;
    private ArrayList<Slot> mSlots;

    protected Personage() {
        mAttributes = new HashMap<>();
        mAttributes.put(AttributeName.ENDURANCE, new Attribute(5));
        mAttributes.put(AttributeName.STRENGTH, new Attribute(5));
        mAttributes.put(AttributeName.INTELLIGENCE, new Attribute(100));
        mAttributes.put(AttributeName.AGILITY, new Attribute(5));
        mAttributes.put(AttributeName.SPIRIT, new Attribute(5));
        mVitality = new ArrayList<>();
        mVitality.add(new Vitality(VitalityType.HEATH, mAttributes.get(AttributeName.ENDURANCE).getValue() * 10));
        mVitality.add(new Vitality(VitalityType.MP, mAttributes.get(AttributeName.INTELLIGENCE).getValue() * 16));
        mExperience = 0;
        mArmor = 100;
        mEquipment = new HashMap<>();
        mSlots = new ArrayList<>();
        mSlots.add(new Knife(0, new Range(3, 4), 2));
        mSlots.add(new DamageSpell("Fireball", 6, 50, 6, new Range(197, 236)));
        mSlots.add(new Knife(0, new Range(3, 4), 2));
        mSlots.add(new Knife(0, new Range(3, 4), 2));
        mSlots.add(new Knife(0, new Range(3, 4), 2));
        mSlots.add(new Knife(0, new Range(3, 4), 2));
        mSlots.add(new Knife(0, new Range(3, 4), 2));
        mSlots.add(new Knife(0, new Range(3, 4), 2));
        mSlots.add(new Knife(0, new Range(3, 4), 2));
        mSlots.add(new Knife(0, new Range(3, 4), 2));
    }

    public int getExperience() {
        return mExperience;
    }

    public void setExperience(int experience) {
        mExperience = experience;
    }

    public int getArmor() {
        return mArmor;
    }

    public void setArmor(int armor) {
        mArmor = armor;
    }

    public int getHealth() {
        return mVitality.get(0).getResidualMax().getSecond();
    }
    public void setHealth(int heath) {
        mVitality.get(0).setValue(heath);
    }

    public int getMp() {
        return mVitality.get(1).getResidualMax().getSecond();
    }
    public void setMp(int mp) {
        mVitality.get(1).setValue(mp);
    }

    public void setAttribute(AttributeName attributeName, Attribute attributeValue) {
        mAttributes.put(attributeName, attributeValue);
        if (attributeName == AttributeName.ENDURANCE) {
            mVitality.get(0).setMaxValue(attributeValue.getMax() * 10);
        } else if (attributeName == AttributeName.INTELLIGENCE) {
            mVitality.get(1).setMaxValue(attributeValue.getMax() * 16);
        }
    }

    public Attribute getAttribute(AttributeName attributeName) {
        return mAttributes.get(attributeName);
    }

    public ArrayList<Slot> getSlots() {
        return mSlots;
    }

    public void setSlots(ArrayList<Slot> slots) {
        mSlots = slots;
    }
}
