package tests;

import base.BaseClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BasePage;

import java.io.FileReader;

public class Day3 extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Day3.class);

    private BasePage basePage;

    FileReader data;
    JSONObject details;

    @BeforeClass
    public void beforeClass() throws Exception {
        try {
            String file = "src/main/resources/data.json";
            data = new FileReader(file);

            JSONTokener tokener = new JSONTokener(data);
            details = new JSONObject(tokener);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            if (data != null) {
                data.close();
            }
        }
    }

    @BeforeMethod
    public void beforeMethod() {
        basePage = new BasePage(driver);
    }
    //-------------------------------------------------------//

    @Test(description = "Read the * rating of the book", priority = 1)
    public void readRating() {
        System.out.println("Pseudo-element content: " + basePage.getContentOfRatingField());

        basePage.enterRating(basePage.getContentOfRatingField());

        basePage.clickRatingButton();

        Assert.assertEquals(basePage.getValidationMsg(), details.getJSONObject("day3").getString("message"));

        logger.info("Read the * rating of the book, enter it in text box and click check rating button");
    }
}