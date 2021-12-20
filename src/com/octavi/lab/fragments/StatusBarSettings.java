package com.octavi.lab.fragments;

import com.android.internal.logging.nano.MetricsProto;

import android.os.Bundle;
import android.content.Context;
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
import android.content.pm.PackageManager.NameNotFoundException;
import android.provider.Settings;
import com.android.settings.R;

import com.octavi.support.preference.CustomSeekBarPreference;
import com.octavi.support.preference.SecureSettingSwitchPreference;
import com.octavi.support.preference.SystemSettingSeekBarPreference;

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

    private static final String PREF_BATTERY = "battery";

    private static final String SYSTEMUI_PACKAGE = "com.android.systemui";
    private static final String CONFIG_RESOURCE_NAME = "flag_combined_status_bar_signal_icons";

    private static final String STATUSBAR_LEFT_PADDING = "statusbar_left_padding";
    private static final String STATUSBAR_RIGHT_PADDING = "statusbar_right_padding";
    private static final String COBINED_STATUSBAR_ICONS = "show_combined_status_bar_signal_icons";

    private SystemSettingSeekBarPreference mSbLeftPadding;
    private SystemSettingSeekBarPreference mSbRightPadding;
    private SecureSettingSwitchPreference mCombinedIcons;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.octavi_lab_statusbar);
        final PreferenceScreen prefScreen = getPreferenceScreen();
        final ContentResolver resolver = getActivity().getContentResolver();

        Resources res = null;
        Context ctx = getContext();
        float density = Resources.getSystem().getDisplayMetrics().density;

        try {
            res = ctx.getPackageManager().getResourcesForApplication("com.android.systemui");
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        mSbLeftPadding = (SystemSettingSeekBarPreference) findPreference(STATUSBAR_LEFT_PADDING);
        int sbLeftPadding = Settings.System.getIntForUser(ctx.getContentResolver(),
                Settings.System.LEFT_PADDING, ((int) (res.getIdentifier("com.android.systemui:dimen/status_bar_padding_start", null, null) / density)), UserHandle.USER_CURRENT);
        mSbLeftPadding.setValue(sbLeftPadding);
        mSbLeftPadding.setOnPreferenceChangeListener(this);

        mSbRightPadding = (SystemSettingSeekBarPreference) findPreference(STATUSBAR_RIGHT_PADDING);
        int sbRightPadding = Settings.System.getIntForUser(ctx.getContentResolver(),
                Settings.System.RIGHT_PADDING, ((int) (res.getIdentifier("com.android.systemui:dimen/status_bar_padding_end", null, null) / density)), UserHandle.USER_CURRENT);
        mSbRightPadding.setValue(sbRightPadding);
        mSbRightPadding.setOnPreferenceChangeListener(this);

        mCombinedIcons = (SecureSettingSwitchPreference)
                findPreference(COBINED_STATUSBAR_ICONS);
        Resources sysUIRes = null;
        boolean def = false;
        int resId = 0;
        try {
            sysUIRes = getActivity().getPackageManager()
                    .getResourcesForApplication(SYSTEMUI_PACKAGE);
        } catch (Exception ignored) {
            // If you don't have system UI you have bigger issues
        }
        if (sysUIRes != null) {
            resId = sysUIRes.getIdentifier(
                    CONFIG_RESOURCE_NAME, "bool", SYSTEMUI_PACKAGE);
            if (resId != 0) def = sysUIRes.getBoolean(resId);
        }
        boolean enabled = Settings.Secure.getInt(resolver,
                COBINED_STATUSBAR_ICONS, def ? 1 : 0) == 1;
        mCombinedIcons.setChecked(enabled);
        mCombinedIcons.setOnPreferenceChangeListener(this);

        Preference mBattery = findPreference(PREF_BATTERY);
        if (mBattery != null
                && !getResources().getBoolean(
                        com.android.internal.R.bool.config_intrusiveBatteryLed)) {
            getPreferenceScreen().removePreference(mBattery);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        Resources res = null;
        Context ctx = getContext();
        float density = Resources.getSystem().getDisplayMetrics().density;

        try {
            res = ctx.getPackageManager().getResourcesForApplication("com.android.systemui");
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        if (preference == mSbLeftPadding) {
            int leftValue = (Integer) newValue;
            int sbLeft = ((int) (leftValue / density));
            Settings.System.putIntForUser(getContext().getContentResolver(),
                    Settings.System.LEFT_PADDING, sbLeft, UserHandle.USER_CURRENT);
            return true;
        } else if (preference == mSbRightPadding) {
            int rightValue = (Integer) newValue;
            int sbRight = ((int) (rightValue / density));
            Settings.System.putIntForUser(getContext().getContentResolver(),
                    Settings.System.RIGHT_PADDING, sbRight, UserHandle.USER_CURRENT);
            return true;
        } else if (preference == mCombinedIcons) {
            boolean enabled = (boolean) newValue;
            Settings.Secure.putInt(resolver,
                    COBINED_STATUSBAR_ICONS, enabled ? 1 : 0);
            return true;
        }
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.OCTAVI;
    }

}
