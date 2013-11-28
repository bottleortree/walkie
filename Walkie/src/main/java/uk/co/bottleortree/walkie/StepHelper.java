package uk.co.bottleortree.walkie;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

public class StepHelper {

    private StepHelper() {
        throw new AssertionError(); // static class
    }

    public static int stepsFromEvent(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            return Math.round(event.values[0]);
        } else {
            return -1; // Wrong sensor type
        }
    }

}
