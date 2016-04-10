package octoteam.tahiti.performance;

import org.junit.Test;
import otcoteam.tahiti.performance.recorder.CountingRecorder;

import static org.junit.Assert.assertEquals;


public class CountingRecorderTest {
    public CountingRecorder testRecorder = new CountingRecorder("Login times");

    @Test
    public void testgetName() {
        assertEquals("Login times", testRecorder.getName());
    }

    @Test
    public void testgetReport() {
        assertEquals("0", testRecorder.getReport());
    }

    @Test
    public void testRecord() {
        testRecorder.record();
        assertEquals("1", testRecorder.getReport());
    }

    @Test
    public void testRest() {
        testRecorder.reset();
        assertEquals("0", testRecorder.getReport());
    }
}
