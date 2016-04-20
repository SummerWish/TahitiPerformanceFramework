package octoteam.tahiti.performance.recorder;

import octoteam.tahiti.performance.formatter.DefaultCountingRecordFormatter;
import octoteam.tahiti.performance.formatter.IReportFormatable;

/**
 * 计数型指标, 报告总次数
 */
public class CountingRecorder extends MeasurementRecorder<Long> {

    /**
     * 指标计数
     */
    private long counter;

    /**
     * 构造计数型型指标
     *
     * @param name      指标名称
     * @param formatter 报告格式化器
     */
    public CountingRecorder(String name, IReportFormatable<Long> formatter) {
        super(name, formatter);
        reset();
    }

    /**
     * 构造计数型型指标
     *
     * @param name 指标名称
     */
    public CountingRecorder(String name) {
        this(name, new DefaultCountingRecordFormatter());
    }

    /**
     * {@inheritDoc}
     */
    protected Long getReportData() {
        return counter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        counter = 0;
    }

    /**
     * 记录一次计数
     */
    public void record() {
        counter++;
    }

}
