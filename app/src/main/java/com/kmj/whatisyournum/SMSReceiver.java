package com.kmj.whatisyournum;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kmj.whatisyournum.data.Person;
import com.kmj.whatisyournum.fragment.SettingFragment;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SMSReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    String pw = "";
    ArrayList<Person> person;
    Boolean isCheck=false;
    @Override
    public void onReceive(Context context, Intent intent) {
        person = new ArrayList<>();
        loadData(context);
        if (intent.getAction().equals(SMS_RECEIVED)&&isCheck) {
            Bundle bundle = intent.getExtras();

            SmsMessage[] messages = parseSmsMessage(bundle);
            if (messages.length > 0) {

                String sender = messages[0].getOriginatingAddress();
                String contents = messages[0].getMessageBody();
                SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
                pw = pref.getString("pw", "");
                Log.e(pw, contents);
                if (pw.equals(contents)) {
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(sender, null, "번호 알고싶은 사람의 이름 입력", null, null);
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
                }
                else{
                    for(Person p : person){
                        if(p.getName().equals(contents)){
                            try {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(sender, null, p.getPhoneNo(), null, null);
                            } catch (Exception e) {
                                Log.e("error", e.getMessage());
                            }
                        }
                    }
                }


            }

        }
    }


    SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] objs = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];
        for (int i = 0; i < objs.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
            }
        }
        return messages;
    }

    public void loadData(Context context) {
        Gson gson = new Gson();
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        isCheck=pref.getBoolean("switch",false);
        String json = pref.getString("contact", "");
        ArrayList<Person> shareditems;
        shareditems = gson.fromJson(json, new TypeToken<ArrayList<Person>>() {
        }.getType());
        if (shareditems != null) {
            person.addAll(shareditems);
        }
    }
}