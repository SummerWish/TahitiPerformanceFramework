package octoteam.tahiti.performance.recorder;

import octoteam.tahiti.performance.formatter.DefaultQuantizedRecordFormatter;
import octoteam.tahiti.performance.formatter.IReportFormatable;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 数值型指标, 报告平均值、最大值、最小值以及总值
 */
public class QuantizedRecorder extends MeasurementRecorder<HashMap<String, Double>> {

    /**
     * 报告中支持的字段
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
     * 对于该指标，需要记录的数值类型
     */
    private EnumSet<OutputField> outputFields;

    /**
     * 指标值的集合
     */
    private List<Double> records;

    /**
     * 构造数值型指标
     *
     * @param name         指标名称
     * @param outputFields 报告中要包含的字段
     */
    public QuantizedRecorder(String name, EnumSet<OutputField> outputFields) {
        this(name, outputFields, new DefaultQuantizedRecordFormatter());
    }

    /**
     * 构造数值型指标
     *
     * @param name         指标名称
     * @param outputFields 指标名称
     * @param formatter    报告格式化器
     */
    public QuantizedRecorder(String name, EnumSet<OutputField> outputFields, IReportFormatable<HashMap<String, Double>> formatter) {
        super(name, formatter);
        this.outputFields = outputFields;
        this.formatter = formatter;
        reset();
    }

    /**
     * {@inheritDoc}
     */
    protected HashMap<String, Double> getReportData() {
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
        return fields;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        records = new LinkedList<Double>();
    }

    /**
     * 记录一次数值
     *
     * @param value 需要记录的数值
     */
    public void record(double value) {
        records.add(value);
    }

}
