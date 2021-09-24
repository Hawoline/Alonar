package ru.hawoline.alonar;

import junit.framework.TestCase;
import org.junit.Test;
import ru.hawoline.alonar.model.gamelog.Log;
import ru.hawoline.alonar.model.gamelog.LogImpl;

public class LogUnitTest {
    @Test
    public void testLog() {
        Log log = new LogImpl(10);
        log.putToLog("hahahaha");
        TestCase.assertEquals("hahahaha", log.getCurrent());
        for (int i = 0; i < 10; i++) {
            log.putToLog("Test");
        }
        TestCase.assertEquals("Test", log.getCurrent());
    }
}
