package ru.hawoline.alonar.domain.personage;

import androidx.annotation.NonNull;
import ru.hawoline.alonar.domain.Range;
import ru.hawoline.alonar.domain.personage.effect.Effect;
import ru.hawoline.alonar.domain.personage.heroclass.*;
import ru.hawoline.alonar.domain.personage.inventory.Inventory;
import ru.hawoline.alonar.domain.personage.item.Item;
import ru.hawoline.alonar.domain.personage.item.Quality;
import ru.hawoline.alonar.domain.personage.item.equipment.Body;
import ru.hawoline.alonar.domain.personage.item.equipment.Equipment;
import ru.hawoline.alonar.domain.personage.item.equipment.weapon.Knife;
import ru.hawoline.alonar.domain.personage.specification.Vitality;
import ru.hawoline.alonar.domain.personage.specification.VitalityType;
import ru.hawoline.alonar.domain.personage.specification.attribute.AttributeName;
import ru.hawoline.alonar.domain.personage.specification.attribute.PersonageAttribute;
import ru.hawoline.alonar.domain.personage.spell.DamageSpell;
import ru.hawoline.alonar.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Personage implements Serializable {
    private ArrayList<Vitality> mVitality;
    private HashMap<AttributeName, PersonageAttribute> mAttributes;
    private HashMap<Body, Equipment> mEquipment;
    private Inventory mInventory;
    private ArrayList<Slot> mSlots;
    private int mExperience;
    private int mArmor; // In percent. Max is 80%

    private static final long serialVersionUID = -1613269264133657958L;

    protected Personage() {
        mVitality = new ArrayList<>();
        mVitality.add(new Vitality(VitalityType.HEATH, 10));
        mVitality.add(new Vitality(VitalityType.MP, 10));
        mAttributes = new HashMap<>();
        mEquipment = new HashMap<>();
        mInventory = new Inventory();
        mExperience = 0;
        mArmor = 10;
    }

    public static Personage createPersonage(HeroClass heroClass) {
        Personage personage;

        switch(heroClass) {
            case MAGE:
                personage = new Mage();
                break;
            case PALADIN:
                personage = new Paladin();
                break;
            case PRIEST:
                personage = new Priest();
                break;
            case RANGER:
                personage = new Ranger();
                break;
            case ROBBER:
                personage = new Robber();
                break;
            default:
                personage = new Warrior();
                break;
        }

        personage.setAttribute(AttributeName.ENDURANCE, new PersonageAttribute(10));
        personage.setAttribute(AttributeName.STRENGTH, new PersonageAttribute(5));
        personage.setAttribute(AttributeName.INTELLIGENCE, new PersonageAttribute(100));
        personage.setAttribute(AttributeName.AGILITY, new PersonageAttribute(5));
        personage.setAttribute(AttributeName.SPIRIT, new PersonageAttribute(5));

        Knife knife = new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 0, new Range(6, 8), 4,false);
        personage.equip(Body.ARMS, knife);
        personage.setArmor(10);
        ArrayList<Slot> slots = new ArrayList<>();
        slots.add(knife);
        slots.add(new DamageSpell("Fireball", 6, 50, 6, new Range(201, 239)));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 0, new Range(6, 8), 4,false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 0, new Range(6, 8), 4,false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 0, new Range(6, 8), 4, false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 0, new Range(6, 8), 4, false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 0, new Range(6, 8), 4, false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 0, new Range(6, 8), 4, false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 0, new Range(6, 8), 4, false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 0, new Range(6, 8), 4, false));
        personage.setSlots(slots);

        return personage;
    }

    private void addEquipmentEffectBonuses() {
        for (Body body: mEquipment.keySet()) {
            for (Effect effect: mEquipment.get(body).getEffects()) {
                mAttributes.get(effect.getAttributeName()).increase(effect.getValue());
            }
        }
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
        return mVitality.get(Vitality.HP).getResidualMax().getSecond();
    }
    public void setHealth(int heath) {
        mVitality.get(Vitality.HP).setValue(heath);
    }

    public int getMp() {
        return mVitality.get(Vitality.MP).getResidualMax().getSecond();
    }
    public void setMp(int mp) {
        if (mp > -1) {
            mVitality.get(Vitality.MP).setValue(mp);
        }
    }

    public void setAttribute(AttributeName attributeName, PersonageAttribute personageAttributeValue) {
        mAttributes.put(attributeName, personageAttributeValue);
        if (attributeName == AttributeName.ENDURANCE) {
            mVitality.get(Vitality.HP).setMaxValue(personageAttributeValue.getMax() * 10);
        } else if (attributeName == AttributeName.INTELLIGENCE) {
            mVitality.get(Vitality.MP).setMaxValue(personageAttributeValue.getMax() * 16);
        }
    }

    public PersonageAttribute getAttribute(AttributeName attributeName) {
        return mAttributes.get(attributeName);
    }

    public HashMap<Body, Equipment> getEquipment() {
        return mEquipment;
    }

    public void setEquipment(HashMap<Body, Equipment> equipment) {
        mEquipment = equipment;
    }

    public Inventory getInventory() {
        return mInventory;
    }

    public void setInventory(Inventory inventory) {
        mInventory = inventory;
    }

    public void equip(Body body, @NonNull Equipment equipment) {
        unequip(body);
        if (equipment.getRequiredBody() == body) {
            mEquipment.put(body, equipment);
            for (Effect effect: equipment.getEffects()) {
                mAttributes.get(effect.getAttributeName()).increase(effect.getValue());
            }
        }
    }

    public void unequip(Body body) {
        if (mEquipment.get(body) == null) {
            return;
        }
        for (Effect effect: mEquipment.get(body).getEffects()) {
            mAttributes.get(effect.getAttributeName()).decrease(effect.getValue());
        }
        mEquipment.remove(body);
    }

    public void takeItem(Item item) {
        item.getState().onAddToInventory(mInventory);
    }

    public void throwAwayItem(Item item) {
        item.getState().onThrowAway(mInventory);
    }

    public ArrayList<Slot> getSlots() {
        return mSlots;
    }

    public void setSlots(ArrayList<Slot> slots) {
        mSlots = slots;
    }
}
