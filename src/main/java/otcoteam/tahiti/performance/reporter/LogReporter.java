package otcoteam.tahiti.performance.reporter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import org.slf4j.LoggerFactory;

/**
 * ��װ����־��������
 */
public abstract class LogReporter {

    /**
     * ��־����������
     */
    private LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    /**
     * ������־������
     *
     * @return logger ��־������
     */
    public abstract Logger getLogger();

    /**
     * ������־����������
     *
     * @return context ��־����������
     */
    protected final LoggerContext getLoggerContext() {
        return context;
    }

    /**
     * ������־�ڼ�¼�ĸ�ʽ
     *
     * @return encoder ���ڼ�¼�ĸ�ʽ
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
     * ������־��������Ĭ�ϼ�¼���м������־
     *
     * @return logger ��־������
     */
    protected Logger createLogger() {
        Logger logger = (Logger) LoggerFactory.getLogger(LogReporter.class);
        logger.setLevel(Level.ALL);
        return logger;
    }

}
