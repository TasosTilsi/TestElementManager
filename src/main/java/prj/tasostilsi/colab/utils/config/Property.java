package prj.tasostilsi.colab.utils.config;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

public class Property {

	public static String getBrowser() {
		return getPropertyValue("app.browser");
	}

	public static boolean getHeadlessRun() {
		return Boolean.parseBoolean(getPropertyValue("app.headless"));
	}

	public static String getURL() {
		return getPropertyValue("app.url");
	}

	public static String getRemoteURL() {
		return getPropertyValue("app.remoteUrl");
	}

	public static int getDownloadTimeOut() {
		return Integer.parseInt(getPropertyValue("app.downloadTimeOut"));
	}

	public static int getTimeOutInSec() {
		return Integer.parseInt(getPropertyValue("app.timeOutInSec"));
	}

	public static int getSleepInMillis() {
		return Integer.parseInt(getPropertyValue("app.sleepInMillis"));
	}

	public static boolean getDebugMode() {
		return Boolean.parseBoolean(getPropertyValue("app.debug"));
	}

	public static String getOS() {
		return System.getProperty("os.name");
	}

	public static String getAuthUserNameOne() {
		return getPropertyValue("auth.username.userNameOne");
	}
	public static String getAuthUserNameOnePassword() {
		return getPropertyValue("auth.password.userNameOne");
	}

	private static String getPropertyValue(String propertyVariable) {
		String propertyValue = null;
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(getPropertyFilePath());
			// load a properties file
			prop.load(input);
			// get the property value and print it out
			propertyValue = prop.getProperty(propertyVariable);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return propertyValue;
	}

	public static String getTestNgSuites() {
		return getTestResourcesPath() + "/test_suites";
	}

	public static String getOutputDir() {
		return getTestResourcesPath()+ "/test_data/outputDir/";
	}

	public static String getInputDir() {
		return getTestResourcesPath() + "/test_data/inputDir/";
	}

	public static String getFileExtension(String filename) {
		return FilenameUtils.getExtension(filename);

	}

	public static String getFileNameNoExtension(String filename) {
		return FilenameUtils.removeExtension(filename);

	}

	private static String getPropertyFilePath() {
		String filePathString = getAbsolutePath() + "/src/main/resources/app.properties";
		File f = new File(filePathString);
		if (!f.exists())
			filePathString = getAbsolutePath() + "/app.properties";
		return filePathString;
	}

	private static String getAbsolutePath() {
		String absPath = Paths.get(".").toAbsolutePath().normalize().toString();
		return absPath.replace("\\", "/");
	}

	public static String getTargetPath() {
		return getAbsolutePath();
	}

	public static String getResourcesPath() {
		String filePathString = getAbsolutePath() + "/src/main/resources";
		File f = new File(filePathString);
		if (!f.exists())
			filePathString = getAbsolutePath();
		return filePathString;
	}
	public static String getTestResourcesPath() {
		String filePathString = getAbsolutePath() + "src/test/resources";
		File f = new File(filePathString);
		if (!f.exists())
			filePathString = getAbsolutePath();
		return filePathString;
	}
}
