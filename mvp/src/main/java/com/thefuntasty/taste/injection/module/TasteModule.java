package com.thefuntasty.taste.injection.module;

import android.content.Context;

import com.thefuntasty.taste.display.TDisplay;
import com.thefuntasty.taste.injection.annotation.qualifier.ApplicationContext;
import com.thefuntasty.taste.res.TRes;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TasteModule {

	@Provides
	@Singleton static TRes tRes(@ApplicationContext Context context) {
		return new TRes(context);
	}

	@Provides
	@Singleton static TDisplay tDisplay(@ApplicationContext Context context) {
		return new TDisplay(context);
	}
}
