package prj.tasostilsi.colab.utils.browsers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import prj.tasostilsi.colab.utils.config.Property;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

import static prj.tasostilsi.colab.utils.config.Property.*;

public class Firefox {
    
    private static final String CHMOD_COMMAND = "chmod u+x ";

    public WebDriver start() {
        WebDriverManager.firefoxdriver().setup();
//        return WebDriverManager.chromedriver().capabilities(getCapabilities()).create();
        return new FirefoxDriver(getCapabilities());
    }

    public FirefoxOptions getCapabilities() {
        /*String outputDir = getOutputDir();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", outputDir);
        chromePrefs.put("safebrowsing.enabled", "true");*/

//        System.setProperty("webdriver.chrome.driver", getExecutablePathForChrome());
//        System.setProperty("webdriver.chrome.driver", WebDriverManager.chromedriver().getBrowserPath().get().toString());
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--lang=en");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--remote-debugging-port=9222");
        options.addArguments("--incognito");
        if (Property.getHeadlessRun()){
            options.addArguments("--headless");
            options.addArguments("--window-size=1920,1080");
        }
        /*if(!getOS().toLowerCase(Locale.ROOT).contains("win")) {
            try {
                makeChromeBinaryExecutable();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            options.setBinary(getResourcesPath()+"/binaries/unix/chrome/google-chrome");
            options.setBinary("/usr/share/google-chrome");
        }else{
            options.setBinary(getResourcesPath()+"/binaries/windows/chrome/chrome.exe");
        }*/
        options.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
//        options.setCapability("chrome.switches", Collections.singletonList("--incognito"));
        return options;
    }

    private void makeChromeBinaryExecutable() throws IOException {
        Runtime.getRuntime().exec(CHMOD_COMMAND +getResourcesPath()+"/drivers/unix/chromedriver");
        Runtime.getRuntime().exec(CHMOD_COMMAND +getResourcesPath()+"/binaries/unix/chrome/chrome");
        Runtime.getRuntime().exec(CHMOD_COMMAND +getResourcesPath()+"/binaries/unix/chrome/google-chrome");
        Runtime.getRuntime().exec(CHMOD_COMMAND +getResourcesPath()+"/binaries/unix/chrome/chrome-sandbox");
        Runtime.getRuntime().exec(CHMOD_COMMAND +getResourcesPath()+"/binaries/unix/chrome/chrome_crashpad_handler");
    }

    public String getExecutablePathForChrome() {
        if(!getOS().toLowerCase(Locale.ROOT).contains("win")){
            return getResourcesPath()+"/drivers/unix/chromedriver";
        }else {
            return getResourcesPath()+"/drivers/windows/chromedriver.exe";
        }
    }
}
