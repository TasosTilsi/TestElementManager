package prj.tasostilsi.colab.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import prj.tasostilsi.colab.utils.browsers.Chrome;
import prj.tasostilsi.colab.utils.browsers.Firefox;
import prj.tasostilsi.colab.utils.common.BrowserControls;
import prj.tasostilsi.colab.utils.common.Wait;
import prj.tasostilsi.colab.utils.config.Property;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class WebDriverFactory {
	private static WebDriver driver;

	public WebDriver getDriver() {
		return driver;
	}

	public void setupDriver() {
		try {
			startBrowser();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
		try {
			driver.get(Property.getURL());
		}catch (Exception e){
			System.out.println(e);
			new BrowserControls(driver).refreshPage();
		}
		Wait.getInstance().forPageToLoad();
	}


	protected void quitBrowser() {
		if (!Property.getDebugMode() && driver != null) {
			driver.close();
		}
	}

	protected void startBrowser() throws MalformedURLException {
		String remoteUrl = "";
		try {
			if (Property.getRemoteURL() != null) {
				remoteUrl = Property.getRemoteURL();
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		String browserName = Property.getBrowser();

		driver = remoteUrl != null && !remoteUrl.isEmpty() ? launchBrowserRemote(browserName, remoteUrl) :
				launchBrowser(browserName);

		if (remoteUrl != null && !remoteUrl.isEmpty())
			((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());

		Wait.getInstance().forPageToLoad();
		driver.manage().window().maximize();
	}

	private WebDriver launchBrowserRemote(String browserName, String remoteUrl) throws MalformedURLException,
			IllegalArgumentException {
		switch (browserName) {
			case "chrome":
				return new RemoteWebDriver(new URL(remoteUrl), new Chrome().getCapabilities());
			case "firefox":
				return new RemoteWebDriver(new URL(remoteUrl), new Firefox().getCapabilities());
			default:
				throw new IllegalArgumentException("Not able to set Driver object for option:" + browserName);
		}
	}

	private WebDriver launchBrowser(String browserName) {
		switch (browserName) {
			case "chrome":
				return new Chrome().start();
			case "firefox":
				return new Firefox().start();
			default:
				throw new IllegalArgumentException("Not able to set Driver object for option:" + browserName);
		}
	}
}
