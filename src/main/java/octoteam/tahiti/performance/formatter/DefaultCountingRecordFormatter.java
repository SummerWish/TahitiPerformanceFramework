package octoteam.tahiti.performance.formatter;

/**
 * CountingRecorder 的默认报告格式化器
 */
public class DefaultCountingRecordFormatter implements IReportFormatable<Long> {
    /**
     * {@inheritDoc}
     */
    public String formatReport(Long data) {
        return data.toString();
    }
}
