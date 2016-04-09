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
 * 用于管理性能监测与生成日志记录
 */
public class PerformanceMonitor {

    /**
     * 启动状态
     */
    private boolean started = false;

    /**
     * 日志生成器
     */
    private Logger logger = null;

    /**
     * 指标记录器的集合
     */
    private HashMap<String, MeasurementRecorder> recorders = new HashMap<String, MeasurementRecorder>();

    /**
     * 初始化
     *
     * @param reporter 封装了日志生成器
     * @param period   生成日志的时间间隔
     * @param unit     时间单位
     */
    public PerformanceMonitor(LogReporter reporter, long period, TimeUnit unit) {
        this(reporter);
        start(period, unit);
    }

    /**
     * 初始化
     *
     * @param reporter 封装了日志生成器
     */
    public PerformanceMonitor(LogReporter reporter) {
        logger = reporter.getLogger();
    }

    /**
     * 开始性能检测并定时生成日志
     *
     * @param period 生成日志的时间间隔
     * @param unit   时间单位
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
     * 添加指标记录器
     *
     * @param key      用于标识指标记录器的关键字
     * @param recorder 指标记录器
     */
    public void addRecorder(String key, MeasurementRecorder recorder) {
        recorders.put(key, recorder);
    }

    /**
     * 记录指标数值
     *
     * @param key 用于标识指标记录器的关键字
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
     * 记录指标数值
     *
     * @param key   用于标识指标记录器的关键字
     * @param value 指标值
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
     * 是否已经启动
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * 生成报告日志
     */
    protected void report() {
        for (Map.Entry<String, MeasurementRecorder> entry : recorders.entrySet()) {
            logger.info(String.format("%s: %s", entry.getValue().getName(), entry.getValue().getReport()));
            entry.getValue().reset();
        }
    }

}
