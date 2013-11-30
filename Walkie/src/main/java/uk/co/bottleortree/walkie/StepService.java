package uk.co.bottleortree.walkie;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by Todd on 30/11/13.
 */
public class StepService extends Service implements SensorEventListener{

    private static final int BATCH_MAX_LATENCY = 1000 * 60 * 1; // One minute

    private final IBinder binder = new StepBinder();

    private SensorManager sensorManager;
    private int stepCount = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Step service started", Toast.LENGTH_SHORT).show();
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER), sensorManager.SENSOR_DELAY_NORMAL, BATCH_MAX_LATENCY);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Step service stopped", Toast.LENGTH_SHORT).show();
        sensorManager.unregisterListener(this);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "Step service bound", Toast.LENGTH_SHORT).show();
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "Step service unbound", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        Toast.makeText(this, "Step service rebound", Toast.LENGTH_SHORT).show();
        super.onRebind(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        stepCount = StepHelper.stepsFromEvent(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {} // Not needed for step counters

    public int getStepCount() {
        return stepCount;
    }

    public class StepBinder extends Binder {
        StepService getService() {
            return StepService.this;
        }
    }
}
