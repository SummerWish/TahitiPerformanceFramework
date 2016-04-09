package otcoteam.tahiti.performance.recorder;

/**
 * 用于对指定指标计数
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
     * @param name  指标名称
     */
    public CountingRecorder(String name) {
        this.name = name;
        reset();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getReport() {
        return counter+"";
    }

    @Override
    public void reset() {
        counter = 0;
    }

    /**
     * 计数
     */
    public void record() {
        counter++;
    }

}
