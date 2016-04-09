package otcoteam.tahiti.performance.recorder;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 数值记录器，记录指标的平均值、最大值、最小值以及总值
 */
public class QuantizedRecorder extends MeasurementRecorder {

    /**
     * 指标数值类型
     */
    public enum OutputField {
        /**
         * 平均值
         */
        AVERAGE,

        /**
         * 最大值
         */
        MAX,

        /**
         * 最小值
         */
        MIN,

        /**
         * 总值
         */
        SUM,
    }

    /**
     * 指标名称
     */
    private String name;

    /**
     * 对于该指标，需要记录的数值类型
     */
    private EnumSet<OutputField> outputFields;

    /**
     * 指标值的集合
     */
    private List<Double> records;

    /**
     * 初始化记录器
     *
     * @param name         指标名称
     * @param outputFields 数值类型
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

        HashMap<String, String> fields = new HashMap<String, String>();
        if (outputFields.contains(OutputField.AVERAGE)) fields.put("average", String.format("%.2f", avg));
        if (outputFields.contains(OutputField.SUM)) fields.put("sum", String.format("%.2f", sum));
        if (outputFields.contains(OutputField.MIN)) fields.put("min", String.format("%.2f", min));
        if (outputFields.contains(OutputField.MAX)) fields.put("max", String.format("%.2f", max));

        return fields.toString();
    }

    @Override
    public void reset() {
        records = new LinkedList<Double>();
    }

    /**
     * 记录数值
     *
     * @param value 需要记录的数值，此数值将被加入指标值的集合中
     */
    public void record(double value) {
        records.add(value);
    }

}
