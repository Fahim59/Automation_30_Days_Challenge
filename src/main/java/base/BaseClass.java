package base;

import constants.EndPoint;
import factory.DriverFactory;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import org.apache.commons.io.*;
import org.apache.logging.log4j.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;
import utils.ConfigLoader;

import java.text.*;
import java.time.Duration;
import java.util.*;
import java.util.List;

public class BaseClass {
    public static WebDriver driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    private static final Logger logger = LogManager.getLogger(BaseClass.class);

    @BeforeClass
    public static void launch_browser(){
        driver = DriverFactory.initializeDriver(System.getProperty("browser",
                new ConfigLoader().initializeProperty().getProperty("browser")));

        logger.info("Browser launched successfully");
    }

    @BeforeClass
    public static void open_website(){
        Open_Website(EndPoint.DAY30.url);

        logger.info("Website open successfully");
    }
    //---------------------------------------------------------------------------------------------//
    public String dateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static void Open_Website(String endPoint){
        DriverFactory.getDriver().get(new ConfigLoader().initializeProperty().getProperty("baseUrl") +endPoint);
    }

    public static void SmallWait(int second) throws InterruptedException {Thread.sleep(second);}

    public static void Scroll_Down(int pixels) throws InterruptedException {
        SmallWait(1000);
        JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getDriver();
        js.executeScript("window.scrollBy(0, arguments[0])", pixels);
    }

    //---------------------------------------------------------------------------------------//
    public WebElement wait_for_visibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public WebElement wait_for_presence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    public List<WebElement> wait_for_presence_list(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public void click_Element(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    public void click_Element_Js(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        js.executeScript("arguments[0].click();", element);
    }

    public void select_Dropdown_Element(By locator, String text) {
        WebElement element = wait_for_visibility(locator);

        Select select = new Select(element);
        select.selectByVisibleText(text);
    }
    public void click_Radio_Element(By locator, String text) {
        List<WebElement> options = wait_for_presence_list(locator);

        for (WebElement option : options) {
            if (option.getAttribute("value").equalsIgnoreCase(text)) {
                if (!option.isSelected()) {
                    js.executeScript("arguments[0].click();", option);
                }
            }
        }
    }

    public void hover_And_Click(By locator_hover, By locator_click ){
        Actions actions = new Actions(driver);

        WebElement element = wait_for_visibility(locator_hover);
        actions.moveToElement(element).perform();

        click_Element(locator_click);
    }

    public void drag_And_Drop(By dragable, By dropable ){
        Actions actions = new Actions(driver);

        WebElement dragElement = wait_for_visibility(dragable);
        WebElement dropElement = wait_for_visibility(dropable);

        actions.dragAndDrop(dragElement, dropElement).build().perform();
    }

    public void write_Send_Keys(By locator, String txt) {
        WebElement element = wait_for_presence(locator);
        String text = element.getAttribute("value");

        if (text.isEmpty()) {
            element.sendKeys(txt);
        } else {
            element.clear();
            element.sendKeys(txt);
        }
    }
    public void write_JS_Executor(By locator, String txt) {
        WebElement element = wait_for_presence(locator);
        String text = element.getAttribute("value");

        JavascriptExecutor js = (JavascriptExecutor) driver;

        if (text.isEmpty()) {
            js.executeScript("arguments[0].value = arguments[1];", element, txt);
        }
        else {
            js.executeScript("arguments[0].value = '';", element);
            js.executeScript("arguments[0].value = arguments[1];", element, txt);
        }
    }
    public void write_ActionClass(By locator, String txt) {
        WebElement element = wait_for_presence(locator);
        String text = element.getAttribute("value");

        Actions actions = new Actions(driver);

        if (text.isEmpty()) {
            actions.moveToElement(element).click().sendKeys(txt).build().perform();
            //actions.click(element).sendKeys(txt).perform();
        }
        else {
            element.clear();
            actions.moveToElement(element).click().sendKeys(txt).build().perform();
            //actions.click(element).sendKeys(txt).perform();
        }
    }

    public String get_Text(By locator) {
        WebElement element = wait_for_presence(locator);
        return element.getText();
    }
    public int get_Size(By locator) {
        List<WebElement> elements = wait_for_presence_list(locator);
        return elements.size();
    }

    public void upload_file(By locator, String path) throws InterruptedException {
        Actions action = new Actions(driver);

        WebElement element = wait_for_presence(locator);
        action.moveToElement(element).click().build().perform();

        SmallWait(1000);

        try {
            StringSelection filePath = new StringSelection(path);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(filePath, null);

            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            SmallWait(1000);

            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }
        catch (Exception exp) {
            exp.printStackTrace();
        }
    }
    //---------------------------------------------------------------------------------------------//
    @AfterTest
    public static void SaveLogFile(){
        try {
            File logFile = new File("Log Result/test.log");
            File outputFile = new File("Log Result/test_output.txt");

            String outputContents = FileUtils.readFileToString(outputFile, "UTF-8");
            FileUtils.writeStringToFile(logFile, outputContents, "UTF-8", true);

            outputFile.delete();
        }
        catch (Exception e) {
            System.out.println("Log save failed" +e);
        }
    }

    @AfterClass
    public static void QuitBrowser() {
        //driver.quit();

        logger.info("Browser quit successfully");
    }
}
