package com.iaihussein.popularmovies.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by iaihussein on 12/3/16.
 */

public class CheckInternet {
    /**
     * Checking for all possible Internet providers
     **/
    public static boolean isConnectingToInternet(Context mContext) {
        // check the Internet connection
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConnectivityManager != null) {
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }
}
