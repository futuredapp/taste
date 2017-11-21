package com.thefuntasty.taste.display

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

class TDisplay(private val context: Context) {

	val windowWidth: Int
		get() {
			val metrics = DisplayMetrics()
			val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
			windowManager.defaultDisplay.getMetrics(metrics)
			return metrics.widthPixels
		}

	val windowHeight: Int
		get() {
			val metrics = DisplayMetrics()
			val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
			windowManager.defaultDisplay.getMetrics(metrics)
			return metrics.heightPixels
		}

	fun orientation(): Int  = context.resources.configuration.orientation

	fun dpToPx(dp: Float): Float = dp * context.resources.displayMetrics.density

	fun pxToDp(px: Float): Float = px / context.resources.displayMetrics.density
}
