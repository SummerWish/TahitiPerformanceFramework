package otcoteam.tahiti.performance;

import otcoteam.tahiti.performance.recorder.MeasurementRecorder;

class DummyMeasurementRecorder extends MeasurementRecorder {

    private String name;

    private String data;

    public DummyMeasurementRecorder(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void reset() {
    }

    public String getReport() {
        return data;
    }

}
