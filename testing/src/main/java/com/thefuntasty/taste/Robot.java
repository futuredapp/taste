package com.thefuntasty.taste;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Until;

import java.util.concurrent.TimeUnit;

import static com.thefuntasty.taste.TasteTestingScenario.testDevice;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class Robot {

	private Robot() {

	}

	// Actions
	public static void tapById(String viewId) {
		testDevice.wait(Until.findObject(By.res(TasteTestingConfig.getPackageName(), viewId)), TasteTestingConfig.getViewTimeout()).click();
	}

	public static void tapByText(String text) {
		testDevice.wait(Until.findObject(By.text(text)), TasteTestingConfig.getViewTimeout()).click();
	}

	public static void tapByContainedText(String text) {
		testDevice.wait(Until.findObject(By.textContains(text)), TasteTestingConfig.getViewTimeout()).click();
	}

	public static void writeByText(String findText, String writeText) {
		testDevice.wait(Until.findObject(By.text(findText)), TasteTestingConfig.getViewTimeout()).setText(writeText);
	}

	public static void writeById(String viewId, String writeText) {
		testDevice.wait(Until.findObject(By.res(TasteTestingConfig.getPackageName(), viewId)), TasteTestingConfig.getViewTimeout()).setText(writeText);
	}

	public static void openLeftMenu() {
		testDevice.wait(Until.findObject(By.desc("Navigate up")), TasteTestingConfig.getViewTimeout()).click();
	}

	public static void openRightMenu() {
		testDevice.wait(Until.findObject(By.res(TasteTestingConfig.getPackageName(), "menu_notification")), TasteTestingConfig.getViewTimeout()).click();
	}

	public static void scroll(@TasteDirection.DirectionType int directionType) {
		int screenWidth = testDevice.getDisplayWidth();
		int screenHeight = testDevice.getDisplayHeight();

		switch (directionType) {
			case TasteDirection.DOWN:
				testDevice.drag(screenWidth / 2, screenHeight / 2, screenWidth / 2, 0, TasteTestingConfig.getScrollSteps());
				break;
			case TasteDirection.UP:
				testDevice.drag(screenWidth / 2, screenHeight / 2, screenWidth / 2, screenHeight, TasteTestingConfig.getScrollSteps());
				break;
			case TasteDirection.LEFT:
				testDevice.drag(screenWidth / 4, screenHeight / 2, screenWidth, screenHeight / 2, TasteTestingConfig.getScrollSteps());
				break;
			case TasteDirection.RIGHT:
				testDevice.drag(screenWidth - screenWidth / 4, screenHeight / 2, 0, screenHeight / 2, TasteTestingConfig.getScrollSteps());
				break;
			default:
				throw new RuntimeException("Invalid scroll direction");
		}
	}

	public static void halfScroll(@TasteDirection.DirectionType int directionType) {
		int screenWidth = testDevice.getDisplayWidth();
		int screenHeight = testDevice.getDisplayHeight();

		switch (directionType) {
			case TasteDirection.DOWN:
				testDevice.drag(screenWidth / 2, screenHeight / 2, screenWidth / 2, screenHeight / 4, TasteTestingConfig.getScrollSteps());
				break;
			case TasteDirection.UP:
				testDevice.drag(screenWidth / 2, screenHeight / 2, screenWidth / 2, screenHeight / 4 * 3, TasteTestingConfig.getScrollSteps());
				break;
			case TasteDirection.LEFT:
				testDevice.drag(screenWidth / 4, screenHeight / 2, screenWidth / 2, screenHeight / 2, TasteTestingConfig.getScrollSteps());
				break;
			case TasteDirection.RIGHT:
				testDevice.drag(screenWidth - screenWidth / 4, screenHeight / 2, screenWidth / 2, screenHeight / 2, TasteTestingConfig.getScrollSteps());
				break;
			default:
				throw new RuntimeException("Invalid scroll direction");
		}
	}

	public static void scrollUntilId(@TasteDirection.DirectionType int directionType, String viewId) {
		int retry = 0;
		do {
			halfScroll(directionType);
			if (testDevice.wait(Until.findObject(By.res(TasteTestingConfig.getPackageName(), viewId)), TasteTestingConfig.getScrollTimeout()) != null) {
				break;
			}
			retry++;
		}
		while (retry <= TasteTestingConfig.getScrollThreshold());
	}

	public static void scrollUntilText(@TasteDirection.DirectionType int directionType, String text) {
		int retry = 0;
		do {
			halfScroll(directionType);
			if (testDevice.wait(Until.findObject(By.text(text)), TasteTestingConfig.getScrollTimeout()) != null) {
				break;
			}
			retry++;
		}
		while (retry <= TasteTestingConfig.getScrollThreshold());
	}

	public static void pressBack() {
		testDevice.pressBack();
	}

	public static void pressHome() {
		testDevice.pressHome();
	}

	public static void pressRecents() {
		try {
			testDevice.pressRecentApps();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// Assertions
	public static void notPresentByText(String text) {
		assertNull(testDevice.wait(Until.findObject(By.text(text)), TasteTestingConfig.getViewTimeout()));
	}

	public static void notPresentById(String viewId) {
		assertNull(testDevice.wait(Until.findObject(By.res(TasteTestingConfig.getPackageName(), viewId)), TasteTestingConfig.getViewTimeout()));
	}

	public static void presentByText(String text) {
		assertNotNull(testDevice.wait(Until.findObject(By.text(text)), TasteTestingConfig.getViewTimeout()));
	}

	public static void presentById(String viewId) {
		assertNotNull(testDevice.wait(Until.findObject(By.res(TasteTestingConfig.getPackageName(), viewId)), TasteTestingConfig.getViewTimeout()));
	}

	public static void textInIdEquals(String viewId, String text) {
		assertTrue(testDevice.wait(Until.findObject(By.res(TasteTestingConfig.getPackageName(), viewId)), TasteTestingConfig.getViewTimeout()).getText().equals(text));
	}

	public static void textInIdContains(String viewId, String text) {
		assertTrue(testDevice.wait(Until.findObject(By.res(TasteTestingConfig.getPackageName(), viewId)), TasteTestingConfig.getViewTimeout()).getText().contains(text));
	}

	public static void textInIdDiffer(String viewId, String text) {
		assertFalse(testDevice.wait(Until.findObject(By.res(TasteTestingConfig.getPackageName(), viewId)), TasteTestingConfig.getViewTimeout()).getText().equals(text));
	}

	public static void enabledById(String viewId) {
		assertTrue(testDevice.wait(Until.findObject(By.res(TasteTestingConfig.getPackageName(), viewId)), TasteTestingConfig.getViewTimeout()).isEnabled());
	}

	public static void disabledById(String viewId) {
		assertFalse(testDevice.wait(Until.findObject(By.res(TasteTestingConfig.getPackageName(), viewId)), TasteTestingConfig.getViewTimeout()).isEnabled());
	}

	public static void checkedById(String viewId) {
		assertTrue(testDevice.wait(Until.findObject(By.res(TasteTestingConfig.getPackageName(), viewId)), TasteTestingConfig.getViewTimeout()).isChecked());
	}

	public static void notCheckedById(String viewId) {
		assertFalse(testDevice.wait(Until.findObject(By.res(TasteTestingConfig.getPackageName(), viewId)), TasteTestingConfig.getViewTimeout()).isChecked());
	}

	// Misc

	public static String getTextById(String viewId) {
		return testDevice.wait(Until.findObject(By.res(TasteTestingConfig.getPackageName(), viewId)), TasteTestingConfig.getViewTimeout()).getText();
	}

	public static void takeScreenshot(String name) {
		wait(TasteTestingConfig.getScreenshotWait());
		testDevice.takeScreenshot(TasteTestingSpoonWrapper.getScreenshotDirectory(name));
	}

	public static void wait(int seconds) {
		// This is ugly but it works
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void waitForAnimation() {
		testDevice.waitForWindowUpdate(TasteTestingConfig.getPackageName(), TasteTestingConfig.getViewTimeout());
	}
}
