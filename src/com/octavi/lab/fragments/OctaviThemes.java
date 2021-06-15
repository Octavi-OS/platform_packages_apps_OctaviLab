package com.octavi.lab.fragments;

import com.android.internal.logging.nano.MetricsProto;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.om.IOverlayManager;
import android.content.om.OverlayInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.ServiceManager;
import android.os.UserHandle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.Fragment;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.SwitchPreference;
import android.provider.Settings;
import com.android.settings.R;

import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.display.CustomOverlayPreferenceController;
import com.android.settings.display.OverlayCategoryPreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.List;

import com.android.settings.SettingsPreferenceFragment;
import com.octavi.support.colorpicker.ColorPickerPreference;

public class OctaviThemes extends DashboardFragment implements
        OnPreferenceChangeListener {

    private static final String TAG = "OctaviThemes";

    private Context mContext;
    private IOverlayManager mOverlayManager;

    private static final String SWITCH_STYLE = "switch_style";
    private static final String PREF_RGB_ACCENT_PICKER = "rgb_accent_picker";

    private ColorPickerPreference rgbAccentPicker;
    private ListPreference mSwitchStyle;

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.octavi_themes;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        //addPreferencesFromResource(R.xml.octavi_themes);

        mSwitchStyle = (ListPreference) findPreference(SWITCH_STYLE);
        int switchStyle = Settings.System.getInt(getContext().getContentResolver(),
                Settings.System.SWITCH_STYLE, 1);
        int valueIndex = mSwitchStyle.findIndexOfValue(String.valueOf(switchStyle));
        mSwitchStyle.setValueIndex(valueIndex >= 0 ? valueIndex : 0);
        mSwitchStyle.setSummary(mSwitchStyle.getEntry());
        mSwitchStyle.setOnPreferenceChangeListener(this);

        rgbAccentPicker = (ColorPickerPreference) findPreference(PREF_RGB_ACCENT_PICKER);
        String colorVal = Settings.Secure.getStringForUser(mContext.getContentResolver(),
                Settings.Secure.ACCENT_COLOR, UserHandle.USER_CURRENT);
        int color = (colorVal == null)
                ? Color.WHITE
                : Color.parseColor("#" + colorVal);
        rgbAccentPicker.setNewPreviewColor(color);
        rgbAccentPicker.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        if (preference == mSwitchStyle) {
                String value = (String) objValue;
                Settings.System.putInt(getContext().getContentResolver(), Settings.System.SWITCH_STYLE, Integer.valueOf(value));
                int valueIndex = mSwitchStyle.findIndexOfValue(value);
                mSwitchStyle.setSummary(mSwitchStyle.getEntries()[valueIndex]);
	} else if (preference == rgbAccentPicker) {
            int color = (Integer) objValue;
            String hexColor = String.format("%08X", (0xFFFFFFFF & color));
            Settings.Secure.putStringForUser(mContext.getContentResolver(),
                        Settings.Secure.ACCENT_COLOR,
                        hexColor, UserHandle.USER_CURRENT);
            try {
                 mOverlayManager.reloadAssets("com.android.settings", UserHandle.USER_CURRENT);
                 mOverlayManager.reloadAssets("com.android.systemui", UserHandle.USER_CURRENT);
             } catch (RemoteException ignored) {
             }
            return true;
        }
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.OCTAVI;
    }

    @Override
    protected List<AbstractPreferenceController> createPreferenceControllers(Context context) {
        return buildPreferenceControllers(context, getSettingsLifecycle(), this);
    }

    private static List<AbstractPreferenceController> buildPreferenceControllers(
            Context context, Lifecycle lifecycle, Fragment fragment) {
        final List<AbstractPreferenceController> controllers = new ArrayList<>();
        controllers.add(new OverlayCategoryPreferenceController(context,
                "android.theme.customization.font"));
        controllers.add(new OverlayCategoryPreferenceController(context,
                "android.theme.customization.adaptive_icon_shape"));
        controllers.add(new OverlayCategoryPreferenceController(context,
                "android.theme.customization.icon_pack.android"));
        controllers.add(new CustomOverlayPreferenceController(context,
		"android.theme.customization.custom_overlays"));
        return controllers;
    }
}
