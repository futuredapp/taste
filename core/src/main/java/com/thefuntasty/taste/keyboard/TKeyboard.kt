package com.thefuntasty.taste.keyboard

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class TKeyboard {

	fun hide(activity: Activity) {
		val focusedView = activity.currentFocus
		hide(focusedView)
	}

	fun hide(focusedView: View?) {
		if (focusedView != null) {
			val inputMethodManager = focusedView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
			inputMethodManager.hideSoftInputFromWindow(focusedView.windowToken, 0)
		}
	}

	fun show(view: View) {
		val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		view.requestFocus()
		inputMethodManager.showSoftInput(view, 0)
	}
}
