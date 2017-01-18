package com.thefuntasty.taste.mvp;

interface Presenter<V extends MvpView> {

	void attachView(V mvpView);

	void detachView();
}
