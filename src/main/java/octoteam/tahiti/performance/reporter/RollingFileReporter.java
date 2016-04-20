package octoteam.tahiti.performance.reporter;

import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

/**
 * 以分文件的方式生成日志
 */
public class RollingFileReporter extends LogReporter {

    /**
     * 初始化日志生成器
     *
     * @param fileNamePattern 需要分文件写入日志的文件名的格式, 若扩展名是 .zip 或 .gz 则会生成压缩文件
     * @param pattern         日志输出格式
     */
    public RollingFileReporter(String fileNamePattern, String pattern) {
        super(RollingFileReporter.class, pattern);

        RollingFileAppender fileAppender = new RollingFileAppender();
        fileAppender.setContext(getLoggerContext());
        fileAppender.setEncoder(getEncoder());

        TimeBasedRollingPolicy policy = new TimeBasedRollingPolicy();
        policy.setContext(getLoggerContext());
        policy.setParent(fileAppender);
        policy.setFileNamePattern(fileNamePattern);

        fileAppender.setRollingPolicy(policy);

        policy.start();
        fileAppender.start();

        getLogger().addAppender(fileAppender);
    }

    /**
     * 初始化日志生成器
     *
     * @param fileNamePattern 需要分文件写入日志的文件名的格式
     */
    public RollingFileReporter(String fileNamePattern) {
        this(fileNamePattern, null);
    }

}
