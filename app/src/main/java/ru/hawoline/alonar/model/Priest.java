package ru.hawoline.alonar.model;

public class Priest extends Personage {
    @Override
    public Personage createPersonage() {
        return new Priest();
    }
}
