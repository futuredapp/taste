package com.thefuntasty.taste.injection.component;

import com.thefuntasty.taste.TasteActivity;

public interface BaseActivityComponent<T extends TasteActivity> {

	void inject(T activity);
}
