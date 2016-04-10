package otcoteam.tahiti.performance.reporter;

import ch.qos.logback.core.FileAppender;
import org.slf4j.Logger;

/**
 * 以追加文件的方式生成日志记录
 */
public class AppendFileReporter extends LogReporter {

    /**
     * 日志生成器
     */
    protected Logger logger;

    /**
     * 初始化日志生成器
     *
     * @param fileName 需要追加日志记录的文件名
     */
    public AppendFileReporter(String fileName) {
        FileAppender fileAppender = new FileAppender();
        fileAppender.setContext(getLoggerContext());
        fileAppender.setFile(fileName);
        fileAppender.setEncoder(createEncoder());
        fileAppender.start();
        logger = createLogger();
        ((ch.qos.logback.classic.Logger) logger).addAppender(fileAppender);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

}
