package tests;

import base.BaseClass;
import org.apache.logging.log4j.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;
import pages.BasePage;

import java.util.List;

public class Day26 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day26.class);

    private BasePage basePage;

    @BeforeMethod
    public void beforeMethod() {
        basePage = new BasePage(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Fetch values from Graph", priority = 1)
    public void countWorldPopulation() throws InterruptedException {
        Actions action = new Actions(driver);

        Scroll_Down(1250);

        List<WebElement> graphSize = driver.findElements(basePage.getGraphItemsLocator());

        for(WebElement e : graphSize){
            action.moveToElement(e).perform();
            Thread.sleep(500);

            String formattedText = basePage.getGraphText().replace("Interest", "; Interest")
                    .replace("Total Payment", "; Total Payment")
                    .replace("Principal","; Principal");

            System.out.println(formattedText);
        }

        logger.info("Fetch values from Graph");
    }
}