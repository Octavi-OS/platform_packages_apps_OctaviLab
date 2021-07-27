package com.octavi.lab.fragments;

import com.android.internal.logging.nano.MetricsProto;

import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.content.ContentResolver;
import android.content.res.Resources;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceCategory;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreference;
import android.provider.Settings;
import com.android.settings.R;

import java.util.Locale;
import android.text.TextUtils;
import android.view.View;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class StatusBarSettings extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String KEY_STATUS_BAR_LOGO = "status_bar_logo";

    private SwitchPreference mShowOctaviLogo;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.octavi_lab_statusbar);

        PreferenceScreen prefSet = getPreferenceScreen();

        mShowOctaviLogo = (SwitchPreference) findPreference(KEY_STATUS_BAR_LOGO);
        mShowOctaviLogo.setChecked((Settings.System.getInt(getContentResolver(),
             Settings.System.STATUS_BAR_LOGO, 0) == 1));
        mShowOctaviLogo.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        if  (preference == mShowOctaviLogo) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.STATUS_BAR_LOGO, value ? 1 : 0);
            return true;
        }
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.OCTAVI;
    }

}
