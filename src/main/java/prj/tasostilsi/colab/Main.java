package prj.tasostilsi.colab;



import org.apache.commons.io.FileUtils;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.collections.Lists;
import org.testng.log4testng.Logger;
import prj.tasostilsi.colab.utils.config.Property;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static prj.tasostilsi.colab.utils.config.Property.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        if (args[0].equalsIgnoreCase("-test-case")) {
            runTestCase(args);
        } else if (args[0].equalsIgnoreCase("-test-suite")) {
            runTestSuite(args);
        } else
            LOGGER.error("Error in Attributes!");
    }

    public static void runTestSuite(String[] args) {
        if (args[1].equals("ALL")) {
            File testSuitesInResourcesFolder = new File(getTestNgSuites());
            File[] testSuites = testSuitesInResourcesFolder.listFiles();

            assert testSuites != null;
            for (File testSuite : testSuites) {
                String testSuiteName = testSuite.getName();
                if (testSuite.isFile() && getFileExtension(testSuiteName).equalsIgnoreCase("xml")) {
                    runOneTestSuite(getFileNameNoExtension(testSuiteName));
                } else {
                    LOGGER.error("No suites or wrong file type found in resources folder.");
                }
            }
        } else {
            runOneTestSuite(args[1]);
        }
    }

    private static void runOneTestSuite(String testSuiteName) {
        String browser = Property.getBrowser();
        LOGGER.info("Test Suite: " + testSuiteName);
        LOGGER.info("Browser: " + browser);
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG testng = new TestNG();

        List<String> suites = Lists.newArrayList();
        suites.add("test_suites/" + testSuiteName + ".xml");
        String timeStamp = timestamp();
        new File("reports/" + testSuiteName + "_" + timeStamp + "_" + browser).mkdirs();
        testng.setOutputDirectory("reports/" + testSuiteName + "_" + timeStamp + "_" + browser);
        testng.setTestSuites(suites);
        testng.addListener(tla);
        testng.run();

    }

    public static void runTestCase(String[] args) {
        String browser = Property.getBrowser();
        try {
            String tsName = args[1];
            String tcName = args[2];
            LOGGER.info("Test suite: " + tsName);
            LOGGER.info("Test Case: " + tcName);
            LOGGER.info("Browser: " + browser);

            @SuppressWarnings("rawtypes")
            Class c = Class.forName("testSuites." + tsName + "." + tcName);
            TestListenerAdapter tlaTest = new TestListenerAdapter();
            TestNG testngTest = new TestNG();
            new File("reports/" + tcName + "_" + timestamp() + "_" + browser).mkdirs();
            testngTest.setOutputDirectory("reports/" + tcName + "_" + timestamp() + "_" + browser);

//            @SuppressWarnings("rawtypes")
//            List<Class> listenerClasses = new ArrayList<>();
//            listenerClasses.add(org.uncommons.reportng.HTMLReporter.class);
//            listenerClasses.add(org.uncommons.reportng.JUnitXMLReporter.class);
//            testngTest.setListenerClasses(listenerClasses);

            testngTest.setTestClasses(new Class[]{c});
            testngTest.addListener(tlaTest);
            testngTest.run();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String timestamp() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }

    public static void cleanupOutputDir() {
        File outputDir = new File(getOutputDir());
        outputDir.mkdir();
        try {
            FileUtils.cleanDirectory(outputDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Directory: " + outputDir + " has been cleaned-up.");
    }
}
