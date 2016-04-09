package otcoteam.tahiti.performance.reporter;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;

/**
 * ��׷���ļ��ķ�ʽ������־��¼
 */
public class AppendFileReporter extends LogReporter {

    /**
     * ��־������
     */
    protected Logger logger;

    /**
     * ��ʼ����־������
     *
     * @param fileName ��Ҫ׷����־��¼���ļ���
     */
    public AppendFileReporter(String fileName) {
        FileAppender fileAppender = new RollingFileAppender();
        fileAppender.setContext(getLoggerContext());
        fileAppender.setFile(fileName);
        fileAppender.setEncoder(createEncoder());
        fileAppender.start();
        logger = createLogger();
        logger.addAppender(fileAppender);
    }


    @Override
    public Logger getLogger() {
        return logger;
    }

}
