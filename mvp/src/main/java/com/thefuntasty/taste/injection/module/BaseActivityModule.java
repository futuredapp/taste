package com.thefuntasty.taste.injection.module;

import android.app.Activity;
import android.content.Context;

import com.thefuntasty.taste.TasteActivity;
import com.thefuntasty.taste.injection.annotation.qualifier.ActivityContext;
import com.thefuntasty.taste.injection.annotation.scope.PerScreen;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class BaseActivityModule<T extends TasteActivity> {

	private final T activity;

	public BaseActivityModule(T activity) {
		this.activity = activity;
	}

	@Provides
	@PerScreen
	public T activityT() {
		return activity;
	}

	@Provides
	@PerScreen
	public Activity activity() {
		return activity;
	}

	@Provides
	@ActivityContext
	public Context context() {
		return activity;
	}
}
