package otcoteam.tahiti.performance;

import ch.qos.logback.classic.Logger;
import otcoteam.tahiti.performance.recorder.CountingRecorder;
import otcoteam.tahiti.performance.recorder.MeasurementRecorder;
import otcoteam.tahiti.performance.recorder.QuantizedRecorder;
import otcoteam.tahiti.performance.reporter.LogReporter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 */
public class PerformanceMonitor {

    /**
     * TODO
     */
    private boolean started = false;

    /**
     * TODO
     */
    private Logger logger = null;

    /**
     * TODO
     */
    private HashMap<String, MeasurementRecorder> recorders = new HashMap<String, MeasurementRecorder>();

    /**
     * TODO
     *
     * @param reporter
     * @param period
     * @param unit
     */
    public PerformanceMonitor(LogReporter reporter, long period, TimeUnit unit) {
        this(reporter);
        start(period, unit);
    }

    /**
     * TODO
     *
     * @param reporter
     */
    public PerformanceMonitor(LogReporter reporter) {
        logger = reporter.getLogger();
    }

    /**
     * TODO
     *
     * @param period
     * @param unit
     */
    public void start(long period, TimeUnit unit) {
        if (started) {
            return;
        }
        started = true;
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                PerformanceMonitor.this.report();
            }
        }, period, period, unit);
    }

    /**
     * TODO
     *
     * @param key
     * @param recorder
     */
    public void addRecorder(String key, MeasurementRecorder recorder) {
        recorders.put(key, recorder);
    }

    /**
     * TODO
     *
     * @param key
     */
    public void record(String key) {
        if (!recorders.containsKey(key)) {
            throw new IllegalArgumentException("Recorder not found");
        }
        MeasurementRecorder recorder = recorders.get(key);
        if (!(recorder instanceof CountingRecorder)) {
            throw new IllegalArgumentException("Not an instance of CountingRecorder");
        }
        ((CountingRecorder) recorder).record();
    }

    /**
     * TODO
     *
     * @param key
     * @param value
     */
    public void record(String key, double value) {
        if (!recorders.containsKey(key)) {
            throw new IllegalArgumentException("Recorder not found");
        }
        MeasurementRecorder recorder = recorders.get(key);
        if (!(recorder instanceof QuantizedRecorder)) {
            throw new ClassCastException("Not an instance of QuantizedRecorder");
        }
        ((QuantizedRecorder) recorder).record(value);
    }

    /**
     * TODO
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * TODO
     */
    protected void report() {
        for (Map.Entry<String, MeasurementRecorder> entry : recorders.entrySet()) {
            logger.info(String.format("%s: %s", entry.getValue().getName(), entry.getValue().getReport()));
            entry.getValue().reset();
        }
    }

}
