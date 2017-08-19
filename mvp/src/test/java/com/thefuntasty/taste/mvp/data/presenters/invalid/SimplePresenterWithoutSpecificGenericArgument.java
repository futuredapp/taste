package com.thefuntasty.taste.mvp.data.presenters.invalid;

import com.thefuntasty.taste.mvp.data.presenters.valid.BasePresenter;
import com.thefuntasty.taste.mvp.data.view.SimpleMvpView;

public class SimplePresenterWithoutSpecificGenericArgument<V extends SimpleMvpView> extends BasePresenter<V> {

}
