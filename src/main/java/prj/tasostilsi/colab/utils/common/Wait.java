package prj.tasostilsi.colab.utils.common;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import prj.tasostilsi.colab.utils.BaseTest;
import prj.tasostilsi.colab.utils.config.Property;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Wait extends BaseTest {

    protected static final int PAGE_LOAD_TIME_OUT_IN_SEC = Property.getTimeOutInSec();
    protected static final int PAGE_LOAD_SLEEP_IN_MILLIS = Property.getSleepInMillis();

    private static volatile Wait instance = null;

    private Wait(){
    }

    public static Wait getInstance() {
        if (instance == null) {
            synchronized(Wait.class) {
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
                && getDriver().findElements(By.id("loader")).size() == 0;
        try {
            Thread.sleep(500);
            WebDriverWait webDriverWait = new WebDriverWait(getDriver(), Duration.of(30, ChronoUnit.SECONDS));
            webDriverWait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for BasePage Load Request to complete.");
        }
    }

    public void forLoaderToClose() {
        new WebDriverWait(getDriver(), Duration.of(PAGE_LOAD_TIME_OUT_IN_SEC, ChronoUnit.SECONDS))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.id("loader-wrapper")));
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
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.of(PAGE_LOAD_TIME_OUT_IN_SEC, ChronoUnit.SECONDS),Duration.of(PAGE_LOAD_SLEEP_IN_MILLIS, ChronoUnit.MILLIS));
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
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.of(PAGE_LOAD_TIME_OUT_IN_SEC, ChronoUnit.SECONDS), Duration.of(PAGE_LOAD_SLEEP_IN_MILLIS, ChronoUnit.MILLIS));
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
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.of(PAGE_LOAD_TIME_OUT_IN_SEC, ChronoUnit.SECONDS), Duration.of(PAGE_LOAD_SLEEP_IN_MILLIS, ChronoUnit.MILLIS));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));

    }

    public void forElementToClose(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.of(PAGE_LOAD_TIME_OUT_IN_SEC, ChronoUnit.SECONDS), Duration.of(PAGE_LOAD_SLEEP_IN_MILLIS, ChronoUnit.MILLIS));
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

}
