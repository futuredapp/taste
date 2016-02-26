package com.thefuntasty.taste.sample.activity;

import android.app.Activity;
import android.os.Bundle;

import com.thefuntasty.taste.sample.R;

import butterknife.ButterKnife;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);
	}
}
