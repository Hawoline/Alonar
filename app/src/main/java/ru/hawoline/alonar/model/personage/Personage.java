package ru.hawoline.alonar.model.personage;

import androidx.annotation.NonNull;
import ru.hawoline.alonar.model.personage.effect.Effect;
import ru.hawoline.alonar.model.personage.inventory.Inventory;
import ru.hawoline.alonar.model.personage.item.Item;
import ru.hawoline.alonar.model.personage.item.equipment.Body;
import ru.hawoline.alonar.model.personage.item.equipment.Equipment;
import ru.hawoline.alonar.model.personage.specification.Vitality;
import ru.hawoline.alonar.model.personage.specification.VitalityType;
import ru.hawoline.alonar.model.personage.specification.attribute.AttributeName;
import ru.hawoline.alonar.model.personage.specification.attribute.PersonageAttribute;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Personage {
    private ArrayList<Vitality> vitalities;
    private HashMap<AttributeName, PersonageAttribute> attributes;
    private HashMap<Body, Equipment> equipment;
    private Inventory inventory;
    private ArrayList<DamageSlot> slots;
    private int experience;
    private int armor; // In percent. Max is 80%

    protected Personage() {
        attributes = new HashMap<>();
        vitalities = new ArrayList<>();
        vitalities.add(new Vitality(VitalityType.HEATH, 10));
        vitalities.add(new Vitality(VitalityType.MP, 10));
        equipment = new HashMap<>();
        experience = 0;
        armor = 10;
        inventory = new Inventory();
    }

    private void addEquipmentEffectBonuses() {
        for (Body body: equipment.keySet()) {
            for (Effect effect: equipment.get(body).getEffects()) {
                attributes.get(effect.getAttributeName()).increase(effect.getValue());
            }
        }
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getHealth() {
        return vitalities.get(Vitality.HP).getResidualMax().getSecond();
    }
    public void setHealth(int heath) {
        vitalities.get(Vitality.HP).setValue(heath);
    }

    public int getMp() {
        return vitalities.get(Vitality.MP).getResidualMax().getSecond();
    }
    public void setMp(int mp) {
        if (mp > -1) {
            vitalities.get(Vitality.MP).setValue(mp);
        }
    }

    public void setAttribute(AttributeName attributeName, PersonageAttribute personageAttributeValue) {
        attributes.put(attributeName, personageAttributeValue);
        if (attributeName == AttributeName.ENDURANCE) {
            vitalities.get(Vitality.HP).setMaxValue(personageAttributeValue.getMax() * 10);
        } else if (attributeName == AttributeName.INTELLIGENCE) {
            vitalities.get(Vitality.MP).setMaxValue(personageAttributeValue.getMax() * 16);
        }
    }

    public PersonageAttribute getAttribute(AttributeName attributeName) {
        return attributes.get(attributeName);
    }

    public HashMap<Body, Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(HashMap<Body, Equipment> equipment) {
        this.equipment = equipment;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void equip(Body body, @NonNull Equipment equipment) {
        unequip(body);
        if (equipment.getRequiredBody() == body) {
            this.equipment.put(body, equipment);
            for (Effect effect: equipment.getEffects()) {
                attributes.get(effect.getAttributeName()).increase(effect.getValue());
            }
        }
    }

    public void unequip(Body body) {
        if (equipment.get(body) == null) {
            return;
        }
        for (Effect effect: equipment.get(body).getEffects()) {
            attributes.get(effect.getAttributeName()).decrease(effect.getValue());
        }
        equipment.remove(body);
    }

    public void takeItem(Item item) {
        item.getState().onAddToInventory(inventory);
    }

    public void throwAwayItem(Item item) {
        item.getState().onThrowAway(inventory);
    }

    public ArrayList<DamageSlot> getDamageSlots() {
        return slots;
    }

    public void setSlots(ArrayList<DamageSlot> slots) {
        this.slots = slots;
    }
}
