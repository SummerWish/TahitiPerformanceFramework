package otcoteam.tahiti.performance.recorder;

/**
 * TODO
 */
public class CountingRecorder extends MeasurementRecorder {

    /**
     * TODO
     */
    private String name;

    /**
     * TODO
     */
    private long counter;

    /**
     * TODO
     *
     * @param name
     */
    public CountingRecorder(String name) {
        this.name = name;
        reset();
    }

    /**
     * TODO
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * TODO
     *
     * @return
     */
    public String getReport() {
        return counter+"";
    }

    /**
     * TODO
     */
    public void reset() {
        counter = 0;
    }

    /**
     * TODO
     */
    public void record() {
        counter++;
    }

}
