package com.thefuntasty.taste.mvp.data.presenters.valid;

import com.thefuntasty.taste.mvp.BaseNoOpPresenter;
import com.thefuntasty.taste.mvp.MvpView;

public class BasePresenter<V extends MvpView> extends BaseNoOpPresenter<V> {

	@Override
	public void logGetViewAfterDetachEvent() {
		// Pass
	}
}
