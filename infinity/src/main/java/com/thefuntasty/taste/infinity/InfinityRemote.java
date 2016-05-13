package com.thefuntasty.taste.infinity;

public interface InfinityRemote {
	void onPreLoadFirst();

	void onPreLoadNext();

	void onFirstLoaded();

	void onFirstUnavailable(Object payload);

	void onFirstEmpty();

	void onNextLoaded();

	void onNextUnavailable(Object payload);

	void onFinished();
}
