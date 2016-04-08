package otcoteam.tahiti.performance.reporter;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

/**
 * TODO
 */
public class RollingFileReporter extends LogReporter {

    /**
     * TODO
     */
    protected Logger logger;

    /**
     * TODO
     *
     * @param fileNamePattern
     */
    public RollingFileReporter(String fileNamePattern) {
        RollingFileAppender fileAppender = new RollingFileAppender();
        fileAppender.setContext(getLoggerContext());
        fileAppender.setEncoder(createEncoder());
        fileAppender.start();

        TimeBasedRollingPolicy policy = new TimeBasedRollingPolicy();
        policy.setContext(getLoggerContext());
        policy.setParent(fileAppender);
        policy.setFileNamePattern(fileNamePattern);
        policy.start();

        logger = createLogger();
        logger.addAppender(fileAppender);
    }

    /**
     * TODO
     *
     * @return
     */
    @Override
    public Logger getLogger() {
        return logger;
    }

}
