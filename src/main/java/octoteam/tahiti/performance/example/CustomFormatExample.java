package octoteam.tahiti.performance.example;

import octoteam.tahiti.performance.PerformanceMonitor;
import octoteam.tahiti.performance.formatter.IReportFormatable;
import octoteam.tahiti.performance.recorder.CountingRecorder;
import octoteam.tahiti.performance.reporter.AppendFileReporter;
import octoteam.tahiti.performance.reporter.LogReporter;

import java.util.concurrent.TimeUnit;

public class CustomFormatExample {

    public static void main(String args[]) {
        LogReporter reporter = new AppendFileReporter("report.log", "[%d] %marker is %msg%n");
        PerformanceMonitor monitor = new PerformanceMonitor(reporter);

        CountingRecorder requestTimes = new CountingRecorder("Request times", new IReportFormatable<Long>() {
            public String formatReport(Long data) {
                return String.format("CountingRecord(%d)", data);
            }
        });

        monitor
                .addRecorder(requestTimes)
                .start(1, TimeUnit.MINUTES);

    }

}
