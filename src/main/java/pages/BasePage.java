package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage extends BaseClass{
    private final WebDriver driver;
    private final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private final By confirmMsg = By.xpath("//p[contains(text(),'Congratulations! You must have the proper credenti')]");

    public String verifyMessage(){
        return get_Text(confirmMsg);
    }
}
