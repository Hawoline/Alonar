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
    private ArrayList<Vitality> mVitality;
    private HashMap<AttributeName, Attribute> mAttributes;
    private HashMap<Body, Equipment> mEquipment;
    private ArrayList<Slot> mSlots;
    private int mExperience;
    private int mArmor; // In percent. Max is 80%

    private final int HEALTH = 0;
    private final int MP = 1;

    protected Personage() {
        mAttributes = new HashMap<>();
        mVitality = new ArrayList<>();
        mVitality.add(new Vitality(VitalityType.HEATH, 10));
        mVitality.add(new Vitality(VitalityType.MP, 10));
        mEquipment = new HashMap<>();
        mExperience = 0;
        mArmor = 10;
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
        return mVitality.get(HEALTH).getResidualMax().getSecond();
    }
    public void setHealth(int heath) {
        mVitality.get(HEALTH).setValue(heath);
    }

    public int getMp() {
        return mVitality.get(MP).getResidualMax().getSecond();
    }
    public void setMp(int mp) {
        mVitality.get(MP).setValue(mp);
    }

    public void setAttribute(AttributeName attributeName, Attribute attributeValue) {
        mAttributes.put(attributeName, attributeValue);
        if (attributeName == AttributeName.ENDURANCE) {
            mVitality.get(HEALTH).setMaxValue(attributeValue.getMax() * 10);
        } else if (attributeName == AttributeName.INTELLIGENCE) {
            mVitality.get(MP).setMaxValue(attributeValue.getMax() * 16);
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
