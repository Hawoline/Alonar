package ru.hawoline.alonar.model;

public class Warrior extends Personage {
    @Override
    public Personage createPersonage() {
        return new Warrior();
    }
}
