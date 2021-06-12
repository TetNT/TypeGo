package com.example.typego.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class InternetConnection {
    // checks if either wi-fi or cellular data is available
    public static boolean isAvailable(Context context) {
        boolean wifiConnected = false;
        boolean mobileConnected = false;
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network[] networks = cm.getAllNetworks();
        for (Network network: networks
             ) {
            NetworkInfo ni = cm.getNetworkInfo(network);
            if (ni.getTypeName().equalsIgnoreCase("wifi")) {
                if (ni.isConnected()) wifiConnected = true;
            }
            if (ni.getTypeName().equalsIgnoreCase("mobile")) {
                if (ni.isConnected()) mobileConnected = true;
            }
        }
        return wifiConnected || mobileConnected;
    }

}
