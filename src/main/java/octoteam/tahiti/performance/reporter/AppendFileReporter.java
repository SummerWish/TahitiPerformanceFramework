package octoteam.tahiti.performance.reporter;

import ch.qos.logback.core.FileAppender;

/**
 * 以追加文件的方式生成日志记录
 */
public class AppendFileReporter extends LogReporter {

    /**
     * 初始化日志生成器
     *
     * @param fileName 需要追加日志记录的文件名
     * @param pattern  日志输出格式
     */
    public AppendFileReporter(String fileName, String pattern) {
        super(AppendFileReporter.class, pattern);
        FileAppender fileAppender = new FileAppender();
        fileAppender.setContext(getLoggerContext());
        fileAppender.setFile(fileName);
        fileAppender.setEncoder(getEncoder());
        fileAppender.start();

        getLogger().addAppender(fileAppender);
    }

    /**
     * 初始化日志生成器
     *
     * @param fileName 需要追加日志记录的文件名
     */
    public AppendFileReporter(String fileName) {
        this(fileName, null);
    }

}
