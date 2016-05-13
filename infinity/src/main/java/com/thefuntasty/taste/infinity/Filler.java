package com.thefuntasty.taste.infinity;

import android.support.annotation.UiThread;

import java.util.List;

public interface Filler<T> {
	interface AsynchronousFiller<T> extends Filler<T> {
		void onLoad(int limit, int offset, Callback<T> callback);
	}

	interface Callback<T> {
		@UiThread void onData(List<T> list);
		@UiThread void onError(Object payload);
	}
}
