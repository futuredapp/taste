package com.thefuntasty.taste;

import android.text.TextUtils;

public class TasteTestingConfig {

	private String packageName;

	private int viewTimeout = 5000;
	private int scrollSteps = 10;
	private int scrollThreshold = 10;
	private int scrollTimeout = 2000;

	public TasteTestingConfig(String packageName) {
		this.packageName = packageName;
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

	public void setViewTimeout(int millis) {
		this.viewTimeout = millis;
	}

	public void setscrollSteps(int scrollSteps) {
		this.scrollSteps = scrollSteps;
	}

	public void setscrollThreshold(int scrollThreshold) {
		this.scrollThreshold = scrollThreshold;
	}

	public void setscrollTimeout(int scrollTimeout) {
		this.scrollTimeout = scrollTimeout;
	}

	public String getPackageName() {
		if (TextUtils.isEmpty(packageName)) {
			throw new RuntimeException("Setting package name required. Use TasteTestingConfig.setPackageName()");
		}
		return packageName;
	}
}
