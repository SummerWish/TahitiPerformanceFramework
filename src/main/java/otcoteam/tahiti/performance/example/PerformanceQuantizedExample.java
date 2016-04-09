package otcoteam.tahiti.performance.example;

import otcoteam.tahiti.performance.PerformanceMonitor;
import otcoteam.tahiti.performance.recorder.QuantizedRecorder;
import otcoteam.tahiti.performance.reporter.LogReporter;
import otcoteam.tahiti.performance.reporter.RollingFileReporter;

import java.util.EnumSet;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PerformanceQuantizedExample {
    public static void main(String[] args) {
        LogReporter reporter = new RollingFileReporter("foo-%d{yyyy-MM-dd_HH-mm}.log");

        final PerformanceMonitor monitor = new PerformanceMonitor(reporter);

        EnumSet<QuantizedRecorder.OutputField> requestQuantizedFields =
                EnumSet.of(
                        QuantizedRecorder.OutputField.SUM,
                        QuantizedRecorder.OutputField.MAX,
                        QuantizedRecorder.OutputField.MIN,
                        QuantizedRecorder.OutputField.AVERAGE
                        );
        EnumSet<QuantizedRecorder.OutputField> loginQuantizedFields =
                EnumSet.of(
                        QuantizedRecorder.OutputField.MAX,
                        QuantizedRecorder.OutputField.MIN
                );

        monitor.addRecorder("request", new QuantizedRecorder("Request quantize",requestQuantizedFields));
        monitor.addRecorder("login", new QuantizedRecorder("Login quantize", loginQuantizedFields));

        monitor.start(1, TimeUnit.MINUTES);

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        final Random r = new Random();
        executorService.scheduleAtFixedRate(
                new Runnable() {
                    public void run() {
                        monitor.record("request", r.nextDouble());
                    }
                },
                5, 5, TimeUnit.SECONDS
        );
        executorService.scheduleAtFixedRate(
                new Runnable() {
                    public void run() {
                        monitor.record("login", r.nextDouble());
                    }
                },
                10, 10, TimeUnit.SECONDS
        );

    }
}
