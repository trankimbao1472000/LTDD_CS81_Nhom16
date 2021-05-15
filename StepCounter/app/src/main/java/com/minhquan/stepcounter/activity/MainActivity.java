package com.minhquan.stepcounter.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import  androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


import com.minhquan.stepcounter.R;
import com.minhquan.stepcounter.utils.GetKeyUtils;
import com.minhquan.stepcounter.utils.SharedPreferencesUtils;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    LinearLayout ml;
    TextView txtStep,txtTest;
    TextView textView2;
    Button btReset,btSave;
    SensorManager sensorManager;
    boolean running = false;
    boolean start = true;
    float step;
    SharedPreferencesUtils share;
    float temp;
    float stepD;
    Sensor countSensor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView2 = findViewById(R.id.textView2);
        ml = findViewById(R.id.ml);
        btReset = findViewById(R.id.btReset);
        btSave = findViewById(R.id.btSave);
        txtStep = findViewById(R.id.txtStep);
        //Load_setting();
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        try {
            Load_setting();
        }catch (NullPointerException exception){
            System.out.println(exception.getMessage());
        }
        initialize();

    }

    private void Load_setting() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        boolean chk_night = sp.getBoolean("NIGHT", false);
        if (chk_night) {
            ml.setBackgroundColor(Color.parseColor("#222222"));
            textView2.setTextColor(Color.parseColor("#ffffff"));
            txtStep.setTextColor(Color.parseColor("#ffffff"));

        } else {
            ml.setBackgroundColor(Color.parseColor("#ffffff"));
            textView2.setTextColor(Color.parseColor("#000000"));
            txtStep.setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                Intent i = new Intent(this,Preference.class);
                startActivity(i);
                return true;
            case R.id.item2:
                Intent b = new Intent(this,FAQ.class);
                startActivity(b);
                return true;
            case R.id.item3:
                Intent a = new Intent(this,History.class);
                startActivity(a);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initialize() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            txtStep.setText(String.valueOf(0));
            countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        } else {
            Toast.makeText(this, "Sensor not found !", Toast.LENGTH_SHORT).show();
        }
        share = new SharedPreferencesUtils(this);
        txtTest = findViewById(R.id.txtTest);
    }

    @Override
    protected void onResume() {
        try {
            Load_setting();
        }catch (NullPointerException exception){
            System.out.println(exception.getMessage());
        }
        super.onResume();
        running = true;


        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            Toast.makeText(this, "Sensor not found !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            stepD = (int) (stepD + event.values[0]);
            txtStep.setText(String.valueOf(stepD - temp));
        }

        //Reset button
        btReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                txtStep.setText(String.valueOf(0));
                temp = stepD;
            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void save(View view) {
        String key = GetKeyUtils.getKey();
        //share.setParam(key,step);
        txtTest.setText(key);
        share.setParam(key,stepD);
        Toast.makeText(this, "Save successfully!!", Toast.LENGTH_SHORT).show();
    }


}