package ru.hawoline.alonar.model;

public class Robber extends Personage {
    @Override
    public Personage createPersonage() {
        return new Robber();
    }
}
