package ru.hawoline.alonar.model.personage.money;

import java.io.Serializable;

public class Money {
    private int money;
    private int copper;
    private int silver;
    private int gold;
    private int doubloon;

    public Money(int money, int doubloon) {
        this.money = money;
        this.doubloon = doubloon;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
        copper = this.money % 100;
        silver = this.money / 100 % 100;
        gold = this.money / 10000;
    }

    public int getDoubloon() {
        return doubloon;
    }

    public void setDoubloon(int doubloon) {
        this.doubloon = doubloon;
    }

    public int getCopper() {
        return copper;
    }

    public int getSilver() {
        return silver;
    }

    public int getGold() {
        return gold;
    }
}
