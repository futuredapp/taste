package com.thefuntasty.taste;

import android.text.TextUtils;

public class TasteTestingConfig {

	private String packageName;

	private int launchTimeout = 10000;
	private int viewTimeout = 5000;
	private int scrollSteps = 10;
	private int scrollThreshold = 10;
	private int scrollTimeout = 2000;

	public TasteTestingConfig(String packageName) {
		this.packageName = packageName;
	}

	public int getLaunchTimeout() {
		return launchTimeout;
	}

	public int getViewTimeout() {
		return viewTimeout;
	}

	public int getScrollSteps() {
		return scrollSteps;
	}

	public int getScrollThreshold() {
		return scrollThreshold;
	}

	public int getScrollTimeout() {
		return scrollTimeout;
	}

	public void setLaunchTimeout(int millis) {
		this.launchTimeout = millis;
	}

	public void setViewTimeout(int millis) {
		this.viewTimeout = millis;
	}

	public String getPackageName() {
		if (TextUtils.isEmpty(packageName)) {
			throw new RuntimeException("Setting package name required. Use TasteTestingConfig.setPackageName()");
		}
		return packageName;
	}
}
