package com.thefuntasty.taste.infinity;

public interface InfinityRemote {
	void onPreLoadFirst(boolean pullToRefresh);

	void onPreLoadNext();

	void onFirstLoaded(boolean pullToRefresh);

	void onFirstUnavailable(Object payload, boolean pullToRefresh);

	void onFirstEmpty(boolean pullToRefresh);

	void onNextLoaded();

	void onNextUnavailable(Object payload);

	void onFinished();
}
