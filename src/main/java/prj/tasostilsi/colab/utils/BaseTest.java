package prj.tasostilsi.colab.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import prj.tasostilsi.colab.utils.common.Report;
import prj.tasostilsi.colab.utils.common.Wait;
import prj.tasostilsi.colab.utils.config.Property;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BaseTest extends WebDriverFactory {
	
	public Report testReport = new Report();

	@BeforeTest
	public void setUp() {
		setupDriver();
		testReport.initializeReport("./reports/extendReport.html");
		testReport.createTest(getClassName());
	}

	@AfterTest
	public void tearDown() {
		quitBrowser();
		testReport.flushReport();
	}

	@AfterMethod(alwaysRun = true)
	public void catchExceptions(ITestResult result) {
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String className = this.getClassName();
		String methodName = result.getMethod().getMethodName();
		if (!result.isSuccess()) {
			if (ITestResult.FAILURE == Reporter.getCurrentTestResult().getStatus()) {
				Reporter.log("<p>&nbsp;</p>");
				Reporter.log("<p><font size=\"2\" face=\"Arial\">&nbsp;Result: </font><font size=\"2\" face=\"Arial\" color=\"red\"><b>FAIL</b></font></p>");
				Reporter.log("<p><font size=\"2\" face=\"Arial\">&nbsp;Fault Method: <font size=\"2\" face=\"Arial\" color=\"red\">" + testReport.getSpecificInvalidStep(result, this.getClass()) + "</font></font></p>");
				testReport.logFail(testReport.getSpecificInvalidStep(result, this.getClass()));
			} else if (ITestResult.SKIP == Reporter.getCurrentTestResult().getStatus()) {
				Reporter.log("<p>&nbsp;</p>");
				Reporter.log("<p><font size=\"2\" face=\"Arial\">&nbsp;Result: </font><font size=\"2\" face=\"Arial\" color=\"orange\"><b>SKIP</b></font></p>");
				Reporter.log("<p><font size=\"2\" face=\"Arial\">&nbsp;Skipped Method: <font size=\"2\" face=\"Arial\" color=\"red\">" + testReport.getSpecificInvalidStep(result, this.getClass()) + "</font></font></p>");
				testReport.logSkip(testReport.getSpecificInvalidStep(result, this.getClass()));
			}

			File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

			try {
				System.setProperty("org.uncommons.reportng.escape-output", "false");
				String failureScreenshotsPath = Property.getTargetPath() + "/reports/failure_screenshots/";
				failureScreenshotsPath = failureScreenshotsPath.replace('\\', '/');
				String failureImageFileName = className + "_" + formater.format(calendar.getTime()) + ".png";
				FileUtils.copyFile(scrFile, new File((failureScreenshotsPath + failureImageFileName)));
				((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
				testReport.addScreenshotToReport(failureScreenshotsPath + failureImageFileName);
				String relativeScreenshotPath = "../../failure_screenshots/";
				Reporter.log("<p align=\"left\"><font size=\"2\" face=\"Arial\" color=\"blue\"><b>&nbsp;<a href=\"" + relativeScreenshotPath + failureImageFileName + "\">Error screenshot at " + new Date() + "</b></font></p>");
				Reporter.log("<p>&nbsp;<img width=\"300\" src=\"" + relativeScreenshotPath + failureImageFileName + "\" alt=\"screenshot at " + new Date() + "\"/></p></a><br />");
				Reporter.log("<p align=\"left\"><font size=\"2\" face=\"Arial\" color=\"red\">" + testReport.getStuckTraceMessage(result) + "</b></font></p>");
				Reporter.log("<p>&nbsp;</p>");
				Reporter.log("<p align=\"left\"><font size=\"2\" face=\"Arial\" color=\"blue\">Full Stacktrace:</font></p>");
				Reporter.log("<p align=\"left\"><font size=\"2\" face=\"Arial\">" + testReport.getStuckTrace(result).replace("at ", "<br>&nbsp; at ") + "</font></p>");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	protected String getClassName() {
		return this.getClass().getSimpleName();
	}

}
