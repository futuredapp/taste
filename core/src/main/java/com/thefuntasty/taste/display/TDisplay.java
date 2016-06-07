package com.thefuntasty.taste.display;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.thefuntasty.taste.Taste;

public class TDisplay {

	private static Integer windowWidth = null;
	private static Integer windowHeight = null;

	public static int getWindowWidth() {
		if (windowWidth == null) {
			updateMetrics();
		}

		return windowWidth;
	}

	public static int getWindowHeight() {
		if (windowHeight == null) {
			updateMetrics();
		}
		return windowHeight;
	}

	private static void updateMetrics() {
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) Taste.context().getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(metrics);
		windowWidth = metrics.widthPixels;
		windowHeight = metrics.heightPixels;
	}
}
