package com.thefuntasty.taste.version;

import android.os.Build;

public class TVersion {
	public static boolean isLollipop() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
	}
}
