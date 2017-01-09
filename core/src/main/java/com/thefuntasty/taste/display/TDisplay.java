package com.thefuntasty.taste.display;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.thefuntasty.taste.Taste;

public class TDisplay {

	private final Context context;

	public TDisplay(Context context) {
		this.context = context;
	}

	public int getWindowWidth() {
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) Taste.context().getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(metrics);
		return metrics.widthPixels;
	}

	public int getWindowHeight() {
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}
}
