/*
 * Copyright (C) 2014 TeamEos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.octavi.lab.fragments;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import androidx.preference.ListPreference;
import androidx.preference.SwitchPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;

import com.android.settings.SettingsPreferenceFragment;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.R;

import com.octavi.lab.preferences.SystemSettingSwitchPreference;
import com.octavi.lab.preferences.SecureSettingSwitchPreference;

public class NavbarSettings extends SettingsPreferenceFragment implements OnPreferenceChangeListener {

    private static final String PIXEL_ANIMATION_NAVIGATION = "pixel_nav_animation";
    private static final String INVERT_NAVIGATION = "sysui_nav_bar_inverse";

    private SystemSettingSwitchPreference mPixelAnimationNavigation;
    private SecureSettingSwitchPreference mInvertNavigation;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.octavi_lab_navigation);

        mPixelAnimationNavigation = findPreference(PIXEL_ANIMATION_NAVIGATION);
        mInvertNavigation = findPreference(INVERT_NAVIGATION);
        // On three button nav
        if (com.android.internal.util.octavi.OctaviUtils.isThemeEnabled("com.android.internal.systemui.navbar.threebutton")) {
            mPixelAnimationNavigation.setSummary(getString(R.string.pixel_navbar_anim_summary));
            mInvertNavigation.setSummary(getString(R.string.navigation_bar_invert_layout_summary));
        // On two button nav
        } else if (com.android.internal.util.octavi.OctaviUtils.isThemeEnabled("com.android.internal.systemui.navbar.twobutton")) {
            mPixelAnimationNavigation.setSummary(getString(R.string.pixel_navbar_anim_summary));
            mInvertNavigation.setSummary(getString(R.string.navigation_bar_invert_layout_summary));
        // On gesture nav
        } else {
            mPixelAnimationNavigation.setSummary(getString(R.string.unsupported_gestures));
            mInvertNavigation.setSummary(getString(R.string.unsupported_gestures));
            mPixelAnimationNavigation.setEnabled(false);
            mInvertNavigation.setEnabled(false);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.OCTAVI;
    }
}
