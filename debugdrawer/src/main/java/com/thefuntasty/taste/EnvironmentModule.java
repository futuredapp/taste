package com.thefuntasty.taste;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import io.palaima.debugdrawer.base.DebugModule;

public class EnvironmentModule implements DebugModule {

	private Spinner spinner;
	private final Context context;
	private final EnvironmentModuleCallback callback;
	private List<Environment> list;
	private boolean shouldCallback = false;

	public EnvironmentModule(Context context, List<Environment> list, EnvironmentModuleCallback callback) {
		this.context = context;
		this.callback = callback;
		this.list = list;
	}

	@NonNull @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
		View view = inflater.inflate(R.layout.module_environment, parent, false);

		spinner = (Spinner) view.findViewById(R.id.environment_spinner);

		ArrayAdapter<Environment> adapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, list);
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (shouldCallback) {
					callback.onEnvironmentSelected(list.get((int) spinner.getSelectedItemId()));
				}
				shouldCallback = true;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		return view;
	}

	@Override
	public void onOpened() {
	}

	@Override
	public void onClosed() {
	}

	@Override
	public void onResume() {
	}

	@Override
	public void onPause() {
	}

	@Override
	public void onStart() {
	}

	@Override
	public void onStop() {
	}

	public interface EnvironmentModuleCallback {
		void onEnvironmentSelected(Environment environment);
	}
}