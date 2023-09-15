package prj.tasostilsi.colab.utils.common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import prj.tasostilsi.colab.utils.WebDriverFactory;

import java.io.IOException;


public class Report {
	
	public Report(){}
	
	public String getSpecificInvalidStep(ITestResult result, Class classObject) {
		String stackTrace = getStuckTrace(result);
		String[] stackTraceParts = stackTrace.split(classObject.getPackage().getName());
		String stackTraceLines[] = stackTraceParts[0].split("\\r\\n|\\n|\\r|\\r\\n\\t");
		int stackTraceLength = stackTraceLines.length;
		String invalidStepPath = StringUtils.substringBetween(stackTraceLines[stackTraceLength - 2], ".", "(");
		String invalidSteps[] = invalidStepPath.split("\\.");
		int invalidStepsLength = invalidSteps.length;
		String invalidStep = invalidSteps[invalidStepsLength - 1];
		return invalidStep;
	}
	
	public String getStuckTrace(ITestResult result) {
		return ExceptionUtils.getStackTrace(result.getThrowable());
	}
	
	public String getStuckTraceMessage(ITestResult result) {
		return result.getThrowable().getMessage();
	}
	
	public void logger(int testStep, String logText) {
		Reporter.log("<p>&nbsp;</p>");
		Reporter.log("<p><font size=\"2\" face=\"Arial\">&nbsp;Test Step "
				+ testStep + ": " + logText + "</font></p>");
	}
	
	public void logAction(int step, String logText) {
		this.logInfo(step + ") " + logText);
	}
	
	public void logResponse(int step, String logText) {
		this.logInfo("\t" + step + ") " + logText);
	}
	
	private ExtentReports extent;
	private ExtentTest test;
	
	public void initializeReport(String reportPath) {
		// Initialize ExtentReports and attach the HTML report file
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter(reportPath);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
	}
	
	public void createTest(String testName) {
		// Create a new test in the report
		test = extent.createTest(testName);
	}
	
	public void logInfo(String message) {
		// Log an informational message
		test.log(Status.INFO, message);
	}
	
	public void logSkip(String message) {
		// Log an skipped message
		test.log(Status.SKIP, message);
	}
	
	public void logPass(String message) {
		// Log a pass status with a message
		test.log(Status.PASS, message);
	}
	
	public void logFail(String message) {
		// Log a fail status with a message
		test.log(Status.FAIL, message);
	}
	
	public void addScreenshotToReport(String path) throws IOException {
		// Capture a screenshot and embed it in the report
		test.addScreenCaptureFromPath(path);
	}
	
	public void flushReport() {
		// Save the report and close resources
		extent.flush();
	}
	
}
