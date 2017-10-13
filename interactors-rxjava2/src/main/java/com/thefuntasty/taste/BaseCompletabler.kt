package com.thefuntasty.taste

import android.support.annotation.VisibleForTesting
import com.github.ajalt.timberkt.e
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.TestSubscriber

abstract class BaseCompletabler protected constructor() {
	private var disposable = Disposables.disposed()

	protected abstract fun build(): Completable

	fun execute(onComplete: () -> Unit) {
		dispose()
		disposable = build().applySchedulers().subscribe(onComplete)
	}

	fun execute(onComplete: () -> Unit, onError: (Throwable) -> Unit) {
		dispose()
		disposable = build().applySchedulers().subscribe(
				onComplete,
				{ throwable ->
					e(throwable)
					onError(throwable)
				})
	}

	@VisibleForTesting
	fun execute(testSubscriber: TestSubscriber<Unit>) {
		dispose()
		build()
				.applySchedulers()
				.toFlowable<Unit>()
				.subscribeWith(testSubscriber)

		disposable = testSubscriber
	}

	fun dispose() {
		if (!disposable.isDisposed) {
			disposable.dispose()
		}
	}

	protected val workScheduler: Scheduler = Schedulers.io()

	protected val resultScheduler: Scheduler = AndroidSchedulers.mainThread()

	private fun Completable.applySchedulers(): Completable {
		return compose { resultObservable ->
			resultObservable.subscribeOn(workScheduler).observeOn(resultScheduler)
		}
	}
}
