package com.kmj.whatisyournum.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kmj.whatisyournum.R;

import java.util.concurrent.locks.Lock;

public class LockSetActivity extends AppCompatActivity implements View.OnClickListener {

    Button p0, p1, p2, p3, p4, p5, p6, p7, p8, p9;
    ImageButton pDel, pCheck;
    TextView t1, t2, t3, t4;

    static int pwLen = 0;
    String pw = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_set);
        init();
    }

    void init() {
        p0 = findViewById(R.id.lockset_p0);
        p1 = findViewById(R.id.lockset_p1);
        p2 = findViewById(R.id.lockset_p2);
        p3 = findViewById(R.id.lockset_p3);
        p4 = findViewById(R.id.lockset_p4);
        p5 = findViewById(R.id.lockset_p5);
        p6 = findViewById(R.id.lockset_p6);
        p7 = findViewById(R.id.lockset_p7);
        p8 = findViewById(R.id.lockset_p8);
        p9 = findViewById(R.id.lockset_p9);
        pDel = findViewById(R.id.lockset_del);
        pCheck = findViewById(R.id.lockset_check);

        t1 = findViewById(R.id.lockset_t1);
        t2 = findViewById(R.id.lockset_t2);
        t3 = findViewById(R.id.lockset_t3);
        t4 = findViewById(R.id.lockset_t4);

        p0.setOnClickListener(this);
        p1.setOnClickListener(this);
        p2.setOnClickListener(this);
        p3.setOnClickListener(this);
        p4.setOnClickListener(this);
        p5.setOnClickListener(this);
        p6.setOnClickListener(this);
        p7.setOnClickListener(this);
        p8.setOnClickListener(this);
        p9.setOnClickListener(this);

        pDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pwLen--;
                if (pwLen>=0)
                    pw = pw.substring(0, pwLen);
                changeTextView();
                if(pwLen<=0){
                    pwLen=0;
                }


            }
        });
        pCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pwLen==4){
                    SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("pw", pw);
                    editor.putBoolean("switch",false);
                    editor.commit();

                    Intent intent=new Intent(LockSetActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(LockSetActivity.this, "4자리를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    void changeTextView() {
        switch (pwLen) {
            case 0:
                t1.setText("");
                t2.setText("");
                t3.setText("");
                t4.setText("");
                break;
            case 1:
                t1.setText("*");
                t2.setText("");
                t3.setText("");
                t4.setText("");
                break;
            case 2:
                t1.setText("*");
                t2.setText("*");
                t3.setText("");
                t4.setText("");
                break;
            case 3:
                t1.setText("*");
                t2.setText("*");
                t3.setText("*");
                t4.setText("");
                break;
            case 4:
                t1.setText("*");
                t2.setText("*");
                t3.setText("*");
                t4.setText("*");
                break;

        }
    }

    @Override
    public void onClick(View v) {
        if (pwLen < 4) {
            switch (v.getId()) {
                case R.id.lockset_p0:
                    pwLen++;
                    pw += "0";
                    changeTextView();
                    break;
                case R.id.lockset_p1:
                    pwLen++;
                    pw += "1";
                    changeTextView();
                    break;
                case R.id.lockset_p2:
                    pwLen++;
                    pw += "2";
                    changeTextView();
                    break;
                case R.id.lockset_p3:
                    pwLen++;
                    pw += "3";
                    changeTextView();
                    break;
                case R.id.lockset_p4:
                    pwLen++;
                    pw += "4";
                    changeTextView();
                    break;
                case R.id.lockset_p5:
                    pwLen++;
                    pw += "5";
                    changeTextView();
                    break;
                case R.id.lockset_p6:
                    pwLen++;
                    pw += "6";
                    changeTextView();
                    break;
                case R.id.lockset_p7:
                    pwLen++;
                    pw += "7";
                    changeTextView();
                    break;
                case R.id.lockset_p8:
                    pwLen++;
                    pw += "8";
                    changeTextView();
                    break;
                case R.id.lockset_p9:
                    pwLen++;
                    pw += "9";
                    changeTextView();
                    break;

            }
        } else {
            Toast.makeText(this, "4자리를 모두 입력하였습니다.", Toast.LENGTH_SHORT).show();
        }

    }
}
