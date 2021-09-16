package ru.hawoline.alonar.model.personage;

import ru.hawoline.alonar.model.personage.equipment.Body;
import ru.hawoline.alonar.model.personage.equipment.Equipment;
import ru.hawoline.alonar.model.personage.specification.Vitality;
import ru.hawoline.alonar.model.personage.specification.VitalityType;
import ru.hawoline.alonar.model.personage.specification.attribute.Attribute;
import ru.hawoline.alonar.model.personage.specification.attribute.AttributeName;

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
        mVitality = new ArrayList<>();
        mVitality.add(new Vitality(VitalityType.HEATH, 10));
        mVitality.add(new Vitality(VitalityType.MP, 10));
        mExperience = 0;
        mEquipment = new HashMap<>();
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
