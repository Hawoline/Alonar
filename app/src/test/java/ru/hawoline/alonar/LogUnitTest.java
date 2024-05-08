package ru.hawoline.alonar;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import ru.hawoline.alonar.model.gamelog.Log;
import ru.hawoline.alonar.model.gamelog.GameLog;

public class LogUnitTest {
    private Log log;

    @Before
    public void init() {
        log = GameLog.getInstance();
    }

    @Test
    public void testLog() {
        log.putAction("hahahaha");
        TestCase.assertEquals("hahahaha", log.getCurrent());
        for (int i = 0; i < 9; i++) {
            log.putAction("Test");
        }
        TestCase.assertEquals("Test", log.getCurrent());
    }

    @Test
    public void testGettingLog() {
        for (int i = 0; i < 15; i++) {
            log.putAction(String.valueOf(i));
        }
        String[] log = this.log.show();

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
