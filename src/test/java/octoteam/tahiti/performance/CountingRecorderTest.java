package octoteam.tahiti.performance;

import com.sun.tools.internal.ws.wsdl.document.jaxws.Exception;
import otcoteam.tahiti.performance.recorder.CountingRecorder;
import otcoteam.tahiti.performance.reporter.LogReporter;
import otcoteam.tahiti.performance.reporter.RollingFileReporter;
import org.junit.Test;
import static org.junit.Assert.*;


public class CountingRecorderTest {
    public CountingRecorder testRecorder = new CountingRecorder("Login times");
    @Test
    public void testgetName(){
        assertEquals("Login times",testRecorder.getName());
    }
    @Test
    public void testgetReport(){
        assertEquals("0",testRecorder.getReport());
    }

    @Test
    public void testRecord(){
        testRecorder.record();
        assertEquals("1",testRecorder.getReport());
    }

    @Test
    public void testRest(){
        testRecorder.reset();
        assertEquals("0",testRecorder.getReport());
    }
}
