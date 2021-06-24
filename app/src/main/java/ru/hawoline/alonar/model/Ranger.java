package ru.hawoline.alonar.model;

public class Ranger extends Personage {
    @Override
    public Personage createPersonage() {
        return new Ranger();
    }
}
