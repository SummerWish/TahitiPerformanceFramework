package otcoteam.tahiti.performance.reporter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.RollingPolicy;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.Iterator;
import java.util.Random;

import static org.junit.Assert.*;

public class RollingFileReporterTest {

    @Test
    public void testRollingFileReporter() {
        String tempFile = System.getProperty("java.io.tmpdir") + Double.toString(new Random().nextDouble()) + "-%d{yyyy-MM-dd_HH-mm}";
        LogReporter reporter = new RollingFileReporter(tempFile);

        Logger logger = reporter.getLogger();
        assertNotNull(logger);

        Iterator<Appender<ILoggingEvent>> iterator = ((ch.qos.logback.classic.Logger) logger).iteratorForAppenders();
        assertTrue(iterator.hasNext());

        Appender appender = iterator.next();
        assertTrue(appender instanceof FileAppender);
        appender = iterator.next();
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