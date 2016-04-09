# TahitiPerformanceMonitor

[![Build Status](https://travis-ci.org/SummerWish/TahitiPerformanceMonitor.svg?branch=master)](https://travis-ci.org/SummerWish/TahitiPerformanceMonitor) [![Coverage Status](https://coveralls.io/repos/github/SummerWish/TahitiPerformanceMonitor/badge.svg?branch=master)](https://coveralls.io/github/SummerWish/TahitiPerformanceMonitor?branch=master)

一个简单的性能监控库。

该监控库支持监控统计两类指标：

- 次数指标 (`CountingRecorder`)，报表中反馈出来的是每个周期次数总和（例如统计一分钟内用户登录了多少次）

- 数值指标 (`QuantizedRecorder`)，报表中反馈出来的是每个周期数值的累加、平均、最值等（例如统计每天同时在线人数的最大值和平均值）

该监控库还支持两种报告写入方式：

- 按指定周期写入不同日志 (`RollingFileReporter`)，每个周期的报表按时间写入不同日志中（如每天一个文件、每小时一个文件、每分钟一个文件等）

- 追加写入同一个日志 (`AppendFileReporter`)，每个周期的报表都会写入到同一个日志中

## 下载

### Maven

您可以使用 Maven 下载这个库到您的项目中。请在 pom.xml 中添加我们的 repository 和这个项目：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <!-- 此处可以有其他内容，已省略 -->
    
    <repositories>
        <repository>
            <id>tahiti</id>
            <url>http://10.60.40.241:8888/repository/internal/</url>
            <releases>
                <enabled>false</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>

    <dependencies>
        <!-- 此处可以有其他内容，已省略 -->
        <dependency>
            <groupId>octoteam.tahiti</groupId>
            <artifactId>performance</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>

</project>
```

### 手工下载

TahitiPerformanceMonitor 依赖于 [logback](http://logback.qos.ch/) 写入日志。您需要将以下 jar 全部下载下来添加到项目中：

- [logback-classic](http://central.maven.org/maven2/ch/qos/logback/logback-classic/1.1.6/logback-classic-1.1.6.jar)

- [logback-core](http://central.maven.org/maven2/ch/qos/logback/logback-core/1.1.6/logback-core-1.1.6.jar)

- [slf4j-api](http://central.maven.org/maven2/org/slf4j/slf4j-api/1.7.18/slf4j-api-1.7.18.jar)

- [performance](http://10.60.40.241:8888/repository/snapshots/octoteam/tahiti/performance/1.0-SNAPSHOT/performance-1.0-20160409.110112-1.jar)

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

运行后，请查看当前目录下 `bar-xxxx-xx-xx_xx-xx.log`。每分钟都有一个日志文件，且每个日志文件都包含一分钟内 `request` 和 `login` 指标统计到的次数。

### 每分钟统计指标各类型数值，并写入日志记录到同一个文件

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
运行后，请查看当前目录下 `foo-xxxx-xx-xx_xx-xx.log`。每分钟都有一个日志文件，且每个日志文件都包含一分钟内 `request` 和 `login` 指标统计到的各类型数值。

