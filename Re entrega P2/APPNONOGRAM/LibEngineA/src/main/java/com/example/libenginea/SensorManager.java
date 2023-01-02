package com.example.libenginea;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;


public class SensorManager implements SensorEventListener {
    private EngineA engineA;
    private Context context;

    public SensorManager(EngineA engineA){
        this.engineA = engineA;
        this.context = this.engineA.getContext();
        android.hardware.SensorManager sensorManager=(android.hardware.SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, sensor, android.hardware.SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float valorGiroscopio = 0;
        if(this.context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            valorGiroscopio = sensorEvent.values[1]; // EN EL EJE Y
        else
            valorGiroscopio = sensorEvent.values[0]; // EN EL EJE X

        if(valorGiroscopio>3.0){
            int p = this.engineA.getStats().getPaleta()-1;
            if(p >= 0 && this.engineA.getStats().isPaletaUnlock(p))
                this.engineA.getStats().setPaleta(p);
        }
        else if(valorGiroscopio<-3.0){
            int p = this.engineA.getStats().getPaleta()+1;
            if(p <= 3 && this.engineA.getStats().isPaletaUnlock(p))
                this.engineA.getStats().setPaleta(p);
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
