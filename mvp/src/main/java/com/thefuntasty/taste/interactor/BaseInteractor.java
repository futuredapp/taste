package com.thefuntasty.taste.interactor;

import android.support.annotation.NonNull;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;
import timber.log.Timber;

public abstract class BaseInteractor<T> {

	private Subscription subscription = Subscriptions.unsubscribed();

	protected BaseInteractor() {
	}

	protected abstract Observable<T> buildObservable();

	public void execute(Subscriber<T> subscriber) {
		unsubscribe();
		subscription = buildObservable()
				.compose(applySchedulers())
				.subscribe(subscriber);
	}

	public void execute(@NonNull Action1<T> onNext) {
		execute(onNext, new Action1<Throwable>() {
			@Override public void call(Throwable throwable) { }
		});
	}

	public void execute(@NonNull Action1<T> onNext, final @NonNull Action1<Throwable> onError) {
		unsubscribe();
		subscription = buildObservable()
				.compose(applySchedulers())
				.subscribe(onNext, new Action1<Throwable>() {
					@Override public void call(Throwable throwable) {
						Timber.e(throwable);
						onError.call(throwable);
					}
				});
	}

	public void execute(@NonNull Action0 onComplete, final @NonNull Action1<Throwable> onError) {
		unsubscribe();
		subscription = buildObservable()
				.compose(applySchedulers())
				.subscribe(new Action1<T>() {
					@Override public void call(T t) { }
				}, new Action1<Throwable>() {
					@Override public void call(Throwable throwable) {
						Timber.e(throwable);
						onError.call(throwable);
					}
				}, onComplete);
	}

	public void unsubscribe() {
		if (!subscription.isUnsubscribed()) {
			subscription.unsubscribe();
		}
	}

	protected Scheduler getWorkScheduler() {
		return Schedulers.io();
	}

	protected Scheduler getResultScheduler() {
		return AndroidSchedulers.mainThread();
	}

	private Observable.Transformer<T, T> applySchedulers() {
		return new Observable.Transformer<T, T>() {
			@Override public Observable<T> call(Observable<T> observable) {
				return observable
						.subscribeOn(BaseInteractor.this.getWorkScheduler())
						.observeOn(BaseInteractor.this.getResultScheduler());
			}
		};
	}
}
