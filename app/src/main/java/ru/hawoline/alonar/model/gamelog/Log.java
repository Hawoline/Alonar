package ru.hawoline.alonar.model.gamelog;

public abstract class Log {
    protected String[] mLog;
    protected int mSize;

    private int mCurrentAction;

    public Log(int size) {
        mSize = size;
        mLog = new String[mSize];
        mCurrentAction = 0;
    }

    public void putToLog(String action) {
        mLog[mCurrentAction++ % mSize] = action;
    }

    public String[] showLog() {
        String[] showedLog = new String[mSize];
        for (int i = 0; i < mSize; i++) {
            showedLog[i] = mLog[(mCurrentAction + i) % mSize];
        }
        return showedLog;
    }

    public String getCurrent() {
        return mLog[(mCurrentAction - 1) % mSize];
    }

    public abstract void init(int size);
}
