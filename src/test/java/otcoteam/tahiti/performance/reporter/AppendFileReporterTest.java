package otcoteam.tahiti.performance.reporter;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.FileAppender;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class AppendFileReporterTest {

    @Test
    public void testAppendFileReporter() {
        String tempFile = System.getProperty("java.io.tmpdir") + Double.toString(System.nanoTime()) + ".log";
        LogReporter reporter = new AppendFileReporter(tempFile);

        Logger logger = (Logger) reporter.getLogger();
        assertNotNull(logger);

        Iterator<Appender<ILoggingEvent>> iterator = logger.iteratorForAppenders();
        assertTrue(iterator.hasNext());

        Appender appender = iterator.next();
        assertTrue(appender instanceof FileAppender);

        FileAppender fileAppender = (FileAppender) appender;
        assertEquals(tempFile, fileAppender.getFile());

        appender.stop();
    }

}