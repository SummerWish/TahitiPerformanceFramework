package octoteam.tahiti.performance;

import octoteam.tahiti.performance.formatter.IReportFormatable;
import octoteam.tahiti.performance.recorder.CountingRecorder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CountingRecorderTest {

    @Test
    public void testGetName() {
        CountingRecorder recorder = new CountingRecorder("foo");
        assertEquals("foo", recorder.getName());
    }

    @Test
    public void testGetReportAtInitial() {
        CountingRecorder recorder = new CountingRecorder("foo");
        assertEquals("0", recorder.getReport());
    }

    @Test
    public void testGetRecord() {
        CountingRecorder recorder = new CountingRecorder("foo");
        recorder.record();
        assertEquals("1", recorder.getReport());
        recorder.record();
        assertEquals("2", recorder.getReport());
    }

    @Test
    public void testResst() {
        CountingRecorder recorder = new CountingRecorder("foo");
        recorder.record();
        recorder.reset();
        assertEquals("0", recorder.getReport());
    }

    @Test
    public void testCustomFormatter() {
        IReportFormatable<Long> formatter = new IReportFormatable<Long>() {
            public String formatReport(Long data) {
                return "bar";
            }
        };
        CountingRecorder recorder = new CountingRecorder("foo", formatter);
        assertEquals(formatter, recorder.getFormatter());
        assertEquals("bar", recorder.getReport());
    }

}
