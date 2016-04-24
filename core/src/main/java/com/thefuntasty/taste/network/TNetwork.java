package com.thefuntasty.taste.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.thefuntasty.taste.Taste;

public class TNetwork {
	public static boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) Taste.context().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
	}
}
