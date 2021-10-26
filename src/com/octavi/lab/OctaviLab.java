package com.octavi.lab;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;

import android.os.Bundle;
import android.content.res.Resources;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.Preference.OnPreferenceChangeListener;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class OctaviLab extends SettingsPreferenceFragment  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.octavi_lab);
        final PreferenceScreen screen = getPreferenceScreen();
        if (screen == null) {
                    return;
                }
        final int count = screen.getPreferenceCount();


        for (int i = 0; i < count; i++) {
                final Preference preference = screen.getPreference(i);
                if (preference == null) {
                    break;
                }

                preference.setLayoutResource(R.layout.cardview);

            }
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.OCTAVI;
    }
}
