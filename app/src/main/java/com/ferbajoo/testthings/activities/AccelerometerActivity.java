package com.ferbajoo.testthings.activities;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.ferbajoo.annotation.Foo;
import com.ferbajoo.testthings.R;
import com.ferbajoo.testthings.Utilities;

/**
 * Created by
 * feuribe on 16/11/2017. https://es.androids.help/q19328
 */
@Foo(name = "AccelerometerActivity", value = "Uso de accelerometro del dispositivo", drawable = R.drawable.accelemeter)
public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener{


    private SensorManager sensorManager;
    private Sensor proximitySensor, gyroscopeSensor, rotationVectorSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accelerometer_activity);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = findViewById(R.id.toolbar);
        Utilities.getToolbar(this, toolbar,getString(R.string.accelerometer_tittle));


        sensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null){
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

            if (proximitySensor == null){
                Toast.makeText(this, "No esta disponible en sensor de aproximidad", Toast.LENGTH_SHORT).show();
                return;
            }
            if (gyroscopeSensor == null){
                Toast.makeText(this, "No esta disponible en sensor giroscopio", Toast.LENGTH_SHORT).show();
            }
            if (rotationVectorSensor == null){
                Toast.makeText(this, "No esta disponible en sensor rotation", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,proximitySensor, 2 * 1000 * 1000);
        sensorManager.registerListener(this, gyroscopeSensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, rotationVectorSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, proximitySensor);
        sensorManager.unregisterListener(this, gyroscopeSensor);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor == proximitySensor){
            if(sensorEvent.values[0] < proximitySensor.getMaximumRange()) {
                // Detected something nearby
                getWindow().getDecorView().setBackgroundColor(Color.RED);
            } else {
                // Nothing is nearby
                getWindow().getDecorView().setBackgroundColor(Color.GREEN);
            }
        }else  if (sensorEvent.sensor == gyroscopeSensor){

        }else {
            float[] rotationMatrix = new float[16];
            SensorManager.getRotationMatrixFromVector(
                    rotationMatrix, sensorEvent.values);
// Remap coordinate system
            float[] remappedRotationMatrix = new float[16];
            SensorManager.remapCoordinateSystem(rotationMatrix,
                    SensorManager.AXIS_X,
                    SensorManager.AXIS_Z,
                    remappedRotationMatrix);

// Convert to orientations
            float[] orientations = new float[3];
            SensorManager.getOrientation(remappedRotationMatrix, orientations);

            for(int i = 0; i < 3; i++) {
                orientations[i] = (float)(Math.toDegrees(orientations[i]));
            }

            if(orientations[2] > 45) {
                getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
            } else if(orientations[2] < -45) {
                getWindow().getDecorView().setBackgroundColor(Color.BLUE);
            } else if(Math.abs(orientations[2]) < 10) {
                getWindow().getDecorView().setBackgroundColor(Color.WHITE);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
