package ru.hawoline.alonar.domain.model.personage;

import java.io.Serializable;

public class Location {
    private int x;
    private int y;
    private int direction;

    public static final int DIRECTION_FORWARD = 0;
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_BACK = 2;
    public static final int DIRECTION_LEFT = 3;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
        direction = DIRECTION_RIGHT;
    }

    public synchronized int getX() {
        return x;
    }

    public synchronized void setX(int x) {
        this.x = x;
    }

    public synchronized int getY() {
        return y;
    }

    public synchronized void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public synchronized void move(int xStep, int yStep) {
        if (y + yStep >= 0) {
            if (yStep > 0) {
                direction = DIRECTION_BACK;
            } else if (yStep < 0) {
                direction = DIRECTION_FORWARD;
            }

            if (Math.abs(yStep) < 3) {
                y += yStep;
            }
        }
        if (x + xStep >= 0) {
            if (xStep > Math.abs(yStep)) {
                direction = DIRECTION_RIGHT;
            } else if (xStep < 0 && Math.abs(xStep) > Math.abs(yStep)) {
                direction = DIRECTION_LEFT;
            }

            if (Math.abs(xStep) < 3) {
                x += xStep;
            }
        }
    }
}
