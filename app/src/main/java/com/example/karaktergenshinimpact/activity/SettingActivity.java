package com.example.karaktergenshinimpact.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.example.karaktergenshinimpact.R;

public class SettingActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Load setting fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new MainSettingsFragment()).commit();
    }

    public static class MainSettingsFragment extends PreferenceFragment{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            bindSummaryValue(findPreference("url_web_service"));
        }
    }
    private static void bindSummaryValue(Preference preference){
        preference.setOnPreferenceChangeListener(listener);
        listener.onPreferenceChange(preference,
                PreferenceManager.getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(),""));

    }

    private static Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            String stringValue = o.toString();
            if (preference instanceof EditTextPreference){
                preference.setSummary(stringValue);
            }
            return false;
        }
    };
}