package octoteam.tahiti.performance;

import octoteam.tahiti.performance.recorder.CountingRecorder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RecordMarkerTest {

    @Test
    public void testGetName() {
        RecordMarker marker = new RecordMarker(new CountingRecorder("foo"));
        assertEquals("foo", marker.getName());
        assertEquals("foo", marker.toString());
    }

}