package com.thefuntasty.taste;

import android.text.TextUtils;

public class TasteTestingConfig {

	private static String PACKAGE_NAME;
	private static int LAUNCH_TIMEOUT = 10000;
	private static int VIEW_TIMEOUT = 5000;
	private static int SCREENSHOT_WAIT = 3;
	private static int SCROLL_STEPS = 10;
	private static int SCROLL_THRESHOLD = 10;
	private static int SCROLL_TIMEOUT = 2000;

	private TasteTestingConfig() {

	}

	protected static void setPackageName(String packageName) {
		PACKAGE_NAME = packageName;
	}

	public static void setLaunchTimeout(int launchTimeout) {
		LAUNCH_TIMEOUT = launchTimeout;
	}

	public static String getPackageName() {
		if (TextUtils.isEmpty(PACKAGE_NAME)) {
			throw new RuntimeException("Setting package name required. Use TasteTestingConfig.setPackageName()");
		}
		return PACKAGE_NAME;
	}

	public static int getLaunchTimeout() {
		return LAUNCH_TIMEOUT;
	}

	public static int getViewTimeout() {
		return VIEW_TIMEOUT;
	}

	public static int getScreenshotWait() {
		return SCREENSHOT_WAIT;
	}

	public static int getScrollSteps() {
		return SCROLL_STEPS;
	}

	public static int getScrollThreshold() {
		return SCROLL_THRESHOLD;
	}

	public static int getScrollTimeout() {
		return SCROLL_TIMEOUT;
	}
}
