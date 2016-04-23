package octoteam.tahiti.performance.reporter;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

public class LogReporterTest {

    @Test
    public void testMultipleReporter() {
        Appender appender1 = mock(Appender.class);
        when(appender1.getName()).thenReturn("MOCK1");
        LogReporter reporter1 = new LogReporter(LogReporterTest.class, null);
        reporter1.getLogger().addAppender(appender1);

        Appender appender2 = mock(Appender.class);
        when(appender2.getName()).thenReturn("MOCK2");
        LogReporter reporter2 = new LogReporter(LogReporterTest.class, null);
        reporter2.getLogger().addAppender(appender2);

        assertNotEquals(reporter1.getLogger(), reporter2.getLogger());

        reporter1.getLogger().info("foo");
        verify(appender1).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                return ((LoggingEvent) argument).getFormattedMessage().contains("foo");
            }
        }));
        verify(appender2, never()).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                return ((LoggingEvent) argument).getFormattedMessage().contains("foo");
            }
        }));

        reporter2.getLogger().info("bar");
        verify(appender1, never()).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                return ((LoggingEvent) argument).getFormattedMessage().contains("bar");
            }
        }));
        verify(appender2).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                return ((LoggingEvent) argument).getFormattedMessage().contains("bar");
            }
        }));
    }

}