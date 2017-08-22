package com.thefuntasty.taste

import com.github.ajalt.timberkt.e
import rx.Completable
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.Subscriptions

abstract class BaseCompletableInteractor protected constructor() {
	private var subscription = Subscriptions.unsubscribed()

	protected abstract fun buildObservable(): Completable

	fun execute(onComplete: () -> Unit) {
		unsubscribe()
		subscription = buildObservable().applySchedulers().subscribe(onComplete)
	}

	fun execute(onComplete: () -> Unit, onError: (Throwable) -> Unit) {
		unsubscribe()
		subscription = buildObservable().applySchedulers().subscribe(
				onComplete,
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

	fun Completable.applySchedulers(): Completable {
		return compose({ resultObservable ->
			resultObservable.subscribeOn(workScheduler).observeOn(resultScheduler)
		})
	}
}
