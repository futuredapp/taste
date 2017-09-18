package com.thefuntasty.taste.injection.module

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.content.Context

import com.thefuntasty.taste.TasteActivity
import com.thefuntasty.taste.injection.annotation.ActivityContext
import com.thefuntasty.taste.injection.annotation.PerScreen

import dagger.Module
import dagger.Provides

@Module
abstract class BaseActivityModule<out T : TasteActivity>(private val activity: T) {

	@Provides
	@PerScreen
	fun activityT(): T {
		return activity
	}

	@Provides
	@PerScreen
	fun activity(): Activity {
		return activity
	}

	@Provides
	@ActivityContext
	fun context(): Context {
		return activity
	}

    @Provides
    @ActivityContext
    fun lifecycle(): Lifecycle {
        return activity.lifecycle
    }
}
