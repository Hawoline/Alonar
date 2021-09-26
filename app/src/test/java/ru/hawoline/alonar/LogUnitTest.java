package ru.hawoline.alonar;

import junit.framework.TestCase;
import org.junit.Test;
import ru.hawoline.alonar.model.gamelog.Log;
import ru.hawoline.alonar.model.gamelog.GameLog;

public class LogUnitTest {
    Log mLog;

    private void init() {
        mLog = GameLog.getInstance();
    }
    @Test
    public void testLog() {
        init();
        mLog.putToLog("hahahaha");
        TestCase.assertEquals("hahahaha", mLog.getCurrent());
        for (int i = 0; i < 9; i++) {
            mLog.putToLog("Test");
        }
        TestCase.assertEquals("Test", mLog.getCurrent());
    }

    @Test
    public void testGettingLog() {
        init();
        for (int i = 0; i < 15; i++) {
            mLog.putToLog(String.valueOf(i));
        }
        String[] log = mLog.showLog();

        TestCase.assertEquals("5", log[0]);
        TestCase.assertEquals("6", log[1]);
        TestCase.assertEquals("7", log[2]);
        TestCase.assertEquals("8", log[3]);
        TestCase.assertEquals("9", log[4]);
        TestCase.assertEquals("10", log[5]);
        TestCase.assertEquals("11", log[6]);
        TestCase.assertEquals("12", log[7]);
        TestCase.assertEquals("13", log[8]);
        TestCase.assertEquals("14", log[9]);
    }
}
