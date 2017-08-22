package com.thefuntasty.taste.injection.module

import android.support.v4.app.Fragment
import com.thefuntasty.taste.TasteFragment
import com.thefuntasty.taste.injection.annotation.PerScreen
import dagger.Module
import dagger.Provides

@Module
abstract class BaseFragmentModule<out T : TasteFragment>(private val fragment: T) {

	@Provides
	@PerScreen
	fun fragmentT(): T {
		return fragment
	}

	@Provides
	@PerScreen
	fun fragment(): Fragment {
		return fragment
	}
}
