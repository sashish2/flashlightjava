package com.starkcreativity.flightjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.starkcreativity.flightjava.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Boolean isFlashLightOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        getPermission();
//        turnFlashLightOnOrOf();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getPermission();

        turnFlashLightOnOrOf();
    }

    private void getPermission() {
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            String[] permissions = {Manifest.permission.CAMERA};
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, 0);
            }else{
                ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
            }
        } else{
            turnFlashLightOnOrOf();
        }
    }

    private void turnFlashLightOnOrOf() {
        binding.btn.setOnClickListener(new View.OnClickListener() {
            final CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            @Override
            public void onClick(View v) {
                if (isFlashLightOn){

                    try{
                        String cameraId = cameraManager.getCameraIdList()[0];
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            cameraManager.setTorchMode(cameraId, false);
                        }
                        isFlashLightOn = false;
                        binding.btn.setText(R.string.turnedoff);
                    }catch (CameraAccessException e){
                        Log.d("Camera Error", e.getMessage());
                    }
                }else{

                    try{
                        String cameraId = cameraManager.getCameraIdList()[0];
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            cameraManager.setTorchMode(cameraId, true);
                        }
                        isFlashLightOn = true;
                        binding.btn.setText(R.string.turnedon);
//                        binding.btn.setBackgroundColo;
                    }catch (CameraAccessException e){
                        Log.d("Camera Error", e.getMessage());
                    }
                }
            }
        });
    }
}