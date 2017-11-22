package com.thefuntasty.taste.res

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.annotation.ArrayRes
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.IntegerRes
import android.support.annotation.PluralsRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.graphics.drawable.DrawableCompat

class TRes(private val context: Context) {

	fun resources(): Resources {
		return context.resources
	}

	fun assets(): AssetManager {
		return context.assets
	}

	fun string(@StringRes id: Int): String {
		return context.getString(id)
	}

	fun string(@StringRes id: Int, vararg formatArgs: Any): String {
		return context.getString(id, *formatArgs)
	}

	fun array(@ArrayRes id: Int): Array<String> {
		return resources().getStringArray(id)
	}

	fun dimen(@DimenRes id: Int): Float {
		return resources().getDimension(id)
	}

	fun color(@ColorRes id: Int): Int {
		return ContextCompat.getColor(context, id)
	}

	fun pixel(@DimenRes id: Int): Int {
		return resources().getDimensionPixelSize(id)
	}

	fun integer(@IntegerRes id: Int): Int {
		return resources().getInteger(id)
	}

	fun plurals(@PluralsRes id: Int, quantity: Int): String {
		return resources().getQuantityString(id, quantity)
	}

	fun plurals(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any): String {
		return resources().getQuantityString(id, quantity, *formatArgs)
	}

	fun drawable(@DrawableRes id: Int): Drawable? {
		return ResourcesCompat.getDrawable(resources(), id, null)
	}

	fun drawable(name: String): Drawable? {
		return drawable(drawableIdentifier(name))
	}

	@DrawableRes
	fun drawableIdentifier(name: String): Int {
		return resources().getIdentifier(name, "drawable", context.packageName)
	}

	fun drawableTint(@DrawableRes id: Int, @ColorRes color: Int): Drawable {
		val drawable = DrawableCompat.wrap(drawable(id)!!)
		DrawableCompat.setTint(drawable.mutate(), color(color))
		return drawable
	}

	fun drawableTint(drawable: Drawable, @ColorRes color: Int): Drawable {
		val dr = DrawableCompat.wrap(drawable)
		DrawableCompat.setTint(dr.mutate(), color(color))
		return dr
	}
}
