package otcoteam.tahiti.performance.recorder;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * ��ֵ��¼������¼ָ���ƽ��ֵ�����ֵ����Сֵ�Լ���ֵ
 */
public class QuantizedRecorder extends MeasurementRecorder {

    /**
     * ָ����ֵ����
     */
    public enum OutputField {
        /**
         * ƽ��ֵ
         */
        AVERAGE,

        /**
         * ���ֵ
         */
        MAX,

        /**
         * ��Сֵ
         */
        MIN,

        /**
         * ��ֵ
         */
        SUM,
    }

    /**
     * ָ������
     */
    private String name;

    /**
     * ���ڸ�ָ�꣬��Ҫ��¼����ֵ����
     */
    private EnumSet<OutputField> outputFields;

    /**
     * ָ��ֵ�ļ���
     */
    private List<Double> records;

    /**
     * ��ʼ����¼��
     *
     * @param name ָ������
     * @param outputFields ��ֵ����
     */
    public QuantizedRecorder(String name, EnumSet<OutputField> outputFields) {
        this.name = name;
        this.outputFields = outputFields;
        reset();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getReport() {
        double sum = 0, max = Double.NEGATIVE_INFINITY, min = Double.POSITIVE_INFINITY, avg = 0;
        for (double v : records) {
            sum += v;
            if (v > max) max = v;
            if (v < min) min = v;
        }

        if (records.size() == 0) {
            max = Double.NaN;
            min = Double.NaN;
            avg = Double.NaN;
        } else {
            avg = sum / records.size();
        }

        HashMap<String, Double> fields = new HashMap<String, Double>();
        if (outputFields.contains(OutputField.AVERAGE)) fields.put("average", avg);
        if (outputFields.contains(OutputField.SUM)) fields.put("sum", sum);
        if (outputFields.contains(OutputField.MIN)) fields.put("min", min);
        if (outputFields.contains(OutputField.MAX)) fields.put("max", max);

        return fields.toString();
    }

    @Override
    public void reset() {
        records = new LinkedList<Double>();
    }

    /**
     * ��¼��ֵ
     *
     * @param value ��Ҫ��¼����ֵ������ֵ��������ָ��ֵ�ļ�����
     */
    public void record(double value) {
        records.add(value);
    }

}
