package ru.hawoline.alonar.domain.gamelog;

public abstract class Log {
    protected String[] actions;
    protected int size;

    private int currentAction;

    protected Log(int size) {
        this.size = size;
        actions = new String[this.size];
        currentAction = 0;
    }

    public void putAction(String action) {
        actions[currentAction++ % size] = action;
    }

    public String[] show() {
        String[] actions = new String[size];
        for (int i = 0; i < size; i++) {
            actions[i] = this.actions[(currentAction + i) % size];
        }
        return actions;
    }

    public String getCurrent() {
        return actions[(currentAction - 1) % size];
    }

    public void clear() {
        actions = new String[size];
        currentAction = 0;
    }
}
