package com.thefuntasty.taste

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife

abstract class TasteActivity : AppCompatActivity() {

	@CallSuper
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val activityLayout = activityLayout
		if (activityLayout != 0) {
			setContentView(activityLayout)
			ButterKnife.bind(this)
		}
		inject()
	}

	@get:LayoutRes abstract val activityLayout: Int

	abstract fun inject()
}
