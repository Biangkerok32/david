package com.example.david.viewmover;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;


public class MainActivity extends Activity
{
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private CustomImageViewWithAccelerometerListener imgVw1;
    private CustomImageViewWithAccelerometerListener imgVw2;
    private CustomImageViewWithAccelerometerListener imgVw3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        imgVw1 = (CustomImageViewWithAccelerometerListener)findViewById(R.id.image);
        imgVw2 = (CustomImageViewWithAccelerometerListener)findViewById(R.id.image2);
        imgVw3 = (CustomImageViewWithAccelerometerListener)findViewById(R.id.image3);
        senSensorManager.registerListener(imgVw1, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(imgVw2, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(imgVw3, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }
}
