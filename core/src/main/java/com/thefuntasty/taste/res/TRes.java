package com.thefuntasty.taste.res;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

public class TRes {

	private final Context context;

	public TRes(Context context) {
		this.context = context;
	}

	public Resources resources() {
		return context.getResources();
	}

	public AssetManager assets() {
		return context.getAssets();
	}

	public String string(@StringRes int id) {
		return context.getString(id);
	}

	public String string(@StringRes int id, Object... formatArgs) {
		return context.getString(id, formatArgs);
	}

	public String[] array(@ArrayRes int id) {
		return resources().getStringArray(id);
	}

	public float dimen(@DimenRes int id) {
		return resources().getDimension(id);
	}

	public int color(@ColorRes int id) {
		return ContextCompat.getColor(context, id);
	}

	public int pixel(@DimenRes int id) {
		return resources().getDimensionPixelSize(id);
	}

	public int integer(@IntegerRes int id) {
		return resources().getInteger(id);
	}

	public String plurals(@PluralsRes int id, int quantity) {
		return resources().getQuantityString(id, quantity);
	}

	public String plurals(@PluralsRes int id, int quantity, Object... formatArgs) {
		return resources().getQuantityString(id, quantity, formatArgs);
	}

	public Drawable drawable(@DrawableRes int id) {
		return ResourcesCompat.getDrawable(resources(), id, null);
	}

	public Drawable drawable(String name) {
		return drawable(drawableIdentifier(name));
	}

	@DrawableRes
	public int drawableIdentifier(String name) {
		return resources().getIdentifier(name, "drawable", context.getPackageName());
	}

	public Drawable drawableTint(@DrawableRes int id, @ColorRes int color) {
		Drawable drawable = DrawableCompat.wrap(drawable(id));
		DrawableCompat.setTint(drawable.mutate(), color(color));
		return drawable;
	}
}
