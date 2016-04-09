package otcoteam.tahiti.performance.recorder;

/**
 * ����������¼ָ�����ܵ��඼Ӧ�ü̳������
 */
public abstract class MeasurementRecorder {

    /**
     * ����ָ������
     *
     * @return name ָ������
     */
    public abstract String getName();

    /**
     * ���ø�ָ���Ӧ�ļ�¼
     */
    public abstract void reset();

    /**
     * ���ظ�ָ��ļ�¼����
     *
     * @return report ��¼����
     */
    public abstract String getReport();

}
