# TahitiPerformanceMonitor

一个简单的性能监控库。

- 支持统计多种性能指标（次数、均值、最大值、最小值）

- 支持定时生成性能日志（支持追加方式或分文件方式）

## 下载

### Maven

您可以使用 Maven 下载这个库到您的项目中：

TODO

### 手工下载

TahitiPerformanceMonitor 依赖于 logback 写入日志。

TODO

## 示例

### 每分钟统计各个指标次数，并每分钟写入新日志文件

```java
import otcoteam.tahiti.performance.recorder.CountingRecorder;
import otcoteam.tahiti.performance.reporter.LogReporter;
import otcoteam.tahiti.performance.reporter.RollingFileReporter;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PerMiniuteCountingExample {

    public static void main(String args[]) {
        // 首先需要一个日志生成器, 此处我们建立 RollingFileReporter, 即
        // 生成报告到一组文件中. 由于时间输出格式是 yyyy-MM-dd_HH-mm, 因
        // 此将每分钟生成一个新的日志文件.
        LogReporter reporter = new RollingFileReporter("bar-%d{yyyy-MM-dd_HH-mm}.log");

        // 接下来创建性能监控实例
        final PerformanceMonitor monitor = new PerformanceMonitor(reporter);

        // 对于要统计的指标, 需要进行初始化. 此处使用 CountingRecorder, 即
        // 计数型指标. 对于计数型指标, 报告时将累加这段时间内的记录次数.
        monitor.addRecorder("request", new CountingRecorder("Request times"));
        monitor.addRecorder("login", new CountingRecorder("User login times"));

        // 开始定时生成日志文件, 此处是每 1 分钟统计一次.
        monitor.start(1, TimeUnit.MINUTES);

        // 以下模拟每 5 秒有一次 request
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                monitor.record("request");
            }
        }, 5, 5, TimeUnit.SECONDS);

        // 以下模拟每 10 秒有一次 login
        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                monitor.record("login");
            }
        }, 10, 10, TimeUnit.SECONDS);
    }

}
```

运行后，请查看当前目录下 `foo-xxxx-xx-xx_xx-xx.log`。每分钟都有一个日志文件，且每个日志文件都包含一分钟内 `request` 和 `login` 指标统计到的次数。

### 每分钟统计指标均值和最值，并写入日志记录到同一个文件

```java
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

        // 实例化日志生成器，以每分钟新建日志文件的方式记录日志
        LogReporter reporter = new RollingFileReporter("foo-%d{yyyy-MM-dd_HH-mm}.log");

        // 性能监控实例
        final PerformanceMonitor monitor = new PerformanceMonitor(reporter);

        // 指定记录每分钟内请求（request）的需要记录的数值
        // 包括：总值，最大值，最小值，平均值
        EnumSet<QuantizedRecorder.OutputField> requestQuantizedFields =
                EnumSet.of(
                        QuantizedRecorder.OutputField.SUM,
                        QuantizedRecorder.OutputField.MAX,
                        QuantizedRecorder.OutputField.MIN,
                        QuantizedRecorder.OutputField.AVERAGE
                        );
        // 指定记录每分钟内登陆（login）的需要记录的数值
        // 包括：最大值，最小值
        EnumSet<QuantizedRecorder.OutputField> loginQuantizedFields =
                EnumSet.of(
                        QuantizedRecorder.OutputField.MAX,
                        QuantizedRecorder.OutputField.MIN
                );

        // 在性能监控实例中添加 request 和 login 的数值记录器
        monitor.addRecorder("request", new QuantizedRecorder("Request quantize",requestQuantizedFields));
        monitor.addRecorder("login", new QuantizedRecorder("Login quantize", loginQuantizedFields));

        // 开始性能监控
        monitor.start(1, TimeUnit.MINUTES);

        // 以下模拟每 5 秒记录一次请求（request） 数据
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

        // 以下模拟每 10 秒记录一次登陆（login）数据
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

```
