package ru.hawoline.alonar.domain.model.map;

public class LandscapeMap extends Map {
    public static final int GRASS = 0;
    public static int MOUNTAIN = 1;

    public LandscapeMap(int size) {
        super(size);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = GRASS;
            }
        }

        for (int i = 0; i < map.length; i++) {
            map[i][0] = MOUNTAIN;
        }
        for (int i = 0; i < map.length; i++) {
            map[0][i] = MOUNTAIN;
        }
        for (int i = 0; i < map.length; i++) {
            map[map.length - 1][i] = MOUNTAIN;
        }
        for (int i = 0; i < map.length; i++) {
            map[i][map.length - 1] = MOUNTAIN;
        }
    }
}
