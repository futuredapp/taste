package com.thefuntasty.taste.injection.component

import com.thefuntasty.taste.TasteFragment

interface BaseFragmentComponent<in T : TasteFragment> {

	fun inject(fragment: T)
}
