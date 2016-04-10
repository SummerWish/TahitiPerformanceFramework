package otcoteam.tahiti.performance.recorder;

/**
 * 指标的抽象类, 定义了所有性能指标需要实现的接口
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
