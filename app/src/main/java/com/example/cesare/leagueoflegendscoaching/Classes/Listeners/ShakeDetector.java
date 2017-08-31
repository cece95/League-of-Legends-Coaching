package com.example.cesare.leagueoflegendscoaching.Classes.Listeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.cesare.leagueoflegendscoaching.Activities.Home;

/**
 * Created by cesare on 31/08/2017.
 */

public class ShakeDetector implements SensorEventListener {
    public final static int SHAKE_LIMIT = 10;

    private SensorManager mSensorManager;
    private float mAccel = 0.00f;
    private float mAccelCurrent = SensorManager.GRAVITY_EARTH;
    private float mAccelLast = SensorManager.GRAVITY_EARTH;

    private Activity activity;

    public void onShake(){
        Intent intent = new Intent(activity, Home.class);
        activity.startActivity(intent);
        activity.finish();
    }


    public ShakeDetector(Activity a) {
        this.activity =  a;
        mSensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        registerListener();
    }

    public void registerListener() {
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregisterListener() {
        mSensorManager.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent se) {
        float x = se.values[0];
        float y = se.values[1];
        float z = se.values[2];
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt(x*x + y*y + z*z);
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta;
        if(mAccel > SHAKE_LIMIT)
            onShake();
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}