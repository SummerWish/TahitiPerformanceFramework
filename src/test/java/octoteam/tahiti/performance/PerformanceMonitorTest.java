package octoteam.tahiti.performance;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import octoteam.tahiti.performance.recorder.MeasurementRecorder;
import octoteam.tahiti.performance.reporter.LogReporter;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class PerformanceMonitorTest {

    @Test()
    public void testReport() {
        Appender dummyAppender = mock(Appender.class);
        when(dummyAppender.getName()).thenReturn("MOCK");

        Logger logger = (Logger) LoggerFactory.getLogger(PerformanceMonitorTest.class);
        logger.setAdditive(false);
        logger.addAppender(dummyAppender);

        LogReporter dummyLogReporter = new LogReporter(logger);

        MeasurementRecorder dummyMeasurementRecorder = mock(MeasurementRecorder.class);
        when(dummyMeasurementRecorder.getReport()).thenReturn("foo");

        PerformanceMonitor performanceMonitor = new PerformanceMonitor(dummyLogReporter);
        performanceMonitor.addRecorder(dummyMeasurementRecorder);
        performanceMonitor.report();

        verify(dummyMeasurementRecorder).reset();
        verify(dummyAppender).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                return ((LoggingEvent) argument).getFormattedMessage().contains("foo");
            }
        }));
    }

    @Test
    public void testSchedule() throws InterruptedException {
        Appender dummyAppender = mock(Appender.class);
        when(dummyAppender.getName()).thenReturn("MOCK");

        Logger logger = (Logger) LoggerFactory.getLogger(PerformanceMonitorTest.class);
        logger.setAdditive(false);
        logger.addAppender(dummyAppender);

        LogReporter dummyLogReporter = new LogReporter(logger);

        MeasurementRecorder dummyMeasurementRecorder = mock(MeasurementRecorder.class);
        when(dummyMeasurementRecorder.getReport()).thenReturn("foo");

        PerformanceMonitor performanceMonitor = new PerformanceMonitor(dummyLogReporter);
        assertFalse(performanceMonitor.isStarted());

        performanceMonitor.addRecorder(dummyMeasurementRecorder);
        performanceMonitor.start(500, TimeUnit.MILLISECONDS);
        assertTrue(performanceMonitor.isStarted());

        Thread.sleep(800);

        verify(dummyMeasurementRecorder).reset();
        verify(dummyAppender).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                return ((LoggingEvent) argument).getFormattedMessage().contains("foo");
            }
        }));

        performanceMonitor.stop();
        assertFalse(performanceMonitor.isStarted());
    }

}