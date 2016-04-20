package octoteam.tahiti.performance.recorder;

import octoteam.tahiti.performance.formatter.IReportFormatable;

/**
 * 指标的抽象类, 定义了所有性能指标需要实现的接口
 */
public abstract class MeasurementRecorder<T> {

    /**
     * 报告格式化器
     */
    protected IReportFormatable<T> formatter;

    /**
     * 指标名称
     */
    protected String name;

    /**
     * 构造指标
     *
     * @param name      指标名称
     * @param formatter 报告格式化器
     */
    public MeasurementRecorder(String name, IReportFormatable<T> formatter) {
        this.name = name;
        this.formatter = formatter;
    }

    /**
     * 返回报告格式化器
     *
     * @return 当前的报告格式化器
     */
    public IReportFormatable<T> getFormatter() {
        return formatter;
    }

    /**
     * 返回指标名称
     *
     * @return name 指标名称
     */
    public String getName() {
        return name;
    }

    /**
     * 重置该指标对应的记录
     */
    public abstract void reset();

    /**
     * 返回该指标的记录报告
     *
     * @return 记录报告数据
     */
    protected abstract T getReportData();

    /**
     * 返回该指标的记录报告
     *
     * @return report 记录报告
     */
    public String getReport() {
        return formatter.formatReport(getReportData());
    }

}
