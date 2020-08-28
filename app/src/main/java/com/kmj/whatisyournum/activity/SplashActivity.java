package com.kmj.whatisyournum.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import com.kmj.whatisyournum.R;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SplashActivity extends AppCompatActivity {
    final public static int MY_PERMISSIONS_REQUEST = 100;
    final public static int AFTER_SETTING = 1000;
    boolean isPwset=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Logger.addLogAdapter(new AndroidLogAdapter());

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        if(pref.getString("pw", "")==""){
            isPwset=false;
        }
        else{
            isPwset=true;
        }



        new Handler().postDelayed(new Runnable() { //앱이 시작하자마자 권한때문에 꺼지지 않게 딜레이
            @Override
            public void run() {
                ActivityCompat.requestPermissions(SplashActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS,Manifest.permission.RECEIVE_SMS},
                        MY_PERMISSIONS_REQUEST); // 처음부터 requestPermission() 실행하지 않고 권한 따로 요청 보내는 이유는
                // requestPermission()에 shouldShowRequestPermissionRationale()메서드가 최초실행시 항상 False 반환
            }
        }, 1000);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    if (isPwset){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 1500);
                    }
                    else{
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(SplashActivity.this, LockSetActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 1500);
                    }

                } else {
                    requestPermission();
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AFTER_SETTING:
                requestPermission();
        }
    }

    void requestPermission() {
        if (ContextCompat.checkSelfPermission(SplashActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,
                    Manifest.permission.READ_CONTACTS)) { //can notify
                Toast.makeText(this, "주소록 권한 허용하셔야 앱 사용 가능합니다.", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(SplashActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST);

            } else { //always denied

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                .setData(Uri.parse("package:" + getPackageName()));
                        Toast.makeText(SplashActivity.this, "권한 -> 주소록 허용을 하셔야 앱사용 가능합니다.", Toast.LENGTH_SHORT).show();
                        startActivityForResult(intent, AFTER_SETTING);
                    }
                }, 1500);
            }

        } else { //read contact request granted

            if (ContextCompat.checkSelfPermission(SplashActivity.this,
                    Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,
                        Manifest.permission.SEND_SMS)) { //can notify
                    Toast.makeText(this, "메세지 권한 허용하셔야 앱 사용 가능합니다.", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(SplashActivity.this,
                            new String[]{Manifest.permission.SEND_SMS,Manifest.permission.RECEIVE_SMS},
                            MY_PERMISSIONS_REQUEST);

                } else { //always denied

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    .setData(Uri.parse("package:" + getPackageName()));
                            Toast.makeText(SplashActivity.this, "권한 -> 메시지 허용을 하셔야 앱사용 가능합니다.", Toast.LENGTH_SHORT).show();
                            startActivityForResult(intent, AFTER_SETTING);
                        }
                    }, 1500);
                }



            }
            else{
                if (isPwset){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1500);
                }
                else{
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SplashActivity.this, LockSetActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1500);
                }
            }
        }


    }
}