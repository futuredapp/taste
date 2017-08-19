package com.thefuntasty.taste.injection.module;

import android.support.v4.app.Fragment;

import com.thefuntasty.taste.TasteFragment;
import com.thefuntasty.taste.injection.annotation.scope.PerScreen;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class BaseFragmentModule<T extends TasteFragment> {

	private final T fragment;

	public BaseFragmentModule(T fragment) {
		this.fragment = fragment;
	}

	@Provides
	@PerScreen
	public T fragmentT() {
		return fragment;
	}

	@Provides
	@PerScreen
	public Fragment fragment() {
		return fragment;
	}
}
