package factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

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
                WebDriverManager.chromedriver().setup();
                tlDriver.set(new ChromeDriver());
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