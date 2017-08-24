package com.thefuntasty.taste.injection.module

import android.content.Context
import com.thefuntasty.taste.display.TDisplay
import com.thefuntasty.taste.injection.annotation.ApplicationContext
import com.thefuntasty.taste.res.TRes
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TasteModule {

	@Provides
	@Singleton
	fun tRes(@ApplicationContext context: Context) = TRes(context)

	@Provides
	@Singleton
	fun tDisplay(@ApplicationContext context: Context) = TDisplay(context)
}
