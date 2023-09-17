package prj.tasostilsi.colab.utils.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import prj.tasostilsi.colab.utils.WebDriverFactory;
import prj.tasostilsi.colab.utils.config.Property;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileHandler extends WebDriverFactory {
	
	String uploadTestDataFolder = Property.getInputDir();
	String downloadTestDataFolder = Property.getOutputDir();
	
	private WebDriver driver;
	
	public FileHandler(WebDriver driver) {
		this.driver = driver;
	}
	
	public void uploadFile(String filePath) {
		WebElement fileUploadElement = getDriver().findElement(By.xpath("//input[@type='file']"));
		fileUploadElement.sendKeys(filePath);
	}
	
	public void downloadFile(String downloadUrl) {
		// Use the Wget tool to download the file
		String[] wgetCommand = {"wget", downloadUrl};
		ProcessBuilder processBuilder = new ProcessBuilder(wgetCommand);
		processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
		processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
		try {
			Process process = processBuilder.start();
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void downloadFile(String url, String savePath) {
		// Create a new thread to download the file
		new Thread(() -> {
			try {
				var urlObject = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
				connection.connect();
				
				// Create a new file to save the downloaded file
				File file = new File(savePath);
				FileOutputStream outputStream = new FileOutputStream(file);
				
				// Read the data from the connection and write it to the file
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = connection.getInputStream().read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				
				// Close the streams
				outputStream.close();
				connection.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
