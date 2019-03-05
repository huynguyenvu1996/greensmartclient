package com.group07.greensmart.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.group07.greensmart.R;

/**
 * Created by nguyenvuhuy on 4/13/18.
 */

public class NetworkUtils {

    public static boolean haveNetworkConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static void showSnackbarAlertNetwork(View view, final Context context) {
        Snackbar snackbar = Snackbar.make(view, R.string.network_no_conection, Snackbar.LENGTH_LONG)
                .setAction(R.string.network_title_setting, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                });
        snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.colorPrimary)).setDuration(10000);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        snackbar.show();
    }

}