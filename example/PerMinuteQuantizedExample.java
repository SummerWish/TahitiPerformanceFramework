import otcoteam.tahiti.performance.PerformanceMonitor;
import otcoteam.tahiti.performance.recorder.QuantizedRecorder;
import otcoteam.tahiti.performance.reporter.AppendFileReporter;
import otcoteam.tahiti.performance.reporter.LogReporter;

import java.util.EnumSet;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PerMinuteQuantizedExample {
    public static void main(String[] args) {
        // 首先需要一个报告生成器, 此处我们建立 AppendFileReporter, 即
        // 生成报告追加到同一个文件中.
        LogReporter reporter = new AppendFileReporter("cpu-usage.log");

        // 接下来创建性能监控实例
        final PerformanceMonitor monitor = new PerformanceMonitor(reporter);

        // 对于要统计的指标, 需要进行初始化. 此处使用 QuantizedRecorder, 即
        // 数值型指标. 最后报告中将输出 cpu 指标在每个周期的最大值最小值和平均值
        final QuantizedRecorder cpuUsage = new QuantizedRecorder("CPU Usage", EnumSet.of(
                QuantizedRecorder.OutputField.MAX,
                QuantizedRecorder.OutputField.MIN,
                QuantizedRecorder.OutputField.AVERAGE
        ));

        // 每分钟统计一次指标输出报告
        monitor
                .addRecorder(cpuUsage)
                .start(1, TimeUnit.MINUTES);

        // 每秒采样一次 CPU 占用, 作为演示, 这里用随机数代表 CPU 占用
        final Random r = new Random();
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                cpuUsage.record(r.nextInt(100));
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}
