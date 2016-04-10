package otcoteam.tahiti.performance.reporter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * jdquanyi
 * Created by Nick on 2016/4/10.
 */
public class LogReporterTest {

    LogReporter reporter = new LogReporter() {
        @Override
        public Logger getLogger() {
            return null;
        }

        @Override
        public PatternLayoutEncoder createEncoder() {
            return super.createEncoder();
        }

        @Override
        public Logger createLogger() {
            return super.createLogger();
        }
    };

    @Test
    public void getLogger() throws Exception {
        assertNull(reporter.getLogger());
    }

    @Test
    public void createEncoder() throws Exception {
        PatternLayoutEncoder encoder = reporter.createEncoder();
        assertNotNull(encoder.getContext());
        assertEquals("%d %-5level - %msg%n", encoder.getPattern());
    }

    @Test
    public void createLogger() throws Exception {
        assertEquals(Level.ALL, reporter.createLogger().getLevel());
    }

}