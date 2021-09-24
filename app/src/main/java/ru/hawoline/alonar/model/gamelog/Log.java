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
        String[] showedLog = new String[mSize];
        for (int i = 0; i < mSize; i++) {
            showedLog[i] = mLog[(mCurrentAction + i) % mSize];
        }
        return showedLog;
    }

    public String getCurrent() {
        return mLog[(mCurrentAction - 1) % mSize];
    }
}
