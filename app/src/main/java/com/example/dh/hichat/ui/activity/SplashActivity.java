package com.example.dh.hichat.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.dh.hichat.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //检查权限
        boolean grantExternalRW = isGrantExternalRW(this);
        if (grantExternalRW) {
            enterApp();
        }
    }
    public static boolean isGrantExternalRW(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
            return false;
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length >0 &&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //用户同意授权
                    enterApp();
                }else{
                    //用户拒绝授权
                }
                break;
        }
    }

    private void enterApp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();

            }
        }, 1500);
    }
}
