package factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import utils.ConfigLoader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    /**
     * This method is used to initialize the thread local driver on the basis of given
     * browser
     *
     * @param browser
     * @return this will return tldriver.
     */

    public static WebDriver initializeDriver(String browser){
        System.out.println("browser value is: " + browser);

        switch (browser) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                Map<String, Object> prefs = new HashMap<>();

                options.setExperimentalOption("useAutomationExtension", false);
                options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

                options.addArguments("--disable-notifications");

                prefs.put("download.default_directory", new ConfigLoader().initializeProperty().getProperty("downloadFilePath"));
                prefs.put("profile.default_content_settings.popups", 0);

                prefs.put("credentials_enable_service", false);         //prevent pop up of save password window
                prefs.put("profile.password_manager_enabled", false);   //prevent pop up of save password window

                options.setExperimentalOption("prefs", prefs);

                WebDriverManager.chromedriver().setup();
                tlDriver.set(new ChromeDriver(options));
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                tlDriver.set(new FirefoxDriver());
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                tlDriver.set(new EdgeDriver());
                break;

            case "safari":
                WebDriverManager.safaridriver().setup();
                tlDriver.set(new SafariDriver());
                break;

            default: throw new IllegalStateException("INVALID BROWSER" +browser);
        }

        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
        return getDriver();
    }

    /**
     * this is used to get the driver with ThreadLocal
     *
     * @return
     */

    public static WebDriver getDriver() {
        return tlDriver.get();
    }
}