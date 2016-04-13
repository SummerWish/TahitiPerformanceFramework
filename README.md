# TahitiPerformanceMonitor

[![Build Status](https://travis-ci.org/SummerWish/TahitiPerformanceMonitor.svg?branch=master)](https://travis-ci.org/SummerWish/TahitiPerformanceMonitor)
[![Coverage Status](https://coveralls.io/repos/github/SummerWish/TahitiPerformanceMonitor/badge.svg?branch=master)](https://coveralls.io/github/SummerWish/TahitiPerformanceMonitor?branch=master)
[![Dependency Status](https://www.versioneye.com/user/projects/57091970fcd19a0045440822/badge.svg)](https://www.versioneye.com/user/projects/57091970fcd19a0045440822)

[Documentation](http://summerwish.github.io/TahitiPerformanceMonitor/)

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
            <id>tahiti-nexus-snapshots</id>
            <name>Tahiti NEXUS</name>
            <url>http://sse.tongji.edu.cn/tahiti/nexus/content/groups/public</url>
            <releases><enabled>false</enabled></releases>
            <snapshots><enabled>true</enabled></snapshots>
        </repository>
    </repositories>

    <dependencies>
        <!-- 此处可以有其他内容，已省略 -->
        <dependency>
            <groupId>octoteam.tahiti</groupId>
            <artifactId>tahiti-performance</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>

</project>
```

### 手工下载

- [tahiti-performance](http://sse.tongji.edu.cn/tahiti/nexus/service/local/repositories/public/content/octoteam/tahiti/tahiti-performance/1.0-SNAPSHOT/tahiti-performance-1.0-20160413.122610-5.jar)

除了这个库本身以外，TahitiPerformanceMonitor 还依赖于 [logback](http://logback.qos.ch/) 写入日志。您需要将以下 jar 全部下载下来添加到项目中：

- [logback-classic](http://central.maven.org/maven2/ch/qos/logback/logback-classic/1.1.7/logback-classic-1.1.7.jar)

- [logback-core](http://central.maven.org/maven2/ch/qos/logback/logback-core/1.1.7/logback-core-1.1.7.jar)

- [slf4j-api](http://central.maven.org/maven2/org/slf4j/slf4j-api/1.7.20/slf4j-api-1.7.20.jar)

## 示例

### 每分钟统计各个指标次数，并每分钟写入新日志文件

演示统计请求次数、登录次数等次数型指标。

```java
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
```

运行后，请查看当前目录下 `bar-xxxx-xx-xx_xx-xx.log`。每分钟都有一个日志文件，且每个日志文件都包含一分钟内 `request` 和 `login` 指标统计到的次数。

### 每分钟统计各个指标数值，并写入日志记录到同一个文件

演示统计内存占用率、CPU 占用率等数值型指标。

```java
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
```

运行后，请查看当前目录下 `cpu-usage.log`，记录了每分钟内 `cpu` 指标的最大值最小值和平均值。
