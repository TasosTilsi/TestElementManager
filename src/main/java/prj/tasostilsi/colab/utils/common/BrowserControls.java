package prj.tasostilsi.colab.utils.common;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.awt.event.KeyEvent;

public final class BrowserControls {
	WebDriver driver;
	
	public BrowserControls(WebDriver driver) {
		this.driver = driver;
	}
	
	public void scrollUp() {
		scroll(-1000);
	}
	
	public void scrollDown() {
		scroll(450);
	}
	
	public void moveElementIntoView(WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", element);
		scroll(-250);
	}
	
	public void moveToElement(WebElement element) {
		moveElementIntoView(element);
		new Actions(driver)
				.moveToElement(element)
				.perform();
	}
	
	public void scrollToElementMiddle(By locator) {
		WebElement element = driver.findElement(locator);
		scrollToElementMiddle(element);
	}
	
	public void scrollToElementMiddle(WebElement element) {
		String scrollElementIntoMiddle = "var viewPortHeight = " +
				"Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
				+ "var elementTop = arguments[0].getBoundingClientRect().top;"
				+ "window.scrollBy(0, elementTop-(viewPortHeight/2));";
		
		((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, element);
	}
	
	private void scroll(int amount) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0," + amount + ")", "");
	}
	
	public void refreshPage() {
		driver.navigate().refresh();
	}
	
	public void goBack() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.history.go(-1)");
	}
	
	public void zoomBrowser(Float zoom) throws AWTException {
		Robot robot = new Robot();
		// zoom in
		if (zoom > 0) {
			for (int i = 0; i < zoom; i++) {
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_EQUALS);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_EQUALS);
			}
		} // zoom out
		else if (zoom < 0) {
			zoom = Math.abs(zoom);
			for (int i = 0; i < zoom; i++) {
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_MINUS);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_MINUS);
			}
		}
	}
}
