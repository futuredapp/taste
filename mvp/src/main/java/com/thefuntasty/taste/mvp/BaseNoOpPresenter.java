package com.thefuntasty.taste.mvp;

import com.thefuntasty.taste.tools.reflexion.NoOpGenerator;

public abstract class BaseNoOpPresenter<V extends MvpView> implements Presenter<V> {

	private boolean checkIfPresenterIsValid = true;

	private boolean wasDetached = false;

	private V view;

	public abstract void logGetViewAfterDetachEvent();

	@Override
	public void attachView(V mvpView) {
		view = mvpView;

		if (checkIfPresenterIsValid) {
			MvpView v = NoOpGenerator.fromMvpInterface(getClass());
			if (v == null) {
				throw new IllegalStateException("Can't create NoOp view for " + getClass());
			}
		}
	}

	@Override
	public void detachView() {
		view = null;
		wasDetached = true;
	}

	public V getView() {
		if (view == null && !wasDetached) {
			throw new NullPointerException("MvpView reference is null. Have you called attachView()?");
		}
		if (view == null && wasDetached) {
			view = NoOpGenerator.fromMvpInterface(getClass());
		}
		if (wasDetached) {
			logGetViewAfterDetachEvent();
		}

		return view;
	}

	public boolean isRealViewAttached() {
		return view != null && !wasDetached;
	}
	
	public void setCheckIfPresenterIsValid(boolean checkIfPresenterIsValid) {
		this.checkIfPresenterIsValid = checkIfPresenterIsValid;
	}
}
