package octoteam.tahiti.performance;

import org.junit.Test;
import otcoteam.tahiti.performance.recorder.QuantizedRecorder;

import java.util.EnumSet;
import java.util.Random;
import static org.junit.Assert.*;


public class QuantizedRecorderTest {
    public QuantizedRecorder qRecorder = new QuantizedRecorder("Usage", EnumSet.of(
            QuantizedRecorder.OutputField.AVERAGE,
            QuantizedRecorder.OutputField.MAX,
            QuantizedRecorder.OutputField.MIN
    ));

    @Test
    public void testGetName(){
        assertEquals("Usage",qRecorder.getName());
    }

    @Test
    public void testRecord(){
        qRecorder.record(10);
        qRecorder.record(100);
        qRecorder.record(70);
        assertTrue(qRecorder.getReport().contains("average=60.00"));
        assertTrue(qRecorder.getReport().contains("min=10"));
        assertTrue(qRecorder.getReport().contains("max=100"));
    }

    @Test
    public void testReset(){
        qRecorder.reset();
        assertTrue(qRecorder.getReport().contains("average=NaN"));
        assertTrue(qRecorder.getReport().contains("min=NaN"));
        assertTrue(qRecorder.getReport().contains("max=NaN"));
    }
}
