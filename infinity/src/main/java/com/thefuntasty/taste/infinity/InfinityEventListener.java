package com.thefuntasty.taste.infinity;

public class InfinityEventListener {
	public void onFirstLoaded() {}
	public void onFirstUnavailable(Object payload) {}
	public void onFirstEmpty() {}
	public void onNextLoaded() {}
	public void onNextUnavailable(Object payload) {}
	public void onFinished() {}
	public void onPreLoadFirst() {}
	public void onPreLoadNext() {}
}