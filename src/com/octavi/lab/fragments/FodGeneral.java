/*
 * Copyright (C) 2021 ShapeShiftOS
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

import android.app.Activity;
import android.content.Context;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemProperties;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import androidx.preference.*;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable;
import com.android.settingslib.search.SearchIndexable;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.android.internal.logging.nano.MetricsProto;

import android.hardware.biometrics.BiometricSourceType;
import android.hardware.fingerprint.FingerprintManager;

import com.octavi.support.preferences.SystemSettingSwitchPreference;
import com.android.internal.util.custom.FodUtils;

import java.util.ArrayList;
import java.util.List;

@SearchIndexable
public class FodGeneral extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener, Indexable {

    private ContentResolver mResolver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fod_general);
        PreferenceScreen prefScreen = getPreferenceScreen();
        mResolver = getActivity().getContentResolver();
        Context mContext = getContext();
        final PackageManager mPm = getActivity().getPackageManager();                    

        boolean enableScreenOffFOD = getContext().getResources().
                getBoolean(R.bool.config_supportScreenOffFod);
        Preference ScreenOffFODPref = (Preference) findPreference("fod_gesture");

        if (!enableScreenOffFOD){
            prefScreen.removePreference(ScreenOffFODPref);
        }

        Preference AnimaTogglePref = (Preference) findPreference("fod_recognizing_animation");
        Preference AnimaListPref = (Preference) findPreference("fod_recognizing_animation_list");            

        if (!com.android.internal.util.octavi.OctaviUtils.isPackageInstalled(mContext,"com.octavi.fod.animations")) {
            prefScreen.removePreference(AnimaTogglePref);
            prefScreen.removePreference(AnimaListPref);                
        }                

    }      

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.OCTAVI;
    }

    public static final SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
        new BaseSearchIndexProvider() {
            @Override
            public List<SearchIndexableResource> getXmlResourcesToIndex(Context context,
                    boolean enabled) {
                final ArrayList<SearchIndexableResource> result = new ArrayList<>();
                final SearchIndexableResource sir = new SearchIndexableResource(context);
                sir.xmlResId = R.xml.fod_general;
                result.add(sir);
                return result;
            }

            @Override
            public List<String> getNonIndexableKeys(Context context) {
                final List<String> keys = super.getNonIndexableKeys(context);
                return keys;
            }
    };
}
