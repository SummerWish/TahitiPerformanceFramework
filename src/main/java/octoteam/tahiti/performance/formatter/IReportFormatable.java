package octoteam.tahiti.performance.formatter;

/**
 * 可输出报告接口
 *
 * @param <T> 报告数据类型
 */
public interface IReportFormatable<T> {
    /**
     * 格式化结构化的报告数据为字符串
     *
     * @param data 报告数据
     * @return 报告字符串
     */
    String formatReport(T data);
}
