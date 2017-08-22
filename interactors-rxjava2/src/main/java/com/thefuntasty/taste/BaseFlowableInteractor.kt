package com.thefuntasty.taste

import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers

abstract class BaseFlowableInteractor<T> protected constructor() {
	private var disposable: Disposable = Disposables.disposed()

	protected abstract fun buildObservable(): Flowable<T>

	fun execute(onNext: (T) -> Unit) {
		dispose()
		disposable = buildObservable()
				.applySchedulers()
				.subscribe(onNext)
	}

	fun execute(onNext: (T) -> Unit, onError: (Throwable) -> Unit) {
		dispose()
		disposable = buildObservable()
				.applySchedulers()
				.subscribe(onNext, onError)
	}

	fun execute(onNext: (T) -> Unit, onError: (Throwable) -> Unit, onComplete: () -> Unit) {
		dispose()
		disposable = buildObservable()
				.applySchedulers()
				.subscribe(onNext, onError, onComplete)
	}

	fun dispose() {
		if (!disposable.isDisposed) {
			disposable.dispose()
		}
	}

	protected open fun getWorkScheduler() = Schedulers.io()

	protected open fun getResultScheduler(): Scheduler = AndroidSchedulers.mainThread()

	private fun Flowable<T>.applySchedulers(): Flowable<T> {
		return compose({ resultObservable ->
			resultObservable
					.subscribeOn(getWorkScheduler())
					.observeOn(getResultScheduler())
		})
	}
}
