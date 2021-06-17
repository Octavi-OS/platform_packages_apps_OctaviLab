/*
 * Copyright (C) 2016 The Pure Nexus Project
 * used for Nitrogen OS
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

package com.octavi.lab;

import com.android.internal.logging.nano.MetricsProto;

import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Surface;
import android.preference.Preference;
import com.android.settings.R;

import com.android.settings.SettingsPreferenceFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.ComponentName;
import android.widget.ImageView;

import com.octavi.lab.preferences.Utils;
import com.octavi.lab.fragments.StatusBarSettings;
import com.octavi.lab.fragments.LockScreenSettings;
import com.octavi.lab.fragments.PowermenuSettings;
import com.octavi.lab.fragments.GamingPref;
import com.octavi.lab.fragments.GestureSettings;
import com.octavi.lab.fragments.NotificationSettings;
import com.octavi.lab.fragments.ButtonSettings;
import com.octavi.lab.fragments.MiscSettings;
import com.octavi.lab.fragments.ButtonSettings;
import com.octavi.lab.fragments.QuickSettings;
import com.octavi.lab.fragments.VolumeRockerSettings;
import com.octavi.lab.fragments.LockscreenWeather;
import com.octavi.lab.fragments.Visualiser;
import com.octavi.lab.fragments.OctaviThemes;

public class OctaviLab extends SettingsPreferenceFragment implements View.OnClickListener {

  ImageView mQuickSettingsCard;
  ImageView mStatusbarSettingsCard;
  ImageView mLockScreenSettingsCard;
  ImageView mPowerSettingsCard;
  ImageView mGamingModeCard;
  ImageView mGestureSettingsCard;
  ImageView mNotificationSettingsCard;
  ImageView mButtonSettingsCard;
  ImageView mNavigationSettingsCard;
  ImageView mMiscSettingsCard;
  ImageView mVolumeCard;
  ImageView mVisualiserCard;
  ImageView mLockWeatherCard;
  ImageView mThemeCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.octavilab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mQuickSettingsCard = (ImageView) view.findViewById(R.id.quicksettings_card);
        mQuickSettingsCard.setOnClickListener(this);

        mStatusbarSettingsCard = (ImageView) view.findViewById(R.id.statusbarsettings_card);
        mStatusbarSettingsCard.setOnClickListener(this);

        mLockScreenSettingsCard = (ImageView) view.findViewById(R.id.lockscreensettings_card);
        mLockScreenSettingsCard.setOnClickListener(this);

        mPowerSettingsCard = (ImageView) view.findViewById(R.id.powersettings_card);
        mPowerSettingsCard.setOnClickListener(this);

        mGestureSettingsCard = (ImageView) view.findViewById(R.id.gesturesettings_card);
        mGestureSettingsCard.setOnClickListener(this);

        mVolumeCard = (ImageView) view.findViewById(R.id.volume_card);
        mVolumeCard.setOnClickListener(this);

        mNotificationSettingsCard = (ImageView) view.findViewById(R.id.notificationsettings_card);
        mNotificationSettingsCard.setOnClickListener(this);

        mVisualiserCard = (ImageView) view.findViewById(R.id.visual_card);
        mVisualiserCard.setOnClickListener(this);

        mThemeCard = (ImageView) view.findViewById(R.id.theme_card);
        mThemeCard.setOnClickListener(this);

        mGamingModeCard = (ImageView) view.findViewById(R.id.gaming_card);
        mGamingModeCard.setOnClickListener(this);

        mNavigationSettingsCard = (ImageView) view.findViewById(R.id.navigationsettings_card);
        mNavigationSettingsCard.setOnClickListener(this);

        mMiscSettingsCard = (ImageView) view.findViewById(R.id.miscsettings_card);
        mMiscSettingsCard.setOnClickListener(this);

        mLockWeatherCard = (ImageView) view.findViewById(R.id.weather_card);
        mLockWeatherCard.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
            if (id == R.id.quicksettings_card)
              {
                QuickSettings quicksettingsfragment = new QuickSettings();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(this.getId(), quicksettingsfragment);
                transaction.addToBackStack(null);
                transaction.commit();
               }
            if (id == R.id.statusbarsettings_card)
              {
                StatusBarSettings statusbarsettingsfragment = new StatusBarSettings();
                FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                transaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction1.replace(this.getId(), statusbarsettingsfragment);
                transaction1.addToBackStack(null);
                transaction1.commit();
              }
            if (id == R.id.lockscreensettings_card)
              {
                LockScreenSettings lockscreensettingsfragment = new LockScreenSettings();
                FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                transaction2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction2.replace(this.getId(), lockscreensettingsfragment);
                transaction2.addToBackStack(null);
                transaction2.commit();
               }
            if (id == R.id.powersettings_card)
              {
                PowermenuSettings powersettingsfragment = new PowermenuSettings();
                FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
                transaction3.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction3.replace(this.getId(), powersettingsfragment);
                transaction3.addToBackStack(null);
                transaction3.commit();
               }
            if (id == R.id.gesturesettings_card)
              {
                GestureSettings gesturesettingsfragment = new GestureSettings();
                FragmentTransaction transaction4 = getFragmentManager().beginTransaction();
                transaction4.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction4.replace(this.getId(), gesturesettingsfragment);
                transaction4.addToBackStack(null);
                transaction4.commit();
              }
           if (id == R.id.volume_card)
              {
                VolumeRockerSettings volumefragment = new VolumeRockerSettings();
                FragmentTransaction transaction5 = getFragmentManager().beginTransaction();
                transaction5.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction5.replace(this.getId(), volumefragment);
                transaction5.addToBackStack(null);
                transaction5.commit();
               }
            if (id == R.id.gaming_card)
              {
                GamingPref gamingfragment = new GamingPref();
                FragmentTransaction transaction7 = getFragmentManager().beginTransaction();
                transaction7.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction7.replace(this.getId(), gamingfragment);
                transaction7.addToBackStack(null);
                transaction7.commit();
               }
            if (id == R.id.miscsettings_card)
              {
                MiscSettings miscsettingsfragment = new MiscSettings();
                FragmentTransaction transaction8 = getFragmentManager().beginTransaction();
                transaction8.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction8.replace(this.getId(), miscsettingsfragment);
                transaction8.addToBackStack(null);
                transaction8.commit();
               }
           if (id == R.id.navigationsettings_card)
             {
               ButtonSettings navigationsettingsfragment = new ButtonSettings();
               FragmentTransaction transaction9 = getFragmentManager().beginTransaction();
               transaction9.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
               transaction9.replace(this.getId(), navigationsettingsfragment);
               transaction9.addToBackStack(null);
               transaction9.commit();
              }
           if (id == R.id.visual_card)
             {
               Visualiser visualfragment = new Visualiser();
               FragmentTransaction transaction9 = getFragmentManager().beginTransaction();
               transaction9.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
               transaction9.replace(this.getId(), visualfragment);
               transaction9.addToBackStack(null);
               transaction9.commit();
              }
           if (id == R.id.notificationsettings_card)
             {
               NotificationSettings notifragment = new NotificationSettings();
               FragmentTransaction transaction9 = getFragmentManager().beginTransaction();
               transaction9.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
               transaction9.replace(this.getId(), notifragment);
               transaction9.addToBackStack(null);
               transaction9.commit();
              }
           if (id == R.id.theme_card)
             {
               OctaviThemes themefragment = new OctaviThemes();
               FragmentTransaction transaction9 = getFragmentManager().beginTransaction();
               transaction9.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
               transaction9.replace(this.getId(), themefragment);
               transaction9.addToBackStack(null);
               transaction9.commit();
              }
           if (id == R.id.weather_card)
             {
               LockscreenWeather weatherfragment = new LockscreenWeather();
               FragmentTransaction transaction9 = getFragmentManager().beginTransaction();
               transaction9.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
               transaction9.replace(this.getId(), weatherfragment);
               transaction9.addToBackStack(null);
               transaction9.commit();
              }
        }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.OCTAVI;
    }

    public static void lockCurrentOrientation(Activity activity) {
        int currentRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int orientation = activity.getResources().getConfiguration().orientation;
        int frozenRotation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
        switch (currentRotation) {
            case Surface.ROTATION_0:
                frozenRotation = orientation == Configuration.ORIENTATION_LANDSCAPE
                        ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                break;
            case Surface.ROTATION_90:
                frozenRotation = orientation == Configuration.ORIENTATION_PORTRAIT
                        ? ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                        : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                break;
            case Surface.ROTATION_180:
                frozenRotation = orientation == Configuration.ORIENTATION_LANDSCAPE
                        ? ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                        : ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                break;
            case Surface.ROTATION_270:
                frozenRotation = orientation == Configuration.ORIENTATION_PORTRAIT
                        ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        : ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                break;
        }
        activity.setRequestedOrientation(frozenRotation);
    }
}
