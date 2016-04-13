package octoteam.tahiti.performance.reporter;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import org.slf4j.LoggerFactory;

/**
 * 日志生成器的抽象类, 定义了所有日志生成器都要实现的接口
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
    public abstract org.slf4j.Logger getLogger();

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
     * 创建日志生成器
     *
     * @param clazz 日志记录对象所处的类
     * @return 日志生成器
     */
    protected Logger createLogger(Class<?> clazz) {
        Logger logger = (Logger) LoggerFactory.getLogger(clazz);
        logger.setAdditive(false);
        return logger;
    }

}
