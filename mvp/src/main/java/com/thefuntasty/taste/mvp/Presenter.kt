package com.thefuntasty.taste.mvp

interface Presenter<in V : MvpView> {

	fun attachView(mvpView: V)

	fun detachView()
}
