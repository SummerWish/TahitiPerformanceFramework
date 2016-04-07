package otcoteam.tahiti.performance.reporter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import org.slf4j.LoggerFactory;

/**
 * TODO
 */
public abstract class LogReporter {

    /**
     * TODO
     */
    private LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    /**
     * TODO
     *
     * @return
     */
    public abstract Logger getLogger();

    /**
     * TODO
     *
     * @return
     */
    protected final LoggerContext getLoggerContext() {
        return context;
    }

    /**
     * TODO
     *
     * @return
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
     * TODO
     *
     * @return
     */
    protected Logger createLogger() {
        Logger logger = (Logger) LoggerFactory.getLogger(LogReporter.class);
        logger.setLevel(Level.ALL);
        return logger;
    }

}
