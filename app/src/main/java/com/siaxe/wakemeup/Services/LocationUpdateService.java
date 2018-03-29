package com.siaxe.wakemeup.Services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by sudesh on 3/19/2018.
 */

public class LocationUpdateService extends Service {

    int i;
    Calendar c = Calendar.getInstance();
    String TAG = "TagService-->";
    String TAGONE = "TAG SERVICE_EXTRA----->";
    SharedPreferences.Editor editor;
    private String token = "";
    private LocationManager locationManager;
    private android.location.LocationListener myLocationListener;
    private Location driverLocation;
    private boolean isFirstTime = true;
    private Handler handler;
    private boolean isRunning = true;




    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAGONE, "onCreate:");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAGONE, "onStartCommand: ");

        getCurrentLocation();
        return START_STICKY;
    }

    public void getCurrentLocation() {

        String serviceString = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(serviceString);

        myLocationListener = new android.location.LocationListener() {

            public void onLocationChanged(Location locationListener) {

                Log.d(TAGONE, "onLocationChanged: " + locationListener.getLatitude() + "--> || -->" + locationListener.getLongitude());
               // Log.d(TAGONE, "lat: " + locationListener.getLatitude() + "   ||   lng --> " + locationListener.getLongitude()+"   ||   time --> "+getCurrentTime(System.currentTimeMillis()));
                driverLocation = locationListener;

                writefileError("lat: " + locationListener.getLatitude() + "   ||   lng --> " + locationListener.getLongitude()+"   ||   time --> "+getCurrentTime());


            }

            public void onProviderDisabled(String provider) {

            }

            public void onProviderEnabled(String provider) {
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            Log.d(TAGONE, "getCurrentLocation: permission");
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, myLocationListener);

    }

    @Override
    public void onDestroy() {

        isRunning = false;
        Log.d(TAGONE, "onDestroy: location stopped");


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.removeUpdates(myLocationListener);
        myLocationListener = null;

        super.onDestroy();
    }

    public void writeToFile(String data) {

        SharedPreferences.Editor editor;
        try {
            editor = getSharedPreferences("user_token", MODE_PRIVATE).edit();
            editor.putString("token", data);
            editor.commit();

        } catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void writefile(String str) {
        try {
            java.io.File file = new java.io.File(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    + "/Jlog.txt");



            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(str);
            writer.newLine();
            writer.flush();
            writer.close();

        } catch (IOException e) {
            System.out.println("writefile ex " + e);
            e.printStackTrace();
        }

    }

    public void writefileError(String str) {
        try {
            java.io.File fileError = new java.io.File(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    + "/WMUlogError.txt");



            BufferedWriter writer = new BufferedWriter(new FileWriter(fileError, true));
            writer.write(str);
            writer.newLine();
            writer.flush();
            writer.close();

        } catch (IOException e) {
            System.out.println("writefileError ex " + e);
            e.printStackTrace();
        }

    }

    public boolean isGPSEnabled() {
        try{
            final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                return false;
            }else{
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return true;
        }


    }

    public String getCurrentTime(){

        String date = c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DAY_OF_MONTH);
        String time = c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);

        return  (date + " " + time);
    }


}

