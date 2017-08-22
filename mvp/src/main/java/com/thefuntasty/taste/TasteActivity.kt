package com.thefuntasty.taste

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife

abstract class TasteActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val activityLayout = activityLayout
		if (activityLayout != 0) {
			setContentView(activityLayout)
			ButterKnife.bind(this)
		}
		inject()
	}

	abstract val activityLayout: Int

	abstract fun inject()

}
