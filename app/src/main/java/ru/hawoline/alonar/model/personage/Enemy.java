package ru.hawoline.alonar.model.personage;

import ru.hawoline.alonar.model.personage.specification.attribute.Attribute;
import ru.hawoline.alonar.model.personage.specification.attribute.AttributeName;

public class Enemy extends Personage {
    private String mName;

    private Enemy(String name) {
        mName = name;
        setAttribute(AttributeName.ENDURANCE, new Attribute(100));
    }

    public static Enemy createEnemy(String name) {
        return new Enemy(name);
    }

    public String getName() {
        return mName;
    }
}
