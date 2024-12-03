package ru.hawoline.alonar.domain.model.personage.item.equipment.clothing;

import ru.hawoline.alonar.domain.model.personage.item.Quality;
import ru.hawoline.alonar.domain.model.personage.item.equipment.Body;
import ru.hawoline.alonar.domain.model.personage.item.equipment.Equipment;
import ru.hawoline.alonar.util.Pair;

public class Clothing extends Equipment {
    private int armor;
    private ClothingType clothingType;

    public Clothing(String name, int requiredLevel, Quality quality, Pair<Integer, Integer> strength, Body requiredBody) {
        super(name, requiredLevel, quality, strength, requiredBody);
        setClothingType(ClothingType.RAG);
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public ClothingType getClothingType() {
        return clothingType;
    }

    public void setClothingType(ClothingType clothingType) {
        this.clothingType = clothingType;
    }
}
