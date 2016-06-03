package com.thefuntasty.taste.infinity;

public class InfinityEventListener {
	public void onFirstLoaded(boolean pullToRefresh) {}
	public void onFirstUnavailable(Object payload, boolean pullToRefresh) {}
	public void onFirstEmpty(boolean pullToRefresh) {}
	public void onNextLoaded() {}
	public void onNextUnavailable(Object payload) {}
	public void onFinished() {}
	public void onPreLoadFirst(boolean pullToRefresh) {}
	public void onPreLoadNext() {}
}