package otcoteam.tahiti.performance.recorder;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO
 */
public class QuantizedRecorder extends MeasurementRecorder {

    /**
     * TODO
     */
    public enum OutputField {
        /**
         * TODO
         */
        AVERAGE,

        /**
         * TODO
         */
        MAX,

        /**
         * TODO
         */
        MIN,

        /**
         * TODO
         */
        SUM,
    }

    /**
     * TODO
     */
    private String name;

    /**
     * TODO
     */
    private EnumSet<OutputField> outputFields;

    /**
     * TODO
     */
    private List<Double> records;

    /**
     * TODO
     *
     * @param name
     * @param outputFields
     */
    public QuantizedRecorder(String name, EnumSet<OutputField> outputFields) {
        this.name = name;
        this.outputFields = outputFields;
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

    /**
     * TODO
     */
    public void reset() {
        records = new LinkedList<Double>();
    }

    /**
     * TODO
     *
     * @param value
     */
    public void record(double value) {
        records.add(value);
    }

}
