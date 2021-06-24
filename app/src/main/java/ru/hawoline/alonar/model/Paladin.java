package ru.hawoline.alonar.model;

public class Paladin extends Personage {
    @Override
    public Personage createPersonage() {
        return new Paladin();
    }
}
