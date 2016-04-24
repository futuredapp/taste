package com.thefuntasty.taste.savestate;

import android.os.Bundle;

import org.parceler.Parcels;

import icepick.Bundler;

/**
 * Class for wrapping and unwrapping parcelled classes. Class must implement TBundleable
 * interface and must be annotated with @Parcel annotation.
 *
 * Parcelled activity's/fragment's fields which should be saved to `savedInstanceState` must be annotated
 * with @State(TBundler.class) annotation.
 */
public class TBundler implements Bundler<TBundleable>{
	@Override public void put(String key, TBundleable TBundleable, Bundle bundle) {
		bundle.putParcelable(key, Parcels.wrap(TBundleable));
	}

	@Override public TBundleable get(String key, Bundle bundle) {
		return Parcels.unwrap(bundle.getParcelable(key));
	}
}
