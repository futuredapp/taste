package com.thefuntasty.taste

import com.github.ajalt.timberkt.e
import rx.Observable
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.Subscriptions

abstract class BaseObservableInteractor<T> protected constructor() {
	private var subscription = Subscriptions.unsubscribed()

	protected abstract fun buildObservable(): Observable<T>

	fun execute(onSuccess: (T) -> Unit) {
		unsubscribe()
		subscription = buildObservable().applySchedulers().subscribe(
				onSuccess)
	}

	fun execute(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit) {
		unsubscribe()
		subscription = buildObservable().applySchedulers().subscribe(
				onSuccess,
				{ throwable ->
					e(throwable)
					onError(throwable)
				})
	}

	fun unsubscribe() {
		if (!subscription.isUnsubscribed) {
			subscription.unsubscribe()
		}
	}

	protected val workScheduler: Scheduler = Schedulers.io()

	protected val resultScheduler: Scheduler = AndroidSchedulers.mainThread()

	fun Observable<T>.applySchedulers(): Observable<T> {
		return compose({ resultObservable ->
			resultObservable.subscribeOn(workScheduler).observeOn(resultScheduler)
		})
	}
}