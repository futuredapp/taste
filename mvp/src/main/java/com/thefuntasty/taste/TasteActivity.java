package com.thefuntasty.taste;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class TasteActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int activityLayout = getActivityLayout();
		if (activityLayout != 0) {
			setContentView(activityLayout);
			ButterKnife.bind(this);
		}
		inject();
	}

	public abstract @LayoutRes int getActivityLayout();

	public abstract void inject();

}
