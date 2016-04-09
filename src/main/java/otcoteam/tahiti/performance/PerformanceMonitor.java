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
 * ���ڹ������ܼ����������־��¼
 */
public class PerformanceMonitor {

    /**
     * ����״̬
     */
    private boolean started = false;

    /**
     * ��־������
     */
    private Logger logger = null;

    /**
     * ָ���¼���ļ���
     */
    private HashMap<String, MeasurementRecorder> recorders = new HashMap<String, MeasurementRecorder>();

    /**
     * ��ʼ��
     *
     * @param reporter ��װ����־������
     * @param period ������־��ʱ����
     * @param unit ʱ�䵥λ
     */
    public PerformanceMonitor(LogReporter reporter, long period, TimeUnit unit) {
        this(reporter);
        start(period, unit);
    }

    /**
     * ��ʼ��
     *
     * @param reporter ��װ����־������
     */
    public PerformanceMonitor(LogReporter reporter) {
        logger = reporter.getLogger();
    }

    /**
     * ��ʼ���ܼ�Ⲣ��ʱ������־
     *
     * @param period ������־��ʱ����
     * @param unit ʱ�䵥λ
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
     * ���ָ���¼��
     *
     * @param key ���ڱ�ʶָ���¼���Ĺؼ���
     * @param recorder ָ���¼��
     */
    public void addRecorder(String key, MeasurementRecorder recorder) {
        recorders.put(key, recorder);
    }

    /**
     * ��¼ָ����ֵ
     *
     * @param key ���ڱ�ʶָ���¼���Ĺؼ���
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
     * ��¼ָ����ֵ
     *
     * @param key ���ڱ�ʶָ���¼���Ĺؼ���
     * @param value ָ��ֵ
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
     * �Ƿ��Ѿ�����
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * ���ɱ�����־
     */
    protected void report() {
        for (Map.Entry<String, MeasurementRecorder> entry : recorders.entrySet()) {
            logger.info(String.format("%s: %s", entry.getValue().getName(), entry.getValue().getReport()));
            entry.getValue().reset();
        }
    }

}
