package com.thefuntasty.taste.injection.component;

import com.thefuntasty.taste.TasteFragment;

public interface BaseFragmentComponent<T extends TasteFragment> {

	void inject(T fragment);
}
