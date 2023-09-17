package prj.tasostilsi.colab.utils.common;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import prj.tasostilsi.colab.utils.WebDriverFactory;
import prj.tasostilsi.colab.utils.config.Property;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Wait extends WebDriverFactory {
	
	protected static final int PAGE_LOAD_TIME_OUT_IN_SEC = Property.getTimeOutInSec();
	protected static final int PAGE_LOAD_SLEEP_IN_MILLIS = Property.getSleepInMillis();
	
	private static Wait instance = null;
	private final WebDriverWait wait = new WebDriverWait(getDriver(), Duration.of(PAGE_LOAD_TIME_OUT_IN_SEC, ChronoUnit.SECONDS), Duration.of(PAGE_LOAD_SLEEP_IN_MILLIS, ChronoUnit.MILLIS));
	;
	
	private Wait() {
	}
	
	
	public static Wait getInstance() {
		if (instance == null) {
			synchronized (Wait.class) {
				if (instance == null) {
					instance = new Wait();
				}
			}
		}
		return instance;
	}
	
	public void forPageToLoad() {
		ExpectedCondition<Boolean> expectation = driver -> ((JavascriptExecutor) Objects.requireNonNull(driver))
				.executeScript("return document.readyState").toString().equals("complete")
				&& driver.findElements(By.id("loader")).size() == 0;
		try {
			wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for BasePage Load Request to complete.");
		}
	}
	
	// Sleep thread X seconds. !!!Use of [sleep/thread] must be avoided!!!
	public void sleep(double secs) {
		secs *= 1000;
		try {
			Thread.sleep(Math.round(secs));
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	
	// WAIT for an Element
	public void forElement(WebElement element) {
		if (element != null) {
			wait
					.ignoring(StaleElementReferenceException.class)
					.pollingEvery(Duration.ofSeconds(2))
					.until(ExpectedConditions.visibilityOf(element));
		} else {
			System.out.println("ERROR Element is NOT present.");
		}
	}
	
	public void forElement(By by) {
		try {
			wait
					.ignoring(StaleElementReferenceException.class)
					.pollingEvery(Duration.ofSeconds(2))
					.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (TimeoutException e) {
			Assert.fail("Cannot find Element. After " + PAGE_LOAD_TIME_OUT_IN_SEC + " seconds");
		}
	}
	
	public void forElementToClose(String xpath) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
		
	}
	
	public void forElementToClose(WebElement element) {
		wait.until(ExpectedConditions.invisibilityOf(element));
	}
	
	public WebElement waitForElementToBeClickable(By locator) {
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	public WebElement waitForElementToBeClickable(WebElement element) {
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void waitForTextToBePresentInElement(By locator, String text) {
		wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
	}
	
	public void waitForTextToBePresentInElement(WebElement element, String text) {
		wait.until(ExpectedConditions.textToBePresentInElement(element, text));
	}
	
	public WebElement waitForElementToBePresent(By locator) {
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
}
