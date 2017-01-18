package com.thefuntasty.taste.interactor;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

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
