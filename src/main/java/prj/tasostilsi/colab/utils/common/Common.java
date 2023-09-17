package prj.tasostilsi.colab.utils.common;

import org.openqa.selenium.WebDriver;
import prj.tasostilsi.colab.utils.WebDriverFactory;

public class Common extends WebDriverFactory {
	
	public BrokenLinkHandler brokenLinkHandler;
	public FileHandler fileHandler;
	public BrowserControls browserControls;
	
	public Common() {
		brokenLinkHandler = new BrokenLinkHandler(getDriver());
		fileHandler = new FileHandler(getDriver());
		browserControls = new BrowserControls(getDriver());
	}
	
	public Common(WebDriver driver) {
		brokenLinkHandler = new BrokenLinkHandler(driver);
		fileHandler = new FileHandler(driver);
		browserControls = new BrowserControls(driver);
	}
}
