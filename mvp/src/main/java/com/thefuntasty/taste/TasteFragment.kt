package com.thefuntasty.taste

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import butterknife.ButterKnife
import butterknife.Unbinder

abstract class TasteFragment : Fragment() {

	private var unbinder: Unbinder = Unbinder.EMPTY

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val v = inflater.inflate(fragmentLayout, container, false)
		unbinder = ButterKnife.bind(this, v)
		inject()
		return v
	}

	override fun onDestroyView() {
		super.onDestroyView()

		unbinder.unbind()
	}

	@get:LayoutRes abstract val fragmentLayout: Int

	abstract fun inject()
}
