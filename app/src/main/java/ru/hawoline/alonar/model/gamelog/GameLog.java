package ru.hawoline.alonar.model.gamelog;

public class GameLog extends Log {
    private static Log mGameLog;

    private GameLog(int size) {
        super(size);
    }

    private GameLog() {
        this(0);
    }

    @Override
    public void init(int size) {
        if (mSize == 0) {
            mSize = size;
            mLog = new String[mSize];
        }
    }

    public static Log getInstance() {
        if (mGameLog == null) {
            mGameLog = new GameLog();
        }

        return mGameLog;
    }
}
