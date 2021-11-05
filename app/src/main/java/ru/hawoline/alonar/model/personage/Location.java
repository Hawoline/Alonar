package ru.hawoline.alonar.model.personage;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;

public class Location implements Serializable {
    private int mX;
    private int mY;
    private int mDirection;

    private static final long serialVersionUID = 4815547538178012906L;

    public static final int DIRECTION_FORWARD = 0;
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_BACK = 2;
    public static final int DIRECTION_LEFT = 3;

    public Location(int x, int y) {
        mX = x;
        mY = y;
        mDirection = DIRECTION_RIGHT;
    }

    public synchronized int getX() {
        return mX;
    }

    public synchronized void setX(int x) {
        mX = x;
    }

    public synchronized int getY() {
        return mY;
    }

    public synchronized void setY(int y) {
        mY = y;
    }

    public int getDirection() {
        return mDirection;
    }

    public synchronized void move(int xStep, int yStep) {
        if (mY + yStep >= 0) {
            if (yStep > 0) {
                mDirection = DIRECTION_BACK;
            } else if (yStep < 0) {
                mDirection = DIRECTION_FORWARD;
            }

            if (Math.abs(yStep) < 3) {
                mY += yStep;
            }
        }
        if (mX + xStep >= 0) {
            if (xStep > Math.abs(yStep)) {
                mDirection = DIRECTION_RIGHT;
            } else if (xStep < 0 && Math.abs(xStep) > Math.abs(yStep)) {
                mDirection = DIRECTION_LEFT;
            }

            if (Math.abs(xStep) < 3) {
                mX += xStep;
            }
        }
    }
}
