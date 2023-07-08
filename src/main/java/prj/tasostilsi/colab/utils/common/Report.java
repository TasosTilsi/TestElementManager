package prj.tasostilsi.colab.utils.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Report {
	
	public static String getSpecificInvalidStep(ITestResult result, Class classObject) {
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
	
	public static String getStuckTrace(ITestResult result) {
		return ExceptionUtils.getStackTrace(result.getThrowable());
	}
	
	public static String getStuckTraceMessage(ITestResult result) {
		return result.getThrowable().getMessage();
	}
	
	public static void logger(int testStep, String logText) {
		Reporter.log("<p>&nbsp;</p>");
		Reporter.log("<p><font size=\"2\" face=\"Arial\">&nbsp;Test Step "
				+ testStep + ": " + logText + "</font></p>");
	}
	
	public static void logAction(int step, String logText) {
		Reporter.log(step + ") " + logText, 0, true);
	}
	
	public static void logResponse(int step, String logText) {
		Reporter.log("\t" + step + ") " + logText, 0, true);
	}
	
}
