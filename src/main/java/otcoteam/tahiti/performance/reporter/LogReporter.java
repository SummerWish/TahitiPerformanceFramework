package otcoteam.tahiti.performance.reporter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import org.slf4j.LoggerFactory;

/**
 * 封装了日志生成器，用于记录并生成性能日志
 */
public abstract class LogReporter {

    /**
     * 日志生成器环境
     */
    private LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    /**
     * 返回日志生成器
     *
     * @return logger 日志生成器
     */
    public abstract Logger getLogger();

    /**
     * 返回日志生成器环境
     *
     * @return context 日志生成器环境
     */
    protected final LoggerContext getLoggerContext() {
        return context;
    }

    /**
     * 创建日志内记录的格式
     *
     * @return encoder 用于记录的格式
     */
    protected PatternLayoutEncoder createEncoder() {
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setImmediateFlush(true);
        encoder.setPattern("%d %-5level - %msg%n");
        encoder.start();
        return encoder;
    }

    /**
     * 创建日志生成器，默认记录所有级别的日志
     *
     * @return logger 日志生成器
     */
    protected Logger createLogger() {
        Logger logger = (Logger) LoggerFactory.getLogger(LogReporter.class);
        logger.setLevel(Level.ALL);
        return logger;
    }

}
