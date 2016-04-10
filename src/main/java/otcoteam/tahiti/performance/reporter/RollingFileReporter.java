package otcoteam.tahiti.performance.reporter;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

/**
 * 以分文件的方式生成日志
 */
public class RollingFileReporter extends LogReporter {

    /**
     * 日志生成器
     */
    protected Logger logger;

    /**
     * 初始化日志生成器
     *
     * @param fileNamePattern 日志文件名称的格式
     */
    public RollingFileReporter(String fileNamePattern) {
        RollingFileAppender fileAppender = new RollingFileAppender();
        fileAppender.setContext(getLoggerContext());
        fileAppender.setEncoder(createEncoder());

        TimeBasedRollingPolicy policy = new TimeBasedRollingPolicy();
        policy.setContext(getLoggerContext());
        policy.setParent(fileAppender);
        policy.setFileNamePattern(fileNamePattern);

        fileAppender.setRollingPolicy(policy);

        policy.start();
        fileAppender.start();

        logger = createLogger(RollingFileReporter.class);
        logger.addAppender(fileAppender);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

}
