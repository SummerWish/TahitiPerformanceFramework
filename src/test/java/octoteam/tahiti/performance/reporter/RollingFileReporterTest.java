package octoteam.tahiti.performance.reporter;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.RollingPolicy;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class RollingFileReporterTest {

    @Test
    public void testRollingFileReporter() {
        String tempFile = System.getProperty("java.io.tmpdir") + Double.toString(System.nanoTime()) + "-%d{yyyy-MM-dd_HH-mm}.log";
        LogReporter reporter = new RollingFileReporter(tempFile);

        Logger logger = reporter.getLogger();
        assertNotNull(logger);

        Iterator<Appender<ILoggingEvent>> iterator = logger.iteratorForAppenders();
        assertTrue(iterator.hasNext());

        Appender appender = iterator.next();
        assertTrue(appender instanceof RollingFileAppender);

        RollingFileAppender rollingFileAppender = (RollingFileAppender) appender;
        RollingPolicy policy = rollingFileAppender.getRollingPolicy();
        assertTrue(policy instanceof TimeBasedRollingPolicy);

        TimeBasedRollingPolicy timeBasedRollingPolicy = (TimeBasedRollingPolicy) policy;
        assertEquals(tempFile, timeBasedRollingPolicy.getFileNamePattern());

        policy.stop();
        appender.stop();
    }

}