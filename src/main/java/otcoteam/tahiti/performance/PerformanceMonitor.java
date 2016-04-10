package otcoteam.tahiti.performance;

import org.slf4j.Logger;
import otcoteam.tahiti.performance.recorder.MeasurementRecorder;
import otcoteam.tahiti.performance.reporter.LogReporter;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 性能检测
 */
public class PerformanceMonitor {

    /**
     * 日志生成器
     */
    private Logger logger = null;

    /**
     * 定时调用服务
     */
    private ScheduledExecutorService executorService;

    /**
     * 指标记录器的集合
     */
    private List<MeasurementRecorder> recorders = new LinkedList<MeasurementRecorder>();

    /**
     * 初始化
     *
     * @param reporter 日志生成器
     */
    public PerformanceMonitor(LogReporter reporter) {
        logger = reporter.getLogger();
    }

    /**
     * 开始定时生成报告
     *
     * @param period 生成报告的时间间隔
     * @param unit   时间单位
     * @return 当前实例供链式调用
     */
    public PerformanceMonitor start(long period, TimeUnit unit) {
        if (executorService != null) {
            return this;
        }
        executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                PerformanceMonitor.this.report();
            }
        }, period, period, unit);
        return this;
    }

    /**
     * 停止定时生成日志
     *
     * @return 当前实例供链式调用
     */
    public PerformanceMonitor stop() {
        if (executorService == null) {
            return this;
        }
        executorService.shutdownNow();
        executorService = null;
        return this;
    }

    /**
     * 添加指标记录器
     *
     * @param recorder 指标记录器
     * @return 当前实例供链式调用
     */
    public PerformanceMonitor addRecorder(MeasurementRecorder recorder) {
        recorders.add(recorder);
        return this;
    }

    /**
     * 是否已启动定时报告生成
     *
     * @return 是否已经启动
     */
    public boolean isStarted() {
        return executorService != null;
    }

    /**
     * 生成一次报告
     */
    protected void report() {
        for (MeasurementRecorder recorder : recorders) {
            logger.info(String.format("%s: %s", recorder.getName(), recorder.getReport()));
            recorder.reset();
        }
    }

}
