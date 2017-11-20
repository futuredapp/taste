package com.thefuntasty.taste;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;

import org.junit.Before;

public abstract class TasteTestingScenario {

	protected UiDevice testDevice;
	protected TasteTestingRobot robot;
	protected TasteTestingConfig config;

	@Before
	public void setUp() throws RemoteException {
		// Create config instance
		String packageName = getPackageName();
		config = new TasteTestingConfig(packageName);

		beforeSetUp();

		// Initialize UiDevice instance
		testDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
		robot = new TasteTestingRobot(testDevice, config);

		if (!testDevice.isScreenOn()) {
			testDevice.wakeUp();
			testDevice.pressKeyCode(1); // Unlocks screen - works for some devices only
		}

		// Launch the app
		Context context = InstrumentationRegistry.getContext();
		final Intent intent = context.getPackageManager()
				.getLaunchIntentForPackage(packageName);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
		context.startActivity(intent);

		// Here you can wait for your app to load
		afterSetUp();
	}

	protected abstract String getPackageName();

	protected void beforeSetUp() { }

	protected void afterSetUp() { }
}
