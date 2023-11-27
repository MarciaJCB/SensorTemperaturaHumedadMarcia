package com.example.ppt15sensortemperaturahumedad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView temperaturaTextView; //Vistas de textos en las que se mostrará los valores
    private TextView humedadTextView;
    private SensorManager sensorManager; //Instancia que se utilizará para acceder a los sensores del dispositivo
    private Sensor temperaturaSensor; //Objetos tipo sensor que representa los sensores de temperatura y humedad
    private Sensor humedadSensor;
    private Boolean temperaturaDisponible; //Indican los sensores de temperatura y humedad estan disponibles
    private Boolean humedadDisponible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperaturaTextView = findViewById(R.id.Temp);
        humedadTextView = findViewById(R.id.Humed);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Verifica la disponibilidad de los sensores
        temperaturaDisponible = false;
        humedadDisponible = false;

        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            temperaturaSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            temperaturaDisponible = true;
        }else{
            temperaturaTextView.setText("El sensor de temperatura no está diponible");
        }
    }

    //Detecta un cambio de valores de los sensores
    //Verifica el tipo de sensor actualiza los TextView correspondientes con los valores de temperatura y humedad
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
            temperaturaTextView.setText(sensorEvent.values[0] + " °C");
        }else if (sensorEvent.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
            humedadTextView.setText(sensorEvent.values[0] + " %");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    //Se registran los sensores de temperatura  humedad si estan diponibles
    @Override
    protected void onResume(){
        super.onResume();
        if(temperaturaDisponible){
            sensorManager.registerListener(this,temperaturaSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(humedadDisponible){
            sensorManager.registerListener(this, humedadSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    //Se anulan los registros de los sensores para evitar el consumo innecesario de energia cuando la actividad no está en primer plano
    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}