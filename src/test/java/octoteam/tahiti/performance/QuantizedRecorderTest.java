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
            QuantizedRecorder.OutputField.AVERAGE.MIN
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
        assertEquals("{average=60.00, min=10.00, max=100.00}",qRecorder.getReport());
    }

    @Test
    public void testReset(){
        qRecorder.reset();
        assertEquals("{average=NaN, min=NaN, max=NaN}",qRecorder.getReport());
    }
}
