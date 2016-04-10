package otcoteam.tahiti.performance.recorder;

/**
 * 计数型指标, 报告总次数
 */
public class CountingRecorder extends MeasurementRecorder {

    /**
     * 指标名称
     */
    private String name;

    /**
     * 指标计数
     */
    private long counter;

    /**
     * 初始化计数器
     *
     * @param name 指标名称
     */
    public CountingRecorder(String name) {
        this.name = name;
        reset();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getReport() {
        return Long.toString(counter);
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
