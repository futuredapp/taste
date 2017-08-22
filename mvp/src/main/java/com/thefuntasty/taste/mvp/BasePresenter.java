package com.thefuntasty.taste.mvp;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

public abstract class BasePresenter<T extends MvpView> implements Presenter<T> {

	private T mvpView;

	@Override
	@CallSuper
	public void attachView(@NonNull T mvpView) {
		this.mvpView = mvpView;
	}

	@Override
	@CallSuper
	public void detachView() {
		mvpView = null;
	}

	public final boolean isViewAttached() {
		return mvpView != null;
	}

	public final T getView() {
		return mvpView;
	}
}
