package midgardsystem.com.wintowinner.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import midgardsystem.com.wintowinner.R;

/**
 * Created by Gabriel on 22/12/2016.
 */
public class SettingsFragment  extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        //addPreferencesFromResource(R.xml.settings);

        //SwitchPreference switchPreference = (SwitchPreference) findPreference("switch_active");
        //switchPreference.setEnabled(false);
    }
}
