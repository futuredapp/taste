package com.thefuntasty.taste.injection.component

import com.thefuntasty.taste.TasteActivity

interface BaseActivityComponent<in T : TasteActivity> {

	fun inject(activity: T)
}
