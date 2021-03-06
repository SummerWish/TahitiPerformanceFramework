package octoteam.tahiti.performance.example;

import octoteam.tahiti.performance.PerformanceMonitor;
import octoteam.tahiti.performance.recorder.CountingRecorder;
import octoteam.tahiti.performance.reporter.LogReporter;
import octoteam.tahiti.performance.reporter.RollingFileReporter;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PerMinuteCountingExample {

    public static void main(String args[]) {
        // 首先需要一个报告生成器, 此处我们建立 RollingFileReporter, 即
        // 生成报告到一组文件中. 由于时间输出格式是 yyyy-MM-dd_HH-mm, 因
        // 此将每分钟生成一个新文件.
        LogReporter reporter = new RollingFileReporter("bar-%d{yyyy-MM-dd_HH-mm}.log");

        // 接下来创建性能监控实例
        final PerformanceMonitor monitor = new PerformanceMonitor(reporter);

        // 对于要统计的指标, 需要进行初始化. 此处使用 CountingRecorder, 即
        // 计数型指标. 对于计数型指标, 报告时将累加这段时间内的记录次数.
        final CountingRecorder requestTimes = new CountingRecorder("Request times");
        final CountingRecorder loginTimes = new CountingRecorder("User login times");

        // 将指标加入监控器, 并开始定时报告, 此处是每 1 分钟统计一次.
        monitor
                .addRecorder(requestTimes)
                .addRecorder(loginTimes)
                .start(1, TimeUnit.MINUTES);

        // 以下模拟每 5 秒有一次 request
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                requestTimes.record();
            }
        }, 5, 5, TimeUnit.SECONDS);

        // 以下模拟每 10 秒有一次 login
        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                loginTimes.record();
            }
        }, 10, 10, TimeUnit.SECONDS);
    }

}
