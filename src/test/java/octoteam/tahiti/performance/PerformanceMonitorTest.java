package octoteam.tahiti.performance;

import octoteam.tahiti.performance.recorder.MeasurementRecorder;
import org.junit.Test;
import org.slf4j.Logger;
import octoteam.tahiti.performance.reporter.LogReporter;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class PerformanceMonitorTest {

    @Test()
    public void testReport() {
        Logger logger = mock(Logger.class);
        LogReporter dummyLogReporter = mock(LogReporter.class);
        when(dummyLogReporter.getLogger()).thenReturn(logger);
        MeasurementRecorder dummyMeasurementRecorder = mock(MeasurementRecorder.class);
        when(dummyMeasurementRecorder.getName()).thenReturn("name");
        when(dummyMeasurementRecorder.getReport()).thenReturn("foo");

        PerformanceMonitor performanceMonitor = new PerformanceMonitor(dummyLogReporter);
        performanceMonitor.addRecorder(dummyMeasurementRecorder);
        performanceMonitor.report();

        verify(dummyMeasurementRecorder).reset();
        verify(logger).info("name: foo");
    }

    @Test
    public void testSchedule() throws InterruptedException {
        Logger logger = mock(Logger.class);
        LogReporter dummyLogReporter = mock(LogReporter.class);
        when(dummyLogReporter.getLogger()).thenReturn(logger);
        MeasurementRecorder dummyMeasurementRecorder = mock(MeasurementRecorder.class);
        when(dummyMeasurementRecorder.getName()).thenReturn("name");
        when(dummyMeasurementRecorder.getReport()).thenReturn("foo");

        PerformanceMonitor performanceMonitor = new PerformanceMonitor(dummyLogReporter);
        assertFalse(performanceMonitor.isStarted());

        performanceMonitor.addRecorder(dummyMeasurementRecorder);
        performanceMonitor.start(500, TimeUnit.MILLISECONDS);
        assertTrue(performanceMonitor.isStarted());

        Thread.sleep(800);

        verify(dummyMeasurementRecorder).reset();
        verify(logger).info("name: foo");

        performanceMonitor.stop();
        assertFalse(performanceMonitor.isStarted());
    }

}