package com.example.fitvit;
//<<<<<<< HEAD
import android.content.Context;
import android.graphics.Color;
//=======
//
//>>>>>>> a7e84aeef89a558cd4f3c2f32691e9b83c42c7cc
//
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.TextViewCompat;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , SensorEventListener {

    BarChart stepsbarchart;
    SensorManager sensorManager;

    TextView tv_steps;
    Boolean sensor_running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        tv_steps  = findViewById(R.id.tv_steps);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);


        generateTemporaryGraph();



    }

    @Override
    protected void onResume() {
        super.onResume();
        sensor_running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if(countSensor != null) {
            sensorManager.registerListener(this, countSensor, sensorManager.SENSOR_DELAY_UI);
        }   else{
            //sensor not found
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensor_running = false;
        //if we unregister the hardware will stop detecting steps
        //sensorManager.unregisterListener(this);
    }

    //funtion to generate temporary bar graph
    public void generateTemporaryGraph(){

        stepsbarchart = findViewById(R.id.steps_bargraph);

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(0,180f ));
        barEntries.add(new BarEntry(1,730f ));
        barEntries.add(new BarEntry(2,590f ));
        barEntries.add(new BarEntry(3,830f ));
        barEntries.add(new BarEntry(4,640f ));
        barEntries.add(new BarEntry(5,330f ));
        barEntries.add(new BarEntry(6,220f ));
        BarDataSet bardataset = new BarDataSet(barEntries , "Steps");
        String [] dates = new String[]{"01/10/2019","02/10/2019","03/10/2019","04/10/2019","05/10/2019","06/10/2019","07/10/2019"};


        BarData bardata = new BarData(bardataset);
        bardata.setBarWidth(0.9f);
        bardata.setValueTextSize(12);
        int whiteColorValue = Color.WHITE;
        bardataset.setValueTextColor(whiteColorValue);

        stepsbarchart.setData(bardata);
        stepsbarchart.setTouchEnabled(true);
        stepsbarchart.setDragEnabled(true);
        stepsbarchart.setScaleEnabled(true);
        stepsbarchart.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        stepsbarchart.getXAxis().setTextColor(whiteColorValue);
        stepsbarchart.getAxisLeft().setDrawGridLines(false);
        stepsbarchart.getXAxis().setDrawGridLines(false);
        stepsbarchart.getAxisRight().setDrawGridLines(false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
        } else if (id == R.id.nav_bmi) {

        } else if (id == R.id.nav_water_reminder) {

            Intent i=new Intent (MainActivity.this,water_main.class);
            startActivity(i);

        } else if (id == R.id.nav_exercise) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensor_running){
            tv_steps.setText(String.valueOf(sensorEvent.values[0]));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}