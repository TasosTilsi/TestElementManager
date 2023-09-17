package prj.tasostilsi.colab.utils.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BrokenLinkHandler {
	
	private WebDriver driver;
	
	public BrokenLinkHandler(WebDriver driver) {
		this.driver = driver;
	}
	
	public List<String> findBrokenLinks() {
		List<String> brokenLinks = new ArrayList<>();
		
		// Get all links on the page
		List<WebElement> linkElements = driver.findElements(By.tagName("a"));
		
		// Iterate over the links and check the status code of each link
		for (WebElement linkElement : linkElements) {
			String href = linkElement.getAttribute("href");
			try {
				URL url = new URL(href);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.connect();
				int responseCode = connection.getResponseCode();
				if (responseCode != 200) {
					brokenLinks.add(href);
				}
			} catch (IOException e) {
				brokenLinks.add(href);
			}
		}
		
		return brokenLinks;
	}
	
}
