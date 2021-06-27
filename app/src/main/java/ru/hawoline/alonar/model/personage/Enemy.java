package ru.hawoline.alonar.model.personage;

public class Enemy extends Personage {
    private String mName;

    private Enemy(String name) {
        mName = name;
        setEndurance(100);
    }

    public static Personage createEnemy(String name) {
        return new Enemy(name);
    }
}
