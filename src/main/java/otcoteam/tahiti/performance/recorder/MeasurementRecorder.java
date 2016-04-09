package otcoteam.tahiti.performance.recorder;

/**
 * 所有用来记录指标性能的类都应该继承这个类
 */
public abstract class MeasurementRecorder {

    /**
     * 返回指标名称
     *
     * @return name 指标名称
     */
    public abstract String getName();

    /**
     * 重置该指标对应的记录
     */
    public abstract void reset();

    /**
     * 返回该指标的记录报告
     *
     * @return report 记录报告
     */
    public abstract String getReport();

}
