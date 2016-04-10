package octoteam.tahiti.performance;

import org.junit.Test;
import otcoteam.tahiti.performance.recorder.QuantizedRecorder;

import java.util.EnumSet;

import static org.junit.Assert.*;


public class QuantizedRecorderTest {

    @Test
    public void testGetName() {
        QuantizedRecorder recorder = new QuantizedRecorder("foo", EnumSet.of(
                QuantizedRecorder.OutputField.AVERAGE,
                QuantizedRecorder.OutputField.MAX,
                QuantizedRecorder.OutputField.MIN
        ));
        assertEquals("foo", recorder.getName());
    }

    @Test
    public void testGetReportAtInitial() {
        QuantizedRecorder recorder = new QuantizedRecorder("foo", EnumSet.of(
                QuantizedRecorder.OutputField.MIN
        ));
        assertTrue(recorder.getReport().contains("min=NaN"));
        assertFalse(recorder.getReport().contains("max="));
        assertFalse(recorder.getReport().contains("average="));
        assertFalse(recorder.getReport().contains("sum="));
    }

    @Test
    public void testGetRecord() {
        QuantizedRecorder recorder = new QuantizedRecorder("foo", EnumSet.of(
                QuantizedRecorder.OutputField.AVERAGE,
                QuantizedRecorder.OutputField.MAX,
                QuantizedRecorder.OutputField.MIN,
                QuantizedRecorder.OutputField.SUM
        ));
        recorder.record(10);
        recorder.record(100);
        recorder.record(70);
        assertTrue(recorder.getReport().contains("average=60.00"));
        assertTrue(recorder.getReport().contains("min=10"));
        assertTrue(recorder.getReport().contains("max=100"));
        assertTrue(recorder.getReport().contains("sum=180"));
    }

    @Test
    public void testReset() {
        QuantizedRecorder recorder = new QuantizedRecorder("foo", EnumSet.of(
                QuantizedRecorder.OutputField.MAX
        ));
        recorder.record(10);
        recorder.reset();
        assertTrue(recorder.getReport().contains("max=NaN"));
    }

}
