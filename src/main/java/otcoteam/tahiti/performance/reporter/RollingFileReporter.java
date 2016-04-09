package otcoteam.tahiti.performance.reporter;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

/**
 * �Է��ļ��ķ�ʽ������־
 */
public class RollingFileReporter extends LogReporter {

    /**
     * ��־������
     */
    protected Logger logger;

    /**
     * ��ʼ����־������
     *
     * @param fileNamePattern ��־�ļ����Ƶĸ�ʽ
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

        logger = createLogger();
        logger.addAppender(fileAppender);
    }


    @Override
    public Logger getLogger() {
        return logger;
    }

}
