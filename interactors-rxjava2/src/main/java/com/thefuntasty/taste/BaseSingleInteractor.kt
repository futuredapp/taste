package com.thefuntasty.taste

import com.github.ajalt.timberkt.e
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers

abstract class BaseSingleInteractor<T> protected constructor() {
	private var disposable = Disposables.disposed()

	protected abstract fun buildObservable(): Single<T>

	fun execute(onSuccess: (T) -> Unit) {
		dispose()
		disposable = buildObservable().applySchedulers().subscribe(onSuccess)
	}

	fun execute(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit) {
		dispose()
		disposable = buildObservable().applySchedulers().subscribe(
				onSuccess,
				{ throwable ->
					e(throwable)
					onError(throwable)
				})
	}

	fun dispose() {
		if (disposable.isDisposed) {
			disposable.dispose()
		}
	}

	protected open fun getWorkScheduler() = Schedulers.io()

	protected open fun getResultScheduler(): Scheduler = AndroidSchedulers.mainThread()

	private fun Single<T>.applySchedulers(): Single<T> {
		return compose({ resultObservable ->
			resultObservable.subscribeOn(getWorkScheduler()).observeOn(getResultScheduler())
		})
	}
}
