package com.group07.greensmart.activity.settings;

import android.annotation.TargetApi;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.group07.greensmart.R;
import com.group07.greensmart.rest.DefaultSharedPrefsUtils;

import de.psdev.licensesdialog.LicensesDialog;


/**
 * Created by huyjackson on 8/19/17.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SettingsPreferenceFragment extends PreferenceFragment {

    private static final String TAG = SettingsPreferenceFragment.class.getSimpleName();
    protected Location mLastLocation;
    private EditTextPreference serverEditTextPreference;
    private FusedLocationProviderClient mFusedLocationClient;
    private Preference locationPref;
    private Preference openSourcePref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        //            setHasOptionsMenu(true);

        // Bind the summaries of EditText/List/Dialog/Ringtone preferences
        // to their values. When their values change, their summaries are
        // updated to reflect the new value, per the Android Design
        // guidelines.

        serverEditTextPreference = (EditTextPreference) findPreference("server");


        serverEditTextPreference.setSummary(DefaultSharedPrefsUtils.getServer(getActivity()));
        serverEditTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                String server = o.toString();
                DefaultSharedPrefsUtils.setServer(getActivity(), server);
                serverEditTextPreference.setSummary(server);
                return true;
            }
        });

        locationPref = findPreference("location");
        locationPref.setSummary(getActivity().getString(
                R.string.settings_summary_location,
                String.valueOf(DefaultSharedPrefsUtils.getLocationLat(getActivity())),
                String.valueOf(DefaultSharedPrefsUtils.getLocationLng(getActivity()))));

        locationPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(final Preference preference) {
                getLastLocation();
                return true;
            }
        });

        openSourcePref = findPreference("open_source_licenses");
        openSourcePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                new LicensesDialog.Builder(getActivity())
                        .setNotices(R.raw.open_source_licenses)
                        .setTitle("Open Source Licenses")
                        .build()
                        .show();
                return true;
            }
        });


    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            Toast.makeText(getActivity(), "L: " + mLastLocation.getLatitude() + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                            locationPref.setSummary(getActivity().getString(R.string.settings_summary_location, String.valueOf(mLastLocation.getLatitude()), String.valueOf(mLastLocation.getLongitude())));
                            DefaultSharedPrefsUtils.setLocationLat(getActivity(), String.valueOf(mLastLocation.getLatitude()));
                            DefaultSharedPrefsUtils.setLocationLng(getActivity(), String.valueOf(mLastLocation.getLongitude()));
                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                        }
                    }
                });
    }


}
