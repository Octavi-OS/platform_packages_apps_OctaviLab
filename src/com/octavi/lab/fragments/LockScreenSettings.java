/*
 *  Copyright (C) 2015 The OmniROM Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.octavi.lab.fragments;

import com.android.internal.logging.nano.MetricsProto;

import android.app.Activity;
import android.content.Context;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemProperties;
import androidx.preference.SwitchPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import com.octavi.lab.preferences.SystemSettingSwitchPreference;
import com.octavi.lab.preferences.SystemSettingListPreference;
import com.octavi.lab.preferences.CustomSeekBarPreference;
import com.android.internal.util.custom.FodUtils;
import com.android.settings.widget.CardPreference;

import android.provider.Settings;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.octavi.support.preferences.SecureSettingSwitchPreference;
import com.octavi.support.preferences.SystemSettingSwitchPreference;
import com.octavi.support.preferences.SystemSettingSeekBarPreference;
import com.octavi.support.preferences.CustomSeekBarPreference;
import com.octavi.support.colorpicker.ColorPickerPreference;
import com.android.internal.util.octavi.OctaviUtils;

public class LockScreenSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String LOCKSCREEN_MAX_NOTIF_CONFIG = "lockscreen_max_notif_cofig";
    private static final String LOCKSCREEN_FOD_CATEGORY = "lockscreen_fod_category";
    private static final String AMBIENT_ICONS_COLOR = "ambient_icons_color";
    private static final String AMBIENT_ICONS_LOCKSCREEN = "ambient_icons_lockscreen";

    private CustomSeekBarPreference mMaxKeyguardNotifConfig;
    private ColorPickerPreference mAmbientIconsColor;
    private SystemSettingSwitchPreference mAmbientIconsLockscreen;

    private ContentResolver mResolver;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.octavi_lab_lockscreen);
        PreferenceScreen prefScreen = getPreferenceScreen();
        mResolver = getActivity().getContentResolver();
        Resources resources = getResources();
        Context mContext = getContext();
        ContentResolver resolver = getActivity().getContentResolver();
        final PackageManager mPm = getActivity().getPackageManager();

        CardPreference mLockscreenFod = findPreference("lockscreen_fod_category");
        if (!getResources().getBoolean(com.android.internal.R.bool.config_supportsInDisplayFingerprint)) {
                    getPreferenceScreen().removePreference(mLockscreenFod);
        } else {
            mLockscreenFod = (CardPreference) findPreference(LOCKSCREEN_FOD_CATEGORY);
        }

        mMaxKeyguardNotifConfig = (CustomSeekBarPreference) findPreference(LOCKSCREEN_MAX_NOTIF_CONFIG);
        int kgconf = Settings.System.getInt(getContentResolver(),
                Settings.System.LOCKSCREEN_MAX_NOTIF_CONFIG, 3);
        mMaxKeyguardNotifConfig.setValue(kgconf);
        mMaxKeyguardNotifConfig.setOnPreferenceChangeListener(this);

        mAmbientIconsLockscreen = (SystemSettingSwitchPreference) findPreference(AMBIENT_ICONS_LOCKSCREEN);
        mAmbientIconsLockscreen.setChecked((Settings.System.getInt(resolver,
                Settings.System.AMBIENT_ICONS_LOCKSCREEN, 0) == 1));
        mAmbientIconsLockscreen.setOnPreferenceChangeListener(this);

        // Ambient Icons Color
        mAmbientIconsColor = (ColorPickerPreference) findPreference(AMBIENT_ICONS_COLOR);
        int intColor = Settings.System.getInt(getContentResolver(),
                Settings.System.AMBIENT_ICONS_COLOR, Color.WHITE);
        String hexColor = String.format("#%08x", (0xffffffff & intColor));
        mAmbientIconsColor.setNewPreviewColor(intColor);
        mAmbientIconsColor.setSummary(hexColor);
        mAmbientIconsColor.setOnPreferenceChangeListener(this);
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        final String key = preference.getKey();
        if (preference == mMaxKeyguardNotifConfig) {
            int kgconf = (Integer) newValue;
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.LOCKSCREEN_MAX_NOTIF_CONFIG, kgconf);
            return true;
        } else if (preference == mAmbientIconsLockscreen) {
            boolean value = (Boolean) newValue;
            Settings.System.putInt(resolver,
                    Settings.System.AMBIENT_ICONS_LOCKSCREEN, value ? 1 : 0);
            return true;
        } else if (preference == mAmbientIconsColor) {
            String hex = ColorPickerPreference.convertToARGB(Integer
                .parseInt(String.valueOf(newValue)));
            mAmbientIconsColor.setSummary(hex);
            int intHex = ColorPickerPreference.convertToColorInt(hex);
            Settings.System.putInt(resolver,
                    Settings.System.AMBIENT_ICONS_COLOR, intHex);
            return true;
        }
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.OCTAVI;
    }

}
