package com.thefuntasty.taste;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TasteTestingRobot {

	private UiDevice testDevice;
	private TasteTestingConfig config;

	public TasteTestingRobot(UiDevice testDevice, TasteTestingConfig config) {
		this.testDevice = testDevice;
		this.config = config;
	}

	// Actions
	public void tapById(String viewId) {
		testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).click();
	}

	public void tapByText(String text) {
		testDevice.wait(Until.findObject(By.text(text)), config.getViewTimeout()).click();
	}

	public void tapByContainedText(String text) {
		testDevice.wait(Until.findObject(By.textContains(text)), config.getViewTimeout()).click();
	}

	public void tapByDescription(String contentDescription) {
		testDevice.wait(Until.findObject(By.desc(contentDescription)), config.getViewTimeout()).click();
	}

	public void tapByContainedInDescription(String contentDescription) {
		testDevice.wait(Until.findObject(By.descContains(contentDescription)), config.getViewTimeout()).click();
	}

	public void writeByText(String findText, String writeText) {
		testDevice.wait(Until.findObject(By.text(findText)), config.getViewTimeout()).setText(writeText);
	}

	public void writeById(String viewId, String writeText) {
		testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).setText(writeText);
	}

	public void allowPermission() {
		testDevice.wait(Until.findObject(By.res("com.android.packageinstaller", "permission_allow_button")), config.getViewTimeout()).click();
	}

	public void denyPermission() {
		testDevice.wait(Until.findObject(By.res("com.android.packageinstaller", "permission_deny_button")), config.getViewTimeout()).click();
	}

	public void scroll(@TasteDirection.DirectionType int directionType) {
		int screenWidth = testDevice.getDisplayWidth();
		int screenHeight = testDevice.getDisplayHeight();

		switch (directionType) {
			case TasteDirection.DOWN:
				testDevice.drag(screenWidth / 2, screenHeight / 2, screenWidth / 2, 0, config.getScrollSteps());
				break;
			case TasteDirection.UP:
				testDevice.drag(screenWidth / 2, screenHeight / 2, screenWidth / 2, screenHeight, config.getScrollSteps());
				break;
			case TasteDirection.LEFT:
				testDevice.drag(screenWidth / 4, screenHeight / 2, screenWidth, screenHeight / 2, config.getScrollSteps());
				break;
			case TasteDirection.RIGHT:
				testDevice.drag(screenWidth - screenWidth / 4, screenHeight / 2, 0, screenHeight / 2, config.getScrollSteps());
				break;
			default:
				throw new RuntimeException("Invalid scroll direction");
		}
	}

	public void halfScroll(@TasteDirection.DirectionType int directionType) {
		int screenWidth = testDevice.getDisplayWidth();
		int screenHeight = testDevice.getDisplayHeight();

		switch (directionType) {
			case TasteDirection.DOWN:
				testDevice.drag(screenWidth / 2, screenHeight / 2, screenWidth / 2, screenHeight / 4, config.getScrollSteps());
				break;
			case TasteDirection.UP:
				testDevice.drag(screenWidth / 2, screenHeight / 2, screenWidth / 2, screenHeight / 4 * 3, config.getScrollSteps());
				break;
			case TasteDirection.LEFT:
				testDevice.drag(screenWidth / 4, screenHeight / 2, screenWidth / 2, screenHeight / 2, config.getScrollSteps());
				break;
			case TasteDirection.RIGHT:
				testDevice.drag(screenWidth - screenWidth / 4, screenHeight / 2, screenWidth / 2, screenHeight / 2, config.getScrollSteps());
				break;
			default:
				throw new RuntimeException("Invalid scroll direction");
		}
	}

	public void scrollUntilId(@TasteDirection.DirectionType int directionType, String viewId) {
		int retry = 0;
		do {
			halfScroll(directionType);
			if (testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getScrollTimeout()) != null) {
				break;
			}
			retry++;
		}
		while (retry <= config.getScrollThreshold());
	}

	public void scrollUntilText(@TasteDirection.DirectionType int directionType, String text) {
		int retry = 0;
		do {
			halfScroll(directionType);
			if (testDevice.wait(Until.findObject(By.text(text)), config.getScrollTimeout()) != null) {
				break;
			}
			retry++;
		}
		while (retry <= config.getScrollThreshold());
	}

	public void dragViewById(@TasteDirection.DirectionType int directionType, String viewId) {
		int screenWidth = testDevice.getDisplayWidth();
		int screenHeight = testDevice.getDisplayHeight();
		int viewX = testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).getVisibleCenter().x;
		int viewY = testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).getVisibleCenter().y;

		switch (directionType) {
			case TasteDirection.DOWN:
				testDevice.drag(viewX, viewY, viewX, viewY - screenHeight, config.getScrollSteps());
				break;
			case TasteDirection.UP:
				testDevice.drag(viewX, viewY, viewX, viewY + screenHeight, config.getScrollSteps());
				break;
			case TasteDirection.LEFT:
				testDevice.drag(viewX, viewY, viewX + screenWidth, viewY, config.getScrollSteps());
				break;
			case TasteDirection.RIGHT:
				testDevice.drag(viewX, viewY, viewX - screenWidth, viewY, config.getScrollSteps());
				break;
			default:
				throw new RuntimeException("Invalid scroll direction");
		}
	}

	public void dragViewByText(@TasteDirection.DirectionType int directionType, String text) {
		int screenWidth = testDevice.getDisplayWidth();
		int screenHeight = testDevice.getDisplayHeight();
		int viewX = testDevice.wait(Until.findObject(By.text(text)), config.getViewTimeout()).getVisibleCenter().x;
		int viewY = testDevice.wait(Until.findObject(By.text(text)), config.getViewTimeout()).getVisibleCenter().y;

		switch (directionType) {
			case TasteDirection.DOWN:
				testDevice.drag(viewX, viewY, viewX, viewY - screenHeight, config.getScrollSteps());
				break;
			case TasteDirection.UP:
				testDevice.drag(viewX, viewY, viewX, viewY + screenHeight, config.getScrollSteps());
				break;
			case TasteDirection.LEFT:
				testDevice.drag(viewX, viewY, viewX + screenWidth, viewY, config.getScrollSteps());
				break;
			case TasteDirection.RIGHT:
				testDevice.drag(viewX, viewY, viewX - screenWidth, viewY, config.getScrollSteps());
				break;
			default:
				throw new RuntimeException("Invalid scroll direction");
		}
	}

	public void pressBack() {
		testDevice.pressBack();
	}

	public void pressHome() {
		testDevice.pressHome();
	}

	public void pressRecents() {
		try {
			testDevice.pressRecentApps();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// Assertions
	public void notPresentByText(String text) {
		assertNull(testDevice.wait(Until.findObject(By.text(text)), config.getViewTimeout()));
	}

	public void notPresentById(String viewId) {
		assertNull(testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()));
	}

	public void presentByText(String text) {
		assertNotNull(testDevice.wait(Until.findObject(By.text(text)), config.getViewTimeout()));
	}

	public void presentById(String viewId) {
		assertNotNull(testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()));
	}

	public void textInIdEquals(String viewId, String text) {
		assertTrue(testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).getText().equals(text));
	}

	public void textInIdContains(String viewId, String text) {
		assertTrue(testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).getText().contains(text));
	}

	public void textInIdDiffer(String viewId, String text) {
		assertFalse(testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).getText().equals(text));
	}

	public void enabledById(String viewId) {
		assertTrue(testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).isEnabled());
	}

	public void disabledById(String viewId) {
		assertFalse(testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).isEnabled());
	}

	public void checkedById(String viewId) {
		assertTrue(testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).isChecked());
	}

	public void notCheckedById(String viewId) {
		assertFalse(testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).isChecked());
	}

	// Misc

	public String getTextById(String viewId) {
		return testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).getText();
	}

	public void takeScreenshot(String name) {
		wait(config.getScreenshotWait());
		testDevice.takeScreenshot(TasteTestingSpoonWrapper.getScreenshotDirectory(name));
	}

	public void wait(int seconds) {
		// This is ugly but it works
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void waitForAnimation() {
		testDevice.waitForWindowUpdate(config.getPackageName(), config.getViewTimeout());
	}
}
