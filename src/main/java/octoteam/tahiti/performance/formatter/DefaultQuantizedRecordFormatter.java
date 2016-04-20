package octoteam.tahiti.performance.formatter;

import java.util.HashMap;
import java.util.Map;

/**
 * QuantizedRecorder 的默认报告格式化器
 */
public class DefaultQuantizedRecordFormatter implements IReportFormatable<HashMap<String, Double>> {
    /**
     * {@inheritDoc}
     */
    public String formatReport(HashMap<String, Double> data) {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }
            builder.append(String.format("%s=%.2f", entry.getKey(), entry.getValue()));
        }
        return builder.toString();
    }
}
