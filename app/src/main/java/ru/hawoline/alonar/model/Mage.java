package ru.hawoline.alonar.model;

public class Mage extends Personage {

    private Mage() {
        super();
    }

    public static Personage createPersonage() {
        return new Mage();
    }
}
