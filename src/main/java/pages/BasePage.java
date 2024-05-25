package pages;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

    /**
        * Day 1
    */
    private final By basicAuthOption = By.linkText("Basic Auth");
    private final By confirmMsg = By.xpath("//p[contains(text(),'Congratulations! You must have the proper credential')]");

    public void clickBasicAuth(){
        click_Element(basicAuthOption);
    }

    public String verifyMessage(){
        return get_Text(confirmMsg);
    }

    /**
     * Day 2
    */
    private final By passField = By.id("pass");
    private final By passNewField = By.id("passnew");

    public void enterPassword(String pass){
        write_JS_Executor(passField, pass);
    }

    public void enterPasswordRemovingAttribute(String pass){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("document.getElementById('passnew').removeAttribute('disabled')");

        write_Send_Keys(passNewField, pass);
    }
}
