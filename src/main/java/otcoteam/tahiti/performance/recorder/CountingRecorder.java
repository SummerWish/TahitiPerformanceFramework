package otcoteam.tahiti.performance.recorder;

/**
 * ���ڶ�ָ��ָ�����
 */
public class CountingRecorder extends MeasurementRecorder {

    /**
     * ָ������
     */
    private String name;

    /**
     * ָ�����
     */
    private long counter;

    /**
     * ��ʼ��������
     *
     * @param name  ָ������
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
     * ����
     */
    public void record() {
        counter++;
    }

}
