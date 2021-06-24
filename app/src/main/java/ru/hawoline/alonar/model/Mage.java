package ru.hawoline.alonar.model;

public class Mage extends Personage {

    public Mage() {
    }

    @Override
    public Personage createPersonage() {
        return new Mage();
    }
}
