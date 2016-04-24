package com.thefuntasty.taste.permission;

import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.thefuntasty.taste.Taste;

public class TPermission {

	public static boolean haveAny(@NonNull String... permissions) {
		for (String permission : permissions) {
			if (has(permission)) {
				return true;
			}
		}
		return false;
	}

	public static boolean haveAll(@NonNull String... permissions) {
		for (String permission : permissions) {
			if (!has(permission)) {
				return false;
			}
		}
		return true;
	}

	private static boolean has(@NonNull String permission) {
		return new ContextWrapper(Taste.context()).checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
	}
}
