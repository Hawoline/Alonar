package ru.hawoline.alonar.domain.model.gamelog;

public class GameLog extends Log {
    private static Log instance;

    private GameLog() {
        super(10);
    }

    public static Log getInstance() {
        if (instance == null) {
            instance = new GameLog();
        }

        return instance;
    }
}
