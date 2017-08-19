package com.thefuntasty.taste;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class TasteFragment extends Fragment {

	private Unbinder unbinder;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(getFragmentLayout(), container, false);
		unbinder = ButterKnife.bind(this, v);
		inject();
		return v;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();

		unbinder.unbind();
	}

	public abstract @LayoutRes int getFragmentLayout();

	public abstract void inject();

}
