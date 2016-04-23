package octoteam.tahiti.performance.reporter;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;

/**
 * 基于日志输出的报告器
 */
public class LogReporter {

    /**
     * 日志生成器环境
     */
    private LoggerContext context = new LoggerContext();

    /**
     * 日志生成器
     */
    private Logger logger;

    /**
     * 输出格式
     */
    private String pattern = "%d - %marker: %msg%n";

    /**
     * 构造基于日志的报告器
     *
     * @param clazz   日志所属类
     * @param pattern 日志输出格式
     */
    public LogReporter(Class<?> clazz, String pattern) {
        if (pattern != null) {
            this.pattern = pattern;
        }
        logger = context.getLogger(clazz);
        logger.setAdditive(false);
    }

    /**
     * 构造基于现有日志的报告器
     *
     * @param logger 日志实例
     */
    public LogReporter(Logger logger) {
        this.logger = logger;
    }

    /**
     * 返回日志生成器
     *
     * @return logger 日志生成器
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * 返回日志生成器环境
     *
     * @return context 日志生成器环境
     */
    protected final LoggerContext getLoggerContext() {
        return context;
    }

    /**
     * 日志编码器
     */
    protected PatternLayoutEncoder encoder = null;

    /**
     * 获得日志编码器
     *
     * @return encoder 用于记录的格式
     */
    protected PatternLayoutEncoder getEncoder() {
        if (encoder != null) {
            return encoder;
        }
        encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setImmediateFlush(true);
        encoder.setPattern(pattern);
        encoder.start();
        return encoder;
    }

}
