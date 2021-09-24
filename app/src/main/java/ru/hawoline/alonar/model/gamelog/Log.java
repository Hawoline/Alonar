package ru.hawoline.alonar.model.gamelog;

public abstract class Log {
    private String[] mLog;
    private int mSize;

    private int mCurrentAction;

    public Log(int size) {
        mSize = size;
        mLog = new String[mSize];
        mCurrentAction = 0;
    }

    public void putToLog(String action) {
        mLog[mCurrentAction++ % mSize] = action;
    }

    public String[] getLog() {
        return mLog;
    }

    public String getCurrent() {
        return mLog[(mCurrentAction - 1) % mSize];
    }
}
