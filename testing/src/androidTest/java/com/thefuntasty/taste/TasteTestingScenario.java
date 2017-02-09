package com.thefuntasty.taste;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

import org.junit.Before;

public abstract class TasteTestingScenario {

	protected static UiDevice testDevice;

	@Before
	public void setUp() throws RemoteException {
		beforeSetUp();

		// Initialize UiDevice instance
		testDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

		if (!testDevice.isScreenOn()) {
			testDevice.wakeUp();
			testDevice.pressKeyCode(1); // Unlocks screen - not working on xiaomi
		}

		// Launch the app
		Context context = InstrumentationRegistry.getContext();
		String packageName = getPackageName();
		TasteTestingConfig.setPackageName(packageName);
		final Intent intent = context.getPackageManager()
				.getLaunchIntentForPackage(packageName);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
		context.startActivity(intent);

		// Wait for the login screen
		testDevice.wait(Until.findObject(By.res(packageName, getWaitedForViewId())), TasteTestingConfig.getLaunchTimeout());

		afterSetUp();
	}

	protected abstract String getWaitedForViewId();

	protected abstract String getPackageName();

	protected void beforeSetUp() { }

	protected void afterSetUp() { }
}
