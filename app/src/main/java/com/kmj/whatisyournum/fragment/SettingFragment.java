package com.kmj.whatisyournum.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.kmj.whatisyournum.R;

import androidx.annotation.Nullable;

import static android.content.Context.MODE_PRIVATE;

public class SettingFragment extends PreferenceFragment {

    public static SharedPreferences pref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.setting);


        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        pref.registerOnSharedPreferenceChangeListener(listener);


    }
    SharedPreferences.OnSharedPreferenceChangeListener listener=new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            switch (key){
                case "message":
                    SharedPreferences pref = getContext().getSharedPreferences("pref", MODE_PRIVATE);
                    Boolean sw = pref.getBoolean("switch", false);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("switch", !sw);
                    editor.commit();
                    break;
            }
        }
    };
}
