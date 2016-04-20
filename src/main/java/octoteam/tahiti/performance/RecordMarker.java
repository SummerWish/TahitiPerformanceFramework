package octoteam.tahiti.performance;

import octoteam.tahiti.performance.recorder.MeasurementRecorder;
import org.slf4j.Marker;

import java.util.Iterator;

public class RecordMarker implements Marker {

    private String name;

    public RecordMarker(MeasurementRecorder recorder) {
        name = recorder.getName();
    }

    public String getName() {
        return name;
    }

    public void add(Marker reference) {
    }

    public boolean remove(Marker reference) {
        return false;
    }

    public boolean hasChildren() {
        return false;
    }

    public boolean hasReferences() {
        return false;
    }

    public Iterator<Marker> iterator() {
        return null;
    }

    public boolean contains(Marker other) {
        return false;
    }

    public boolean contains(String name) {
        return false;
    }

    public boolean equals(Object o) {
        return false;
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return name;
    }

}
