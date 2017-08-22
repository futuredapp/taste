package com.thefuntasty.taste

import com.github.ajalt.timberkt.e
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers

abstract class BaseCompletableInteractor protected constructor() {
	private var disposable = Disposables.disposed()

	protected abstract fun buildObservable(): Completable

	fun execute(onComplete: () -> Unit) {
		unsubscribe()
		disposable = buildObservable().applySchedulers().subscribe(onComplete)
	}

	fun execute(onComplete: () -> Unit, onError: (Throwable) -> Unit) {
		unsubscribe()
		disposable = buildObservable().applySchedulers().subscribe(
				onComplete,
				{ throwable ->
					e(throwable)
					onError(throwable)
				})
	}

	fun unsubscribe() {
		if (!disposable.isDisposed) {
			disposable.dispose()
		}
	}

	protected val workScheduler: Scheduler = Schedulers.io()

	protected val resultScheduler: Scheduler = AndroidSchedulers.mainThread()

	private fun Completable.applySchedulers(): Completable {
		return compose({ resultObservable ->
			resultObservable.subscribeOn(workScheduler).observeOn(resultScheduler)
		})
	}
}
