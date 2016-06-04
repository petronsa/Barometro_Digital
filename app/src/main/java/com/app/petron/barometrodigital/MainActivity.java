package com.app.petron.barometrodigital;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView barometrotext;
    private StartAppAd startAppAd = new StartAppAd(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppSDK.init(this, "101423750", "205476544", true);
        setContentView(R.layout.activity_main);
        barometrotext = (TextView) findViewById(R.id.barometrotext);
        SensorManager snsMgr = (SensorManager) getSystemService(Service.SENSOR_SERVICE);

        Sensor pS = snsMgr.getDefaultSensor(Sensor.TYPE_PRESSURE);

        snsMgr.registerListener(this, pS, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onResume() {

        super.onResume();
        SensorManager snsMgr = (SensorManager) getSystemService(Service.SENSOR_SERVICE);

        Sensor pS = snsMgr.getDefaultSensor(Sensor.TYPE_PRESSURE);

        snsMgr.registerListener(this, pS, SensorManager.SENSOR_DELAY_UI);
        startAppAd.onResume();
    }


    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        String redondeo;
        redondeo = String.format("%.1f", values[0]);
        barometrotext.setText(redondeo);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        //poner en marcha la publicidad al salir
        MainActivity.this.startAppAd.showAd();
        MainActivity.this.startAppAd.loadAd();

    }

    @Override
    public void onPause() {
        super.onPause();
        startAppAd.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.votar:
                //botón votar
                Uri uriUrl = Uri.parse("https://play.google.com/store/apps/details?id=company.petron.termometro_digital");
                Intent intent = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(intent);
                return true;
            case R.id.salir:
                //botón salir
                MainActivity.this.startAppAd.showAd();
                MainActivity.this.startAppAd.loadAd();

                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
